package xyz.uben._4cface.infra.persistent.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.BaseRepository;
import xyz.uben.common.infra.persistent.IdGenerateStrategy;
import xyz.uben.common.rbac.domain.model.Org;
import xyz.uben.common.rbac.domain.model.Role;
import xyz.uben.common.rbac.infra.persistent.org.OrgMapper;
import xyz.uben._4cface.domain.model.Category;
import xyz.uben._4cface.infra.persistent.doctemplate.DocTemplateMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiong on 2016/11/1.
 */
@Repository
public class CategoryRepository extends BaseRepository<Category, Long> {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    DocTemplateMapper docTemplateMapper;
    @Autowired
    OrgMapper orgMapper;

    @Override
    public void add(Category category) {
        if (null == category.getCreateTime()) {
            category.setCreateTime(new Date());
        }
        //主键生成
        if (idGenerateStrategy.equals(IdGenerateStrategy.CUSTOM)) {
            category.setMid((Long) idGenerator.getNextId());
        }
        if (0 != category.getPid()) {
            //获取父类别，设置pLevelCode
            Category r = categoryMapper.select(new Category(category.getPid()));
            category.setpLevelCode(r.getLevelCode());
            //添加类别
            getMapper(CategoryMapper.class).insert(category);
            //设置类别的LevelCode，为 pLevelCode + 类别mid + "-"
            categoryMapper.update(category.generateLevelCode());
//            Category ca = categoryMapper.query(category.getCategoryName());
        } else {
            //添加类别
            getMapper(CategoryMapper.class).insert(category);
            //设置类别的LevelCode，为 pLevelCode + 类别mid + "-"
        }
        categoryMapper.update(category.generateLevelCode());
//        Category ca = categoryMapper.query(category.getCategoryName());
       /* if (null != category.getRoles()) {
            category.getRoles().forEach(roleId -> categoryMapper.insertRole(category.getMid(), roleId));
        }*/
        if (null != category.getOrgIds()) {
            categoryMapper.deleteOrg(category.getMid());
            List<Long> orgIdlist = category.getOrgIds();
            for (Long orgId : orgIdlist) {
                Org org = orgMapper.select(new Org(orgId));
                categoryMapper.insertOrg(category.getMid(), orgId, org.getLevelCode());
            }
        } else {
            category.setOrgIds(categoryMapper.queryOrg(category.getPid()));
            categoryMapper.deleteOrg(category.getMid());
            List<Long> orgIdlist = category.getOrgIds();
            for (Long orgId : orgIdlist) {
                Org org = orgMapper.select(new Org(orgId));
                categoryMapper.insertOrg(category.getMid(), orgId, org.getLevelCode());
            }
        }

    }

    @Override
    public void update(Category category) {
        category.generateLevelCode();
        categoryMapper.update(category);
        if (0 != category.getPid()) {
            List<String> levelCodes = new ArrayList<String>();
            levelCodes.add(category.getLevelCode());
            List<Category> categories = categoryMapper.queryAll(levelCodes);
            categories.forEach(newrole -> {
                //是否是当前类别，如果是使用实参中的pid，如果不是pid不变
                Category prole = category.getMid().equals(newrole.getMid()) ? new Category(category.getPid()) : new Category(newrole.getPid());
                //父类别
                Category r = categoryMapper.select(prole);
                //设置plevelCOde
                newrole.setpLevelCode(r.getLevelCode());
                //更新类别
                categoryMapper.update(newrole.generateLevelCode());
            });
        }

      /*  if (null != category.getRoles()) {
            categoryMapper.deleteRole(category.getMid());
            category.getRoles().forEach(roleId -> categoryMapper.insertRole(category.getMid(), roleId));
        }*/
        if (null != category.getOrgIds()) {
            categoryMapper.deleteOrg(category.getMid());
            List<Long> orgIdlist = category.getOrgIds();
            for (Long orgId : orgIdlist) {
                Org org = orgMapper.select(new Org(orgId));
                categoryMapper.insertOrg(category.getMid(), orgId, org.getLevelCode());
            }
            List<Category> categoryList = categoryMapper.queryByPid(category.getMid());
            for (Category categorys : categoryList) {
                categoryMapper.deleteOrg(categorys.getMid());
                category.setOrgIds(categoryMapper.queryOrg(categorys.getPid()));
                List<Long> orgsIdlist = category.getOrgIds();
                for (Long orgId : orgsIdlist) {
                    Org org = orgMapper.select(new Org(orgId));
                    categoryMapper.insertOrg(categorys.getMid(), orgId, org.getLevelCode());
                }
            }

        }
        else {
            category.setOrgIds(categoryMapper.queryOrg(category.getPid()));
            categoryMapper.deleteOrg(category.getMid());
            List<Long> orgIdlist = category.getOrgIds();
            for (Long orgId : orgIdlist) {
                Org org = orgMapper.select(new Org(orgId));
                categoryMapper.insertOrg(category.getMid(), orgId, org.getLevelCode());
            }
        }
    }

