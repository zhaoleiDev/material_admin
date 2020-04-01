package com.zhaolei.material.admin.service;

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
     * @return 结果集
     */
    List<LendBorrowMaterialDO> getLendMaterialByStNum(Integer lendStNum);

    /**
     * 根据学号查看借入者信息
     * @param BorrowNum 借入者学号
     * @return 结果集
     */
    List<LendBorrowMaterialDO> getBorrowMaterialByStNum(Integer BorrowNum);
}
