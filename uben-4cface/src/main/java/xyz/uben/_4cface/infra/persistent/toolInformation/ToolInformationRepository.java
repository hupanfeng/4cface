package xyz.uben._4cface.infra.persistent.toolInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.BaseRepository;
import xyz.uben.common.infra.persistent.IdGenerateStrategy;
import xyz.uben._4cface.domain.model.TooInlformation;
import xyz.uben._4cface.domain.model.Warehouse;
import xyz.uben._4cface.infra.persistent.doctemplate.DocTemplateMapper;
import xyz.uben._4cface.infra.persistent.warehouse.WarehouseRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by xiong on 2016/11/7.
 */
@Repository
public class ToolInformationRepository extends BaseRepository<TooInlformation, Long> {
    @Autowired
    private ToolInformationMapper toolInformationMapper;
    @Autowired
    WarehouseRepository warehouseRepository;

    @Override
    public void add(TooInlformation toolInformation) {
        toolInformation.setTime(new Date());
        if (idGenerateStrategy.equals(IdGenerateStrategy.CUSTOM)) {
            toolInformation.setMid((Long) idGenerator.getNextId());
        }
        Warehouse warehouse = warehouseRepository.query(toolInformation.getWarehouseId());
        toolInformation.setWarName(warehouse.getName());
        getMapper(DocTemplateMapper.class).insert(toolInformation);
        if (null != toolInformation.getWarehouseId()) {
            toolInformationMapper.insertWarehouse(toolInformation.getMid(), toolInformation.getWarehouseId());
        }

    }

    @Override
    public void update(TooInlformation warehouseInformation) {
        toolInformationMapper.update(warehouseInformation);
        toolInformationMapper.deleteWarehouse(warehouseInformation.getMid());
        toolInformationMapper.insertWarehouse(warehouseInformation.getMid(), warehouseInformation.getWarehouseId());
    }

    @Override
    public void delete(Long mid) {
        toolInformationMapper.delete(new TooInlformation(mid));
        toolInformationMapper.deleteWarehouse(mid);

    }

    @Override
    public TooInlformation query(Long mid) {
        TooInlformation warehouseInformation = toolInformationMapper.select(new TooInlformation(mid));
        if (null != warehouseInformation) {
            warehouseInformation.setWarehouseId(toolInformationMapper.queryWarehouse(mid));
        }
        return warehouseInformation;
    }

    @Override
    public List<TooInlformation> queryByPage(TooInlformation warehouseInformation, PageParameter page) {
        return toolInformationMapper.queryByPage(warehouseInformation, page);
    }

    public List<TooInlformation> queryAll() {
        return toolInformationMapper.queryAll();
    }

    /**
     * 根据文档查询工具
     *
     * @param docId 文档id
     * @return
     */
    public List<TooInlformation> queryByDoc(Long docId) {
        return toolInformationMapper.queryByDoc(docId);
    }
}
