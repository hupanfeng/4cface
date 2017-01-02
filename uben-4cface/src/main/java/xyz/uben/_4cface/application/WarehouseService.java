package xyz.uben._4cface.application;

import xyz.uben.common.application.BaseService;
import xyz.uben._4cface.domain.model.Warehouse;

import java.util.List;

/**
 * Created by xiong on 2016/11/7.
 */
public interface WarehouseService extends BaseService<Warehouse, Long> {
    public List<Warehouse> queryAll();
}
