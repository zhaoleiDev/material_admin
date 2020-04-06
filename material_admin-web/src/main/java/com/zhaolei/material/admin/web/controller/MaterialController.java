package com.zhaolei.material.admin.web.controller;

import com.alibaba.fastjson.JSON;
import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import com.zhaolei.material.admin.domain.base.*;
import com.zhaolei.material.admin.domain.dao.MaterialDO;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.domain.vo.MaterialResponse;
import com.zhaolei.material.admin.domain.vo.MaterialVO;
import com.zhaolei.material.admin.service.MaterialService;
import com.zhaolei.material.admin.service.UserService;
import com.zhaolei.material.admin.web.handler.FileHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author ZHAOLEI
 */
@Controller
@ResponseBody
@RequestMapping("/material")
@Slf4j
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileHandler fileHandler;

    /**
     * 录入物资
     * @param materialVO 物资信息
     * @return 结果
     */
    @RequestMapping("/entry")
    public Response entry(MaterialVO materialVO, MultipartFile file){
        String stNum = LoginContextUtils.getStNum();
        log.info("学号为:{}的用户录入物资:{}",stNum, JSON.toJSONString(materialVO));
        MaterialDO materialDO = new MaterialDO();
        BeanUtils.copyProperties(materialVO,materialDO);
        log.info("materialDO:{}",JSON.toJSONString(materialDO));
        //防止前端传入id,造成数据库混乱
        materialDO.setId(null);
        //负责人检查
        if(materialVO.getPrincipalStNum() == null){
            return Response.addInfo(ResponseEnum.ERROR_PARAM);
        }
        UserDO principal = userService.getUerByStNum(materialVO.getPrincipalStNum());
        if(principal == null){
            return Response.addInfo(ResponseEnum.PRINCIPAL_NOT_REGISTERED);
        }
        //图片处理
        String photoPath = fileHandler.upload(file);
        materialDO.setPhotoPath(photoPath);
        //获取当前操作人信息
        UserDO userDO = userService.getUerByStNum(stNum);
        materialDO.setUpdateStNum(stNum);
        materialDO.setOrgName(userDO.getOrganization());
        return Response.success(materialService.entry(materialDO));
    }

    @PostMapping("/delete")
    public Response delete(@RequestParam("id") Integer id){
        String stNum = LoginContextUtils.getStNum();
        log.info("学号为:{}的用户删除物资id:{}",stNum,id);
        if(materialService.deleteById(id)){
            return Response.success();
        }
        return Response.fail();
    }

    @PostMapping("/update")
    public Response update(MaterialVO materialVO,MultipartFile file){
        String stNum = LoginContextUtils.getStNum();
        log.info("学号为:{}的用户更新物资,信息:{}",stNum,JSON.toJSONString(materialVO));
        MaterialDO materialDO = new MaterialDO();
        BeanUtils.copyProperties(materialVO,materialDO);
        if(file != null){
            String photoPath = fileHandler.upload(file);
            materialDO.setPhotoPath(photoPath);
        }
        if(materialService.updateById(materialDO)){
            return Response.success();
        }
        return Response.fail();
    }

    @RequestMapping("/getOrgMaterial")
    public Response getOrgMaterial(@RequestParam(required = false,defaultValue = "1") int pageNum,@RequestParam(required = false,defaultValue = "13") int pageSize){
        String orgName = LoginContextUtils.getOrgName();
        Page page = new Page(pageNum,pageSize);
        ServiceResponse serviceResponse = materialService.getMaterialByOrg(orgName,page);
        return Response.parseResponse(serviceResponse);
    }

    @RequestMapping("/getMaterialById")
    public Response getMaterialById(@RequestParam("id") int id){
        MaterialDO materialDO = materialService.getMaterialById(id);
        String principalStNum = materialDO.getPrincipalStNum();
        //获取负责人信息
        UserDO principal = userService.getUerByStNum(principalStNum);
        MaterialResponse materialResponse = new MaterialResponse();
        BeanUtils.copyProperties(materialDO,materialResponse);
        materialResponse.setPrincipalName(principal.getUserName());
        return Response.success(materialResponse);
    }


    @RequestMapping("/searchMaterial")
    public Response searchMaterial(){
        return null;
    }

}
