package com.zhaolei.material.admin.service;

import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.LendBorrowMaterialDO;
import com.zhaolei.material.admin.domain.dao.UserDO;

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
}
