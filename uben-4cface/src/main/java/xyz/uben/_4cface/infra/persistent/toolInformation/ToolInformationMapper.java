package xyz.uben._4cface.infra.persistent.toolInformation;

import org.apache.ibatis.annotations.Param;
import xyz.uben.common.infra.mybatis.mapper.annotation.SimpleMapper;
import xyz.uben.common.infra.persistent.mapper.BaseMapper;
import xyz.uben._4cface.domain.model.TooInlformation;

import java.util.List;

/**
 * Created by xiong on 2016/11/1.
 */
@SimpleMapper
public interface ToolInformationMapper extends BaseMapper<TooInlformation> {
    /**
     * 新增信息别跟仓库的关系
     */
    public void insertWarehouse(@Param("infoId") Long infoId, @Param("warehouseId") Long warehouseId);

    /**
     * 删除与信息有关的关系
     *
     * @param infoId
     */
    public void deleteWarehouse(@Param("infoId") Long infoId);

    /**
     * 查询仓库全部信息
     */
    public List<TooInlformation> queryAll();

    /**
     * 根据mid查询对应的仓库id
     *
     * @return
     */
    public Long queryWarehouse(@Param("infoId") Long infoId);

    /**
     * 根据文档查询工具
     *
     * @param docId 文档id
     * @return
     */
    List<TooInlformation> queryByDoc(@Param("docId") Long docId);
}
