package xyz.uben._4cface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import xyz.uben.common.domain.model.Grid;
import xyz.uben.common.domain.model.Message;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.interfaces.BaseController;
import xyz.uben.common.rbac.domain.model.SessionUser;
import xyz.uben._4cface.application.CasesService;
import xyz.uben._4cface.domain.model.Cases;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by xiong on 2016/11/9.
 */
@Controller
@RequestMapping("/cases")
public class CasesController extends BaseController {
    @Autowired
    private CasesService casesService;

    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(Cases cases, PageParameter page) {
        Grid grid = new Grid();
        List<Cases> casesList = casesService.queryByPage(cases, page);
        grid.setRows(casesList);
        grid.setTotal(page.getTotalCount());
        return grid;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Cases> queryAll() {
        List<Cases> cases = casesService.queryAll();
        return cases;
    }

    @RequestMapping("/searchGrid")
    @ResponseBody
    public Grid searchGrid(String key, PageParameter page) {
        Grid g = new Grid();
        try {
            page.setSort("createTime");
            page.setOrder("desc");
            List<Cases> casesList = casesService.queryByKey(key, page);
            g.setRows(casesList);
            g.setTotal(casesList.size());
        } catch (Exception e) {
            logger.error("", e);
        }
        return g;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Message add(Cases cases, boolean isMessage, HttpSession session) {
        Message j = new Message();
        cases.setContent(HtmlUtils.htmlUnescape(cases.getContent()));
        cases.setCreateTime(new Date());
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        cases.setAuthor(sessionUser.getUserName());
        cases.setAuthorId(sessionUser.getUserId());
        casesService.addAndPush(cases, isMessage);
        j.setMsg("添加成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Message edit(Cases cases) {
        Message j = new Message();
        cases.setContent(HtmlUtils.htmlUnescape(cases.getContent()));
        casesService.update(cases);
        j.setMsg("编辑成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Message delete(Cases cases) {
        Message j = new Message();
        casesService.delete(cases.getMid());
        j.setMsg("删除成功");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/get/{mid}")
    @ResponseBody
    public Cases cases(@PathVariable("mid") Long mid) {
        Cases cases = casesService.queryById(mid);
        return cases;
    }
}
