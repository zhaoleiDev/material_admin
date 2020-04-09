package com.zhaolei.material.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhaolei.material.admin.common.redis.RedisUtils;
import com.zhaolei.material.admin.common.tools.TimeUtils;
import com.zhaolei.material.admin.dao.graduation.LendBorrowMaterialMapper;
import com.zhaolei.material.admin.domain.base.Page;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.LendBorrowMaterialDO;
import com.zhaolei.material.admin.domain.dao.MaterialDO;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.service.LendBorrowMaterialService;
import com.zhaolei.material.admin.service.MaterialService;
import com.zhaolei.material.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * 个人的借入借出直接查库
 * 组织的借入借出走缓存，不保证一致性，五分钟延迟
 * @author ZHAOLEI
 */
@Service
@Slf4j
public class LendBorrowMaterialServiceImpl implements LendBorrowMaterialService {
    @Autowired
    private LendBorrowMaterialMapper lendBorrowMaterialMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MaterialService materialService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse lend(LendBorrowMaterialDO lendBorrowMaterialDO,UserDO lender,UserDO borrower) {
        try{
            //检查库存 需要使用当前读的方式的查询方法
            Integer materialId = lendBorrowMaterialDO.getMaterialId();
            MaterialDO materialDO = materialService.getMaterialLendNumById(materialId);
            if(materialDO == null){
                return ServiceResponse.addInfo(ResponseEnum.MATERIAL_NOT_EXIST);
            }
            if(materialDO.getLendNum() < lendBorrowMaterialDO.getNumber()){
                return ServiceResponse.addInfo(ResponseEnum.STOCK_LOW);
            }
            //减库存  若没有记录被更新直接返回,因为在这之前没有更新库的操作，不需要回滚
            Integer lendNum = materialDO.getLendNum()-lendBorrowMaterialDO.getNumber();
            MaterialDO lendMaterial = new MaterialDO();
            lendMaterial.setId(materialId);
            lendMaterial.setLendNum(lendNum);
            boolean res = materialService.updateById(lendMaterial);
            if(!res){
                log.error("借出物资减库存时没有数据被更新:{}",JSON.toJSONString(lendMaterial));
                return ServiceResponse.addInfo(ResponseEnum.FAIL_OPERATION);
            }

            //插入借入借出记录 若没有记录被更新需要提前触发回滚，不能直接返回，因为在此之前已经有了一个更新操作
            lendBorrowMaterialDO.setLendOrg(lender.getOrganization());
            lendBorrowMaterialDO.setLendStNum(lender.getStudentNum());
            lendBorrowMaterialDO.setBorrowOrg(borrower.getOrganization());
            lendBorrowMaterialDO.setBorrowStNum(borrower.getStudentNum());
            boolean flag = lendBorrowMaterialMapper.insertSelective(lendBorrowMaterialDO)>0;
            if(!flag){
                log.error("借出物资减库存时没有数据被更新:{}",JSON.toJSONString(lendMaterial));
                throw new Exception();
            }
            return ServiceResponse.addInfo(ResponseEnum.SUCCESS);
        }catch(Exception e){
            log.error("借出物资失败,material:{},lender:{},borrower:{}", JSON.toJSONString(lendBorrowMaterialDO),lender.getStudentNum(),borrower.getStudentNum());
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.addInfo(ResponseEnum.FAIL_OPERATION);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResponse ackRevert(LendBorrowMaterialDO lendBorrowMaterialDO) {
        try{
            //更新总物资表, 如果没有记录被更新直接返回
            boolean res = materialService.addLendNumById(lendBorrowMaterialDO.getMaterialId(),lendBorrowMaterialDO.getNumber());
            if(!res){
                log.error("确认归还物资时,修改物资表可借出数量时数据被更新:{}",JSON.toJSONString(lendBorrowMaterialDO));
                return ServiceResponse.addInfo(ResponseEnum.FAIL_OPERATION);
            }
            //更新借入借出物资表 如果没有记录被更新直接触发回滚
            lendBorrowMaterialDO.setRevertTime(new Date(System.currentTimeMillis()));
            boolean flag = lendBorrowMaterialMapper.updateByPrimaryKeySelective(lendBorrowMaterialDO)>0;
            if(!flag){
                log.error("归还物资时,更新借入借出表没有数据被更新:{}",JSON.toJSONString(lendBorrowMaterialDO));
                throw new Exception();
            }
            return ServiceResponse.addInfo(ResponseEnum.SUCCESS);
        }catch(Exception e){
            log.error("确认归还物资失败,信息:{}",JSON.toJSONString(lendBorrowMaterialDO));
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.addInfo(ResponseEnum.FAIL_OPERATION);
        }
    }

    @Override
    public ServiceResponse getLendMaterialByStNum(String lendStNum, Page page) {
        //field 字段名，需要与LendBorrowMaterialDO中字段名称完全一致
        return selectBySelectiveUtil("lendStNum",lendStNum,page);
    }

    @Override
    public ServiceResponse getBorrowMaterialByStNum(String borrowNum,Page page) {
        //field 字段名，需要与LendBorrowMaterialDO中字段名称完全一致
        return selectBySelectiveUtil("borrowStNum",borrowNum,page);
    }

    @Override
    public ServiceResponse getOrgLendMaterial(String lendOrgName, Page page) {
        String key = "lend:"+lendOrgName+page.getPageSize()+page.getPageNum();
        String jsonStr = RedisUtils.get(key);
        if(jsonStr == null){
            //field 字段名，需要与LendBorrowMaterialDO中字段名称完全一致
            ServiceResponse serviceResponse = selectBySelectiveUtil("lendOrg",lendOrgName,page);
            String json = JSON.toJSONString(serviceResponse);
            RedisUtils.setRandomEx(key,json, TimeUtils.FIVE_MINUTE_S);
            return serviceResponse;
        }
        //嵌套泛型的类如何序列化,如果不指明泛型，那么反序列化后的泛型就是JSONObject
        return  JSON.parseObject(jsonStr, new TypeReference<ServiceResponse<List<LendBorrowMaterialDO>>>(){});
    }

    @Override
    public ServiceResponse getOrgBorrowMaterial(String borrowOrgName, Page page) {
        String key = "borrow:"+borrowOrgName+page.getPageSize()+page.getPageNum();
        String jsonStr = RedisUtils.get(key);
        if(jsonStr == null){
            //field 字段名，需要与LendBorrowMaterialDO中字段名称完全一致
            ServiceResponse serviceResponse = selectBySelectiveUtil("borrowOrg",borrowOrgName,page);
            String json = JSON.toJSONString(serviceResponse);
            RedisUtils.setRandomEx(key,json, TimeUtils.FIVE_MINUTE_S);
            return serviceResponse;
        }
        //嵌套泛型的类如何序列化,如果不指明泛型，那么反序列化后的泛型就是JSONObject
        return  JSON.parseObject(jsonStr, new TypeReference<ServiceResponse<List<LendBorrowMaterialDO>>>(){});
    }

    /**
     * 调用mapper中selectBySelective方法
     * @param field 字段名，需要与LendBorrowMaterialDO中字段名称完全一致
     * @param searchParam 查询的参数值
     * @param page 分页信息
     * @return 返回结果
     */
    private ServiceResponse selectBySelectiveUtil(String field, String searchParam,Page page) {
        //构建LendBorrowMaterialDO对象
        Class clazz = LendBorrowMaterialDO.class;
        LendBorrowMaterialDO lendBorrowMaterialDO = null;
        //用于标记是否发生异常，因为不想再往上抛出异常,导致异常重复记录，如果有异常抛出就直接在这层返回对应的code码
        boolean flag = false;
        try {
            lendBorrowMaterialDO = (LendBorrowMaterialDO) clazz.newInstance();
            Field rField = clazz.getDeclaredField(field);
            rField.setAccessible(true);
            rField.set(lendBorrowMaterialDO,searchParam);
        } catch (Exception e) {
           log.error("反射获取lendBorrowMaterialDO对象失败",e);
           flag = true;
        }
        if(flag){
            return ServiceResponse.addInfo(ResponseEnum.FAIL_SEARCH);
        }
        //数据库查询
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<LendBorrowMaterialDO> lendBorrowMaterialDOList = lendBorrowMaterialMapper.selectBySelective(lendBorrowMaterialDO);
        //获取分页信息
        PageInfo pageInfo = new PageInfo<>(lendBorrowMaterialDOList);
        Page pageRes = new Page();
        pageRes.setTotal((int)pageInfo.getTotal());
        pageRes.setPageNum(pageInfo.getPageNum());
        pageRes.setPageSize(pageInfo.getPageSize());
        return ServiceResponse.addInfo(ResponseEnum.SUCCESS,lendBorrowMaterialDOList,pageRes);
    }
}
