package xyz.uben._4cface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.uben._4cface.application.MemberService;
import xyz.uben._4cface.domain.model.Member;
import xyz.uben.common.domain.model.Grid;
import xyz.uben.common.domain.model.Message;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.interfaces.BaseController;
import xyz.uben.common.interfaces.annotation.Loggable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author hupanfeng
 * @since 17/1/7
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
    @Autowired
    private MemberService memberService;

    /**
     * 分页查询
     *
     * @param member 查询实体
     * @param page   分页实体
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(Member member, PageParameter page) {
        Grid grid = new Grid();
        try {
            List<Member> memberList = memberService.queryByPage(member, page);
            grid.setRows(memberList);
            grid.setTotal(page.getTotalCount());
        } catch (Exception e) {
            logger.error("", e);
        }
        return grid;
    }

    @RequestMapping("/get/{mid}")
    @ResponseBody
    public Member get(@PathVariable("mid") Long mid) {
        Member member = memberService.query(mid);
        return member;
    }

    /**
     * 添加会员
     *
     * @param member 会员实体
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @Loggable(name = "会员管理", level = Loggable.LOG_LEVEL_MANAGE_M, prefix = "添加会员", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message add(Member member, HttpServletRequest request, HttpSession session) {
        Message m = new Message();
        try {
            memberService.add(member);
            m.setMsg("添加成功");
            m.setSuccess(true);
        } catch (Exception e) {
            logger.error("", e);
            m.setMsg("添加失败");
        }
        return m;
    }

    /**
     * 编辑会员
     *
     * @param member 会员实体
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @Loggable(name = "会员管理", level = Loggable.LOG_LEVEL_MANAGE_M, prefix = "添加会员", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message edit(Member member, HttpServletRequest request, HttpSession session) {
        Message m = new Message();
        try {
            memberService.update(member);
            m.setMsg("编辑成功");
            m.setSuccess(true);
        } catch (Exception e) {
            logger.error("", e);
            m.setMsg("编辑失败");
        }
        return m;
    }

    /**
     * 会员删除
     *
     * @param mid 会员主键
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Loggable(name = "会员管理", level = Loggable.LOG_LEVEL_MANAGE_M, prefix = "删除会员", stype = Loggable.SERIAL_TYPE_TOSTRING)
    public Message delete(Long mid) {
        Message m = new Message();
        try {
            memberService.delete(mid);
            m.setMsg("删除成功");
            m.setSuccess(true);
        } catch (Exception e) {
            logger.error("", e);
            m.setMsg("删除失败");
        }
        return m;
    }
}
