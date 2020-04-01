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

/**
 * 个人的借入借出数据使用缓存：key分别为 学号+lend  学号+borrow
 * 组织的借入借出数据不使用缓存，一个组员对数据的更新会影响到整个组织的借入、借出数据
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
            //检查库存
            Integer materialId = lendBorrowMaterialDO.getMaterialId();
            //需要使用当前读的方式的查询方法
            MaterialDO materialDO = materialService.getMaterialStockById(materialId);
            if(materialDO.getLendNum() < lendBorrowMaterialDO.getNumber()){
                return ServiceResponse.addInfo(ResponseEnum.STOCK_LOW);
            }
            //减库存
            Integer stockNum = materialDO.getLendNum()-lendBorrowMaterialDO.getNumber();
            MaterialDO stockMaterial = new MaterialDO();
            stockMaterial.setId(materialId);
            stockMaterial.setLendNum(stockNum);
            materialService.updateById(stockMaterial);

            //插入借入借出记录
            lendBorrowMaterialDO.setLendOrg(lender.getOrganization());
            lendBorrowMaterialDO.setLendStNum(lender.getStudentNum());
            lendBorrowMaterialDO.setBorrowOrg(borrower.getOrganization());
            lendBorrowMaterialDO.setLendStNum(borrower.getStudentNum());
            lendBorrowMaterialMapper.insertSelective(lendBorrowMaterialDO);
            //删除个人缓存,即使数据库回滚，但缓存的删除不能回滚，不会造成数据的问题,但是缓存删除失败会造成数据库的回滚
            RedisUtils.del(lender.getStudentNum()+"lend");
            RedisUtils.del(borrower.getStudentNum()+"borrow");
            return ServiceResponse.addInfo(ResponseEnum.SUCCESS);
        }catch(Exception e){
            log.error("借出物资失败,material:{},lender:{},borrower:{}", JSON.toJSONString(lendBorrowMaterialDO),JSON.toJSONString(lender),JSON.toJSONString(borrower));
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.addInfo(ResponseEnum.FAIL_OPERATION);
        }
    }
}
