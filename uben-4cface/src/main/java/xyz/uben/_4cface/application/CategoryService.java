package xyz.uben._4cface.application;

import xyz.uben.common.application.BaseService;
import xyz.uben.common.rbac.domain.model.OrgDeleteEvent;
import xyz.uben.common.rbac.domain.model.OrgEditEvent;
import xyz.uben.common.rbac.domain.model.RoleDeleteEvent;
import xyz.uben._4cface.domain.model.Category;

import java.util.List;

/**
 * Created by xiong on 2016/11/1.
 */
public interface CategoryService extends BaseService<Category, Long> {
    /**
     * 修改组时发生
     *
     * @param orgEditEvent
     */
    void onOrgEdit(OrgEditEvent orgEditEvent);

    /**
     * 删除组织时发生
     *
     * @param orgDeleteEvent
     */
    void onOrgDelete(OrgDeleteEvent orgDeleteEvent);

    public List<Long> queryCategoryId(Long roleId);

    public Category queryCategory(Category category);

    public void onRoleDelete(RoleDeleteEvent event);

    public List<Category> queryBypid(Long pid);

    public List<Category> selectAll();

    public List<Long> queryOrgCategoryId(Long orgId);

    /**
     * 根据层级码查询分类
     *
     * @param levelCode 分类层级码
     * @return
     */
    Category queryByLevelCode(String levelCode);

    /**
     * 根据组织层级码查询类别列表
     *
     * @param orgLevelCode
     * @return
     */
    public List<Category> queryByOrgLevelCode(String orgLevelCode);
}
