package xyz.uben._4cface.application.impl;

import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.eventbus.Subscriber;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.rbac.domain.model.OrgDeleteEvent;
import xyz.uben.common.rbac.domain.model.OrgEditEvent;
import xyz.uben.common.rbac.domain.model.Role;
import xyz.uben.common.rbac.domain.model.RoleDeleteEvent;
import xyz.uben.common.rbac.infra.persistent.org.OrgRepository;
import xyz.uben.common.rbac.infra.persistent.user.UserRepository;
import xyz.uben._4cface.application.CategoryService;
import xyz.uben._4cface.domain.model.Category;
import xyz.uben._4cface.infra.persistent.category.CategoryRepository;

import java.util.List;

/**
 * Created by xiong on 2016/11/1.
 */
@Service
public class CategoryServiceImpl extends Subscriber implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrgRepository orgRepository;

    @Subscribe
    public void onRoleDelete(RoleDeleteEvent event) {
        List<Role> roles = event.getRoles();
        categoryRepository.deleteRoleCategory(roles);
    }

    /**
     * 组织levelCode变更时发生
     *
     * @param
     */
    @Subscribe
    @Override
    public void onOrgEdit(OrgEditEvent orgEditEvent) {
        String newLevelCode = orgEditEvent.getNewOrgLevelCode();
        //修改类别组织
        categoryRepository.updateCategoryOrgbyLevelCode(newLevelCode, orgEditEvent.getOrgId());
    }

    /**
     * 删除组织时发生
     *
     * @param
     */
    @Subscribe
    @Override
    public void onOrgDelete(OrgDeleteEvent orgDeleteEvent) {
        categoryRepository.deleteCategoryOrgbyLevelCode(orgDeleteEvent.getNewOrgLevelCode());
    }

    @Override
    public List<Category> queryBypid(Long pid) {
        return categoryRepository.queryByPid(pid);
    }

    @Override
    public List<Category> selectAll() {
        return categoryRepository.selectAll();
    }

    @Override
    public List<Long> queryOrgCategoryId(Long orgId) {
        return categoryRepository.queryOrgCategoryId(orgId);
    }

    @Override
    public void add(Category category) {
        categoryRepository.add(category);
    }

    @Override
    public void update(Category category) {
        categoryRepository.update(category);
    }

    @Override
    public void delete(Long mid) {
        categoryRepository.delete(mid);
    }

    @Override
    public Category query(Long mid) {
       Category category= categoryRepository.query(mid);
        if(category.getPid()==0){
            category.setPid(null);
        }
        return category;
    }

    @Override
    public List<Category> queryByPage(Category category, PageParameter page) {
        return categoryRepository.queryByPage(category, page);
    }

    @Override
    public List<Long> queryCategoryId(Long roleId) {
        return categoryRepository.queryCategoryId(roleId);
    }

    @Override
    public Category queryCategory(Category category) {
        return categoryRepository.queryCategory(category.getMid());
    }

    /**
     * 根据层级码查询分类
     *
     * @param levelCode 分类层级码
     * @return
     */
    @Override
    public Category queryByLevelCode(String levelCode) {
        return categoryRepository.queryByLevelCode(levelCode);
    }

    @Override
    public List<Category> queryByOrgLevelCode(String orgLevelCode) {
        return categoryRepository.queryByOrgLevelCode(orgLevelCode);
    }
}
