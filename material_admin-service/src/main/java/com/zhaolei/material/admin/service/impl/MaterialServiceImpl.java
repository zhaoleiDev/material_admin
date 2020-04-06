package com.zhaolei.material.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhaolei.material.admin.common.tools.LoginContextUtils;
import com.zhaolei.material.admin.dao.graduation.MaterialMapper;
import com.zhaolei.material.admin.domain.base.Page;
import com.zhaolei.material.admin.domain.base.ResponseEnum;
import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.MaterialDO;
import com.zhaolei.material.admin.domain.dao.UserDO;
import com.zhaolei.material.admin.domain.vo.MaterialResponse;
import com.zhaolei.material.admin.service.MaterialService;
import com.zhaolei.material.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 与物资表相关统一不走缓存，因为从业务上有根据组织查询物资的需求
 * 只有有任何一个组员有物资的借入借出、归还确认依据物资的更新都会使组织物资信息发生变化
 * @author ZHAOLEI
 */
@Service
@Slf4j
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private UserService userService;


    @Override
    public boolean entry(MaterialDO materialDO) {
       return  materialMapper.insertSelective(materialDO)>0;
    }

    @Override
    public boolean deleteById(Integer id) {
        String stNum = LoginContextUtils.getStNum();
        MaterialDO materialDO = new MaterialDO();
        materialDO.setUpdateStNum(stNum);
        materialDO.setId(id);
        //将状态设置为删除状态
        materialDO.setStatusInfo(0);
        return materialMapper.updateByPrimaryKeySelective(materialDO)>0;
    }

    @Override
    public boolean updateById(MaterialDO materialDO) {
       return materialMapper.updateByPrimaryKeySelective(materialDO)>0;
    }

    @Override
    public ServiceResponse getMaterialByOrg(String orgName, Page page) {
        List<MaterialResponse> materialResponseList = new ArrayList<>();
        //设置分页参数,直接传pageNum即可
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<MaterialDO> list = materialMapper.getMaterialByOrg(orgName);
        //类型强转获取分页信息
        PageInfo pageHelper = new PageInfo<>(list);
        Page resPage = new Page();
        resPage.setTotal((int)pageHelper.getTotal());
        resPage.setPageNum(pageHelper.getPageNum());
        log.info("pageNum:{}",pageHelper.getPageNum());
        resPage.setPageSize(pageHelper.getPageSize());
        //类型转换以及获取物资负责人的信息
        for(MaterialDO materialDO:list){
            MaterialResponse materialResponse = new MaterialResponse();
            BeanUtils.copyProperties(materialDO,materialResponse);
            UserDO principal = userService.getUerByStNum(materialDO.getPrincipalStNum());
            if(principal != null){
                materialResponse.setPrincipalName(principal.getUserName());
                materialResponse.setPrincipalPhoneNum(principal.getPhoneNum());
                materialResponseList.add(materialResponse);
            }
        }
        return ServiceResponse.addInfo(ResponseEnum.SUCCESS,materialResponseList,resPage);
    }

    @Override
    public MaterialDO getMaterialById(Integer id) {
        return materialMapper.selectByPrimaryKey(id);
    }

    @Override
    public MaterialDO getMaterialLendNumById(Integer id) {
        return materialMapper.getMaterialLendNumById(id);
    }

    @Override
    public boolean addLendNumById(Integer id , Integer addNum) {
        return materialMapper.updateLendNumById(id,addNum)>0;
    }
}
