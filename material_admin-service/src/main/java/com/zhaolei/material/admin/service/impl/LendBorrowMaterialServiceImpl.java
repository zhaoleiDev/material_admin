package com.zhaolei.material.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.redis.RedisUtils;
import com.zhaolei.material.admin.dao.graduation.LendBorrowMaterialMapper;
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

import java.util.Date;
import java.util.List;

/**
 * 个人的借入借出数据使用缓存：key分别为 学号+lend  学号+borrow,缓存时间可以设置相对较长，若删除缓存失败日志会有对应的记录
 * 组织的借入借出数据不使用缓存，一个组员对数据的更新会影响到整个组织的借入、借出数据
 * 索引问题:由于组织角度没走缓存，所以索引需要优先考虑组织维度
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
            //减库存  若没有记录被更新直接返回
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
            lendBorrowMaterialDO.setLendStNum(borrower.getStudentNum());
            boolean flag = lendBorrowMaterialMapper.insertSelective(lendBorrowMaterialDO)>0;
            if(!flag){
                log.error("借出物资减库存时没有数据被更新:{}",JSON.toJSONString(lendMaterial));
                throw new Exception();
            }
            //删除个人缓存,即使数据库回滚，但缓存的删除不能回滚，不会造成数据的问题,但是缓存删除失败会造成数据库的回滚
            RedisUtils.del(lender.getStudentNum()+"lend");
            RedisUtils.del(borrower.getStudentNum()+"borrow");
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
                log.error("确认归还物资时,加可借出数量没有数据被更新:{}",JSON.toJSONString(lendBorrowMaterialDO));
                return ServiceResponse.addInfo(ResponseEnum.FAIL_OPERATION);
            }
            //更新借入借出物资表 如果没有记录被更新直接触发回滚
            lendBorrowMaterialDO.setRevertTime(new Date(System.currentTimeMillis()));
            boolean flag = lendBorrowMaterialMapper.updateByPrimaryKeySelective(lendBorrowMaterialDO)>0;
            if(!flag){
                log.error("归还物资时,更新借入借出表没有数据被更新:{}",JSON.toJSONString(lendBorrowMaterialDO));
                throw new Exception();
            }
            //删除个人维度缓存 同上面一样删除缓存失败会造成数据库回滚，如果第一条成功，第二条抛出异常，第一条删除的记录不会被回滚
            RedisUtils.del(lendBorrowMaterialDO.getBorrowStNum()+"borrow");
            RedisUtils.del(lendBorrowMaterialDO.getLendStNum()+"lend");
            return ServiceResponse.addInfo(ResponseEnum.SUCCESS);
        }catch(Exception e){
            log.error("确认归还物资失败,信息:{}",JSON.toJSONString(lendBorrowMaterialDO));
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.addInfo(ResponseEnum.FAIL_OPERATION);
        }
    }

    @Override
    public List<LendBorrowMaterialDO> getLendMaterialByStNum(Integer lendStNum) {
        return null;
    }

    @Override
    public List<LendBorrowMaterialDO> getBorrowMaterialByStNum(Integer BorrowNum) {
        return null;
    }
}
