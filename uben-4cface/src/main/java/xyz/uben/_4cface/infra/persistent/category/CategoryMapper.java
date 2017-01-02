package xyz.uben._4cface.infra.persistent.category;

import org.apache.ibatis.annotations.Param;
import xyz.uben.common.infra.mybatis.mapper.annotation.SimpleMapper;
import xyz.uben.common.infra.persistent.mapper.BaseMapper;
import xyz.uben.common.rbac.domain.model.Role;
import xyz.uben._4cface.domain.model.Category;

import java.util.List;

/**
 * Created by xiong on 2016/11/1.
 */
@SimpleMapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 新增类别跟角色的关系
     */
    public void insertRole(@Param("categoryId") Long mid, @Param("roleId") Long roled);

    /**
     * 新增用户所属组织
     *
     * @param mid   用户ID
     * @param orgId 组织结构ID
     */
    public void insertOrg(@Param("categoryId") Long mid, @Param("orgId") Long orgId, @Param("orgLevelCode") String orgLevelCode);

    /**
     * 删除与类别有关角色
     *
     * @param mid 类别id
     */
    public void deleteRole(@Param("categoryId") Long mid);

    /**
     * 删除与类别有关组织
     *
     * @param mid 类别id
     */
    public void deleteOrg(@Param("categoryId") Long mid);

    /**
     * 根据名称查询id
     *
     * @param categoryName
     * @return
     */
    public Category query(@Param("categoryName") String categoryName);

    /**
     * 根据角色id查询
     *
     * @param roleId
     * @return
     */
    public List<Long> queryCategoryId(@Param("roleId") Long roleId);

    /**
     * 根据组织id查询
     *
     * @param orgId
     * @return
     */
    public List<Long> queryOrgCategoryId(@Param("orgId") Long orgId);

    /**
     * 跟据mid查询数据
     *
     * @param mid
     * @return
     */
    public Category queryCategory(@Param("mid") Long mid);

    /**
     * 根据mid查询对应的角色
     *
     * @param mid
     * @return
     */
    public List<Long> queryRole(@Param("categoryId") Long mid);

    /**
     * 根据mid查询对应的组织
     *
     * @param mid
     * @return
     */
    public List<Long> queryOrg(@Param("categoryId") Long mid);

    void deleteRoleCategory(@Param("roles") List<Role> roles);

    /**
     * 查询当前用户的权限角色
     */
    public List<Category> queryAll(@Param("levelCodes") List<String> levelCodes);

    /**
     * 根据pid查询所有类别
     */
    public List<Category> queryByPid(@Param("pid") Long pid);

    /**
     * 查询所有的类别
     */
    public List<Category> selectAll();

    /**
     * 根据层级码查询分类
     *
     * @param levelCode 分类层级码
     * @return
     */
    Category queryByLevelCode(String levelCode);

    /**
     * 根据pid删除
     */
    public void deleteCategory(@Param("pid") Long mid);

    /**
     * 修改分类组织层级码
     *
     * @param levelCode 新层级码
     * @param orgId 组织id
     */
    void updateCategoryOrgbyLevelCode(@Param("levelCode") String levelCode, @Param("orgId") Long orgId);

    /**
     * 删除分类组织，根据组织层级码
     *
     * @param levelCode 组织层级码
     */
    void deleteCategoryOrgbyLevelCode(@Param("levelCode") String levelCode);

    /**
     * 根据组织层级码查询类别列表
     *
     * @param orgLevelCode
     * @return
     */
    public List<Category> queryByOrgLevelCode(@Param("orgLevelCode") String orgLevelCode);
}
