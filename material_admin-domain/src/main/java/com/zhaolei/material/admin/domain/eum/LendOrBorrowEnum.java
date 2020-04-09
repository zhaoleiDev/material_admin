package com.zhaolei.material.admin.domain.eum;

/**
 * 注意没有重写hashcode()，所以不要将该类用在hash表结构中
 * @author ZHAOLEI
 */

public enum LendOrBorrowEnum {
    /**
     * 个人借出
     */
    PERSONAL_LEND(1),
    /**
     * 个人借入
     */
    PERSONAL_BORROW(2),
    /**
     * 组织借出
     */
    ORG_LEND(3),
    /**
     * 组织借入
     */
    ORG_BORROW(4)
    ;

    private int type;
    LendOrBorrowEnum(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean equals(LendOrBorrowEnum lendOrBorrowEnum){
        return this.type == lendOrBorrowEnum.getType();
    }
}