    @Override
    public void delete(Long mid) {
        List<Category> list = categoryMapper.queryByPid(mid);
        categoryMapper.delete(new Category(mid));
        if (list != null && list.size() > 0) {
            for (Category category : list) {
                categoryMapper.deleteOrg(category.getMid());
                categoryMapper.deleteRole(category.getMid());
                docTemplateMapper.deleteCategoryTempByCategoryId(category.getMid());
            }
        }
        categoryMapper.deleteOrg(mid);
        categoryMapper.deleteRole(mid);
        categoryMapper.deleteCategory(mid);
        docTemplateMapper.deleteCategoryTempByCategoryId(mid);


    }

    @Override
    public Category query(Long mid) {
        Category category = categoryMapper.select(new Category(mid));
        if (null != category) {
            category.setOrgIds(categoryMapper.queryOrg(mid));
            category.setRoles(categoryMapper.queryRole(mid));
        }
        return category;
    }

    @Override
    public List<Category> queryByPage(Category category, PageParameter page) {
        return categoryMapper.queryByPage(category, page);
    }

    public List<Long> queryCategoryId(Long roleId) {
        return categoryMapper.queryCategoryId(roleId);
    }

    public List<Long> queryOrgCategoryId(Long orgId) {
        return categoryMapper.queryOrgCategoryId(orgId);
    }

    public Category queryCategory(Long mid) {
        return categoryMapper.queryCategory(mid);
    }

    public void deleteRoleCategory(List<Role> roles) {
        categoryMapper.deleteRoleCategory(roles);
    }

    public List<Category> queryByPid(Long pid) {
        List<Category> categoryList = categoryMapper.queryByPid(pid);
        for (Category categroy : categoryList) {
            List<Category> subCategroy = queryByPid(categroy.getMid());
            categroy.setSubCategorys(subCategroy);
        }
        return categoryList;
    }

    public List<Category> selectAll() {
        List<Category> categoryList = categoryMapper.selectAll();
        for (Category categroy : categoryList) {
            List<Category> subCategroy = queryByPid(categroy.getMid());
            categroy.setSubCategorys(subCategroy);
        }
        return categoryList;
    }

    /**
     * 根据层级码查询分类
     *
     * @param levelCode 分类层级码
     * @return
     */
    public Category queryByLevelCode(String levelCode) {
        return categoryMapper.queryByLevelCode(levelCode);
    }

    /**
     * 根据组织层级码查询类别列表
     *
     * @param orgLevelCode
     * @return
     */
    public List<Category> queryByOrgLevelCode(String orgLevelCode) {
        return categoryMapper.queryByOrgLevelCode(orgLevelCode);
    }

    /**
     * 修改分类组织层级码
     *
     * @param levelCode 新层级码
     * @param orgId     组织id
     */
    public void updateCategoryOrgbyLevelCode(String levelCode, Long orgId) {
        categoryMapper.updateCategoryOrgbyLevelCode(levelCode, orgId);
    }

    /**
     * 删除分类组织，根据组织层级码
     *
     * @param levelCode 组织层级码
     */
    public void deleteCategoryOrgbyLevelCode(String levelCode) {
        categoryMapper.deleteCategoryOrgbyLevelCode(levelCode);
    }
}
