package com.zhaolei.material.admin.service;

import com.zhaolei.material.admin.domain.base.Page;
import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.LendBorrowMaterialDO;
import com.zhaolei.material.admin.domain.dao.UserDO;

import java.util.List;

/**
 * @author ZHAOLEI
 */
public interface LendBorrowMaterialService {
    /**
     * 借出物资
     * @param lendBorrowMaterialDO 借出信息
     * @param borrower 借入者
     * @param lender 借出者
     * @return 返回结果
     */
    ServiceResponse lend(LendBorrowMaterialDO lendBorrowMaterialDO, UserDO lender,UserDO borrower);

    /**
     * 确认归还物资
     * @param lendBorrowMaterialDO 确认信息
     * @return 返回结果
     */
    ServiceResponse ackRevert(LendBorrowMaterialDO lendBorrowMaterialDO);

    /**
     * 根据学号查看借出信息
     * @param lendStNum 借出者学号
     * @param page 分页信息
     * @return 结果集
     */
    ServiceResponse getLendMaterialByStNum(String lendStNum, Page page);

    /**
     * 根据学号查看借入者信息
     * @param borrowNum 借入者学号
     * @param page 分页信息
     * @return 结果集
     */
    ServiceResponse getBorrowMaterialByStNum(String borrowNum,Page page);

    /**
     * 获取组织的借出物资
     * @param lendOrgName 组织名称
     * @param page 分页信息
     * @return 返回结果集
     */
    ServiceResponse getOrgLendMaterial(String lendOrgName,Page page);

    /**
     * 获取组织借入物资
     * @param borrowOrgName 组织名称
     * @param page 分页信息
     * @return 返回结果集
     */
    ServiceResponse getOrgBorrowMaterial(String borrowOrgName,Page page);
}
