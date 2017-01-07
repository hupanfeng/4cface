package xyz.uben._4cface.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben._4cface.application.WarehouseService;
import xyz.uben._4cface.domain.model.Warehouse;
import xyz.uben._4cface.infra.persistent.member.MemberRepository;

import java.util.List;

/**
 * Created by xiong on 2016/11/7.
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private MemberRepository warehouseRepository;

    @Override
    public void add(Warehouse warehouse) {
        warehouseRepository.add(warehouse);
    }

    @Override
    public void update(Warehouse warehouse) {
        warehouseRepository.update(warehouse);
    }

    @Override
    public void delete(Long mid) {
        warehouseRepository.delete(mid);
    }

    @Override
    public Warehouse query(Long mid) {
        return warehouseRepository.query(mid);
    }

    @Override
    public List<Warehouse> queryByPage(Warehouse warehouse, PageParameter page) {
        return warehouseRepository.queryByPage(warehouse, page);
    }

    @Override
    public List<Warehouse> queryAll() {
        return warehouseRepository.queryAll();
    }
}
