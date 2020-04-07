package com.zhaolei.material.admin.service;

import com.zhaolei.material.admin.domain.base.Page;
import com.zhaolei.material.admin.domain.base.ServiceResponse;
import com.zhaolei.material.admin.domain.dao.MaterialDO;
import com.zhaolei.material.admin.domain.vo.MaterialResponse;

import java.util.List;

/**
 * @author ZHAOLEI
 */
public interface MaterialService {
    /**
     * 录入物资信息
     * @param materialDO 物资信息
     * @return 录入是否成功
     */
    boolean entry(MaterialDO materialDO);

    /**
     * 根据id逻辑删除物资记录
     * @param id id
     * @return 删除条数
     */
    boolean deleteById(Integer id);

    /**
     * 更新物资信息
     * @param materialDO 更新信息
     * @return 更新条数
     */
    boolean updateById(MaterialDO materialDO);

    /**
     * 根据组织查询物资信息
     * @param orgName 组织名称
     * @param page 分页信息
     * @return 结果集
     */
    ServiceResponse getMaterialByOrg(String orgName, Page page);

    /**
     * 根据物资id查询物资情况
     * @param id 物资id
     * @return 物资信息
     */
    MaterialDO getMaterialById(Integer id);

    /**
     * 根据物资信息查询物资信息（主要是库存）
     * 不能使用快照读去查询，因为在查询过程中可能其他线程已经更改了库存数据，mvcc的版本号并不是事务开启时，而是执行了其中某个操作时
     * 需要使用当前读去查询，在这个事务未结束之前阻塞其他的修改操作
     * @param id 物质id
     * @return 返回物资信息
     */
    MaterialDO getMaterialLendNumById(Integer id);

    /**
     * 增加物资的可借出数量
     * @param id 主键id
     * @param addNum 需要增加的数量
     * @return 返回
     */
    boolean addLendNumById(Integer id, Integer addNum);

    /**
     * 根据传入条件查询对应的物资记录
     * @param materialDO 传入条件
     * @param page 分页信息
     * @return 结果集
     */
    ServiceResponse searchMaterial(MaterialDO materialDO,Page page);
}
