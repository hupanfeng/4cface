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
import xyz.uben._4cface.application.ToolInformationService;
import xyz.uben._4cface.domain.model.TooInlformation;

import java.util.List;

/**
 * Created by xiong on 2016/11/8.
 */
@Controller
@RequestMapping("/toolInformation")
public class ToolInformationController extends BaseController {
    @Autowired
    private ToolInformationService toolInformationService;

    /**
     * 分页查询数据
     * @param tooInlformation 查询参数
     * @param page 分页参数
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(TooInlformation tooInlformation, PageParameter page) {
        Grid grid = new Grid();
        try {
            page.setSort("time");
            page.setOrder("desc");
            List<TooInlformation> tooInlformationList = toolInformationService.queryByPage(tooInlformation,page);
            grid.setRows(tooInlformationList);
            grid.setTotal(page.getTotalCount());
        }catch (Exception e){
            logger.error("",e);
        }
        return grid;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Message add(TooInlformation toolInformation) {
        Message j = new Message();
        toolInformationService.add(toolInformation);
        j.setMsg("添加成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Message delete(TooInlformation toolInformation) {
        Message j = new Message();
        toolInformationService.delete(toolInformation.getMid());
        j.setMsg("删除成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Message edit(TooInlformation toolInformation) {
        Message j = new Message();
        toolInformationService.update(toolInformation);
        j.setMsg("编辑成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/{mid}")
    @ResponseBody
    public TooInlformation toolInformation(@PathVariable("mid") Long mid) {
        return toolInformationService.query(mid);
    }
}
