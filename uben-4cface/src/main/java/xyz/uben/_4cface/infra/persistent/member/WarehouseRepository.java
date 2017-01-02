package xyz.uben._4cface.infra.persistent.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.BaseRepository;
import xyz.uben._4cface.domain.model.Warehouse;

import java.util.List;

/**
 * Created by xiong on 2016/11/7.
 */
@Repository
public class WarehouseRepository extends BaseRepository<Warehouse, Long> {
    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public void add(Warehouse warehouse) {
        warehouseMapper.insert(warehouse);
    }

    @Override
    public void update(Warehouse warehouse) {
        warehouseMapper.update(warehouse);
    }

    @Override
    public void delete(Long mid) {
        warehouseMapper.delete(new Warehouse(mid));
    }

    @Override
    public Warehouse query(Long mid) {
        return warehouseMapper.select(new Warehouse(mid));
    }

    @Override
    public List<Warehouse> queryByPage(Warehouse warehouse, PageParameter page) {
        return warehouseMapper.queryByPage(warehouse, page);
    }

    public List<Warehouse> queryAll() {
        return warehouseMapper.queryAll();
    }

    public Warehouse queryByName(String name) {
        return warehouseMapper.queryByName(name);
    }
}
