package xyz.uben._4cface.infra.persistent.warehouse;

import org.apache.ibatis.annotations.Param;
import xyz.uben.common.infra.mybatis.mapper.annotation.SimpleMapper;
import xyz.uben.common.infra.persistent.mapper.BaseMapper;
import xyz.uben._4cface.domain.model.Warehouse;

import java.util.List;

/**
 * Created by xiong on 2016/11/1.
 */
@SimpleMapper
public interface WarehouseMapper extends BaseMapper<Warehouse> {
    /**
     * 新增仓库别跟信息的关系
     */
    public void insertWarehouse(@Param("warehouseId") Long warehouseId, @Param("infoId") Long infoId);

    /**
     * 删除与仓库有关的关系
     *
     * @param warehouseId
     */
    public void deleteWarehouse(@Param("warehouseId") Long warehouseId);

    /**
     * 查询所有仓库
     *
     * @return
     */
    public List<Warehouse> queryAll();

    /**
     * 跟据名称查询id
     *
     * @param name
     * @return
     */
    public Warehouse queryByName(@Param("name") String name);
}
