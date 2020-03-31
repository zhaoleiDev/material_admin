package com.zhaolei.material.admin.service.impl;

import com.zhaolei.material.admin.dao.graduation.LendBorrowMaterialMapper;
import com.zhaolei.material.admin.service.LendBorrowMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOLEI
 */
@Service
public class LendBorrowMaterialServiceImpl implements LendBorrowMaterialService {
    @Autowired
    private LendBorrowMaterialMapper lendBorrowMaterialMapper;
}
