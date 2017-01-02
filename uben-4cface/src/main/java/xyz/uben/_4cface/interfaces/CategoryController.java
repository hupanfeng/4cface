package xyz.uben._4cface.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import xyz.uben.common.domain.model.Grid;
import xyz.uben.common.domain.model.Message;
import xyz.uben.common.interfaces.BaseController;
import xyz.uben.common.rbac.application.UserService;
import xyz.uben.common.rbac.domain.model.SessionUser;
import xyz.uben.common.rbac.domain.model.UserOrg;
import xyz.uben._4cface.application.CategoryService;
import xyz.uben._4cface.domain.model.Category;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiong on 2016/11/1.
 */
@Controller
@RequestMapping("/toolcategory")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    UserService userService;

    /**
     * 根据pid 查询所有分类
     *
     * @param pid
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Category> dataGrid(Long pid) {
        if (null == pid) {
            pid = 0L;
        }
        return categoryService.queryBypid(pid);
    }

    /* @RequestMapping("/dataGrid")
     @ResponseBody
     public Grid dataGrid(Category category, HttpSession session) {
         Grid grid = new Grid();
         SessionUser sessionUser = (SessionUser) session.getAttribute("user");
         User user = userService.queryOne(sessionUser.getUserId(), true);
         List<Long> userRoles = user.getRoles();
         List<Long> mids = new ArrayList<Long>();
         List<Category> categories = new ArrayList<>();
         for (Long l : userRoles) {
             List<Long> list1 = categoryService.queryCategoryId(l);
             for (Long lt : list1) {
                 category = new Category();
                 if (!mids.contains(lt)) {
                     mids.add(lt);
                     category.setMid(lt);
                     categories.add(category);
                 }
             }
         }
         List<Category> categories1 = new ArrayList<>();
         for (Category c : categories) {
             if (null != sessionUser) {
                 category = categoryService.queryCategory(c);
             }
             categories1.add(category);
         }
         grid.setTotal(categories1.size());
         grid.setRows(categories1);
         return grid;
     }*/
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Grid dataGrid(HttpSession session) {
        Grid grid = new Grid();
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        List<UserOrg> userOrgList = sessionUser.getUserOrgs();
        List<Category> categoryList = new ArrayList<>();
        Map<Long, Category> categoryMap = new LinkedHashMap<>();
        for (UserOrg userOrg : userOrgList) {
            List<Category> orgCategoryList = categoryService.queryByOrgLevelCode(userOrg.getOrgLevelCode());
            orgCategoryList.forEach(
                    category -> {
                        categoryMap.putIfAbsent(category.getMid(), category);
                    }
            );
        }
        categoryList.addAll(categoryMap.values());
        grid.setTotal(categoryList.size());
        grid.setRows(categoryList);
        return grid;
    }

    /* @RequestMapping("/listGrid")
     @ResponseBody
     public Grid listGrid(Category category, HttpSession session) {
         Grid grid = new Grid();
         SessionUser sessionUser = (SessionUser) session.getAttribute("user");
         if (sessionUser != null) {
             User user = userService.queryOne(sessionUser.getUserId(), true);
             List<Long> orgIds = user.getOrgIds();
             List<Long> mids = new ArrayList<Long>();
             List<Category> categories = new ArrayList<>();
             for (Long orgId : orgIds) {
                 List<Long> categoryIds = categoryService.queryOrgCategoryId(orgId);
                 for (Long categoryId : categoryIds) {
                     category = new Category();
                     if (!mids.contains(categoryId)) {
                         mids.add(categoryId);
                         category.setMid(categoryId);
                         categories.add(category);
                     }
                 }
             }
             List<Category> categories1ist = new ArrayList<>();
             for (Category categorie : categories) {
                 if (null != sessionUser) {
                     category = categoryService.queryCategory(categorie);
                 }
                 categories1ist.add(category);
             }
             grid.setTotal(categories1ist.size());
             grid.setRows(categories1ist);
         }
         return grid;
     }*/
    @RequestMapping("/listGrids")
    @ResponseBody
    public Grid listGrids() {
        Grid grid = new Grid();
        List<Category> categoryList = categoryService.selectAll();
        grid.setRows(categoryList);
        grid.setTotal(categoryList.size());
        return grid;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Message add(Category category) {
        Message j = new Message();
        category.setCategoryName(HtmlUtils.htmlUnescape(category.getCategoryName()));
        if (category.getPid() == null || category.getPid().toString() == "") {
            category.setPid(0L);
        }
        categoryService.add(category);
        j.setSuccess(true);
        j.setMsg("添加成功");
        return j;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Message update(Category category) {
        Message j = new Message();
        if (category.getPid() == null) {
            category.setPid(0L);
        }
        category.setCategoryName(HtmlUtils.htmlUnescape(category.getCategoryName()));
        categoryService.update(category);
        j.setSuccess(true);
        j.setMsg("修改成功");
        return j;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Message delete(Category category) {
        Message j = new Message();
        categoryService.delete(category.getMid());
        j.setSuccess(true);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 用户查询
     *
     * @return
     */
    @RequestMapping("/{mid}")
    @ResponseBody
    public Category category(@PathVariable("mid") Long mid) {
        return categoryService.query(mid);
    }


}
