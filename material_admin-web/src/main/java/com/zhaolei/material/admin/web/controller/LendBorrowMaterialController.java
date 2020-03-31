package com.zhaolei.material.admin.web.controller;

import com.zhaolei.material.admin.service.LendBorrowMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ZHAOLEI
 */
@Controller
@ResponseBody
@RequestMapping("/lendBorrow")
@Slf4j
public class LendBorrowMaterialController {
    @Autowired
    private LendBorrowMaterialService lendBorrowMaterialService;
}
