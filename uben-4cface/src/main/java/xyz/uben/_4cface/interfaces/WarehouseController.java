package xyz.uben._4cface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.uben.common.domain.model.Grid;
import xyz.uben.common.domain.model.Message;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.interfaces.BaseController;
import xyz.uben._4cface.application.WarehouseService;
import xyz.uben._4cface.domain.model.Warehouse;

import java.util.List;

/**
 * Created by xiong on 2016/11/8.
 */
@Controller
@RequestMapping("/warehouse")
public class WarehouseController extends BaseController {
    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid() {
        Grid grid = new Grid();
        List<Warehouse> warehouses = warehouseService.queryAll();
        grid.setTotal(warehouses.size());
        grid.setRows(warehouses);
        return grid;
    }

    @RequestMapping("/dataGrids")
    @ResponseBody
    public Grid dataGrids(Warehouse warehouse, PageParameter page) {
        Grid grid = new Grid();
        List<Warehouse> list = warehouseService.queryByPage(warehouse, page);
        grid.setRows(list);
        grid.setTotal(page.getTotalCount());
        return grid;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Message add(Warehouse warehouse) {
        Message j = new Message();
        warehouseService.add(warehouse);
        j.setMsg("添加成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Message edit(Warehouse warehouse) {
        Message j = new Message();
        warehouseService.update(warehouse);
        j.setMsg("编辑成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Message delete(Warehouse warehouse) {
        Message j = new Message();
        warehouseService.delete(warehouse.getMid());
        j.setMsg("删除成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/{mid}")
    @ResponseBody
    public Warehouse warehouse(@PathVariable("mid") Long mid) {
        return warehouseService.query(mid);
    }
}
