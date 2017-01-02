package xyz.uben._4cface.infra.persistent.doctemplate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.BaseRepository;
import xyz.uben.common.infra.persistent.IdGenerateStrategy;
import xyz.uben._4cface.domain.model.Category;
import xyz.uben._4cface.domain.model.DocTemplate;
import xyz.uben._4cface.domain.model.BaseDocContent;
import xyz.uben._4cface.infra.persistent.category.CategoryMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjy on 2016/11/1.
 */
@Repository
public class DocTemplateRepository extends BaseRepository<DocTemplate, Long> {
    @Autowired
    DocTemplateMapper docTemplateMapper;
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 新增知识模板
     *
     * @param template 新增参数对象
     */
    @Override
    public void add(DocTemplate template) {
        if (idGenerateStrategy.equals(IdGenerateStrategy.CUSTOM)) {
            template.setMid((Long) idGenerator.getNextId());
        }
        getMapper(DocTemplateMapper.class).insert(template);
        int isAll = template.getCategoryId().indexOf("-100");
        String[] categoryIds = template.getCategoryId().split(",");
        if(isAll != -1){
            List<Category> categoryList = categoryMapper.queryAll(null);
            for (Category category:categoryList) {
                addCategTemplate(category.getMid(), template.getMid());
            }
        }else{
            for (String categoryId : categoryIds) {
                addCategTemplate(Long.parseLong(categoryId), template.getMid());
            }
        }
    }

    /**
     * 修改知识模板
     *
     * @param template 修改参数对象
     */
    @Override
    public void update(DocTemplate template) {
        docTemplateMapper.update(template);
        //移除所有分类模板管理，再更新
        deleteCategoryTempBymid(template.getMid());
        if(!template.getCategoryId().isEmpty()){
        //添加对应分类模板
            int isAll = template.getCategoryId().indexOf("-100");
            String[] categoryIds = template.getCategoryId().split(",");
            if(isAll != -1){
                List<Category> categoryList = categoryMapper.queryAll(null);
                for (Category category:categoryList) {
                    addCategTemplate(category.getMid(), template.getMid());
                }
            }else{
                for (String categoryId : categoryIds) {
                    addCategTemplate(Long.parseLong(categoryId), template.getMid());
                }
            }
        }
    }

    /**
     * 删除知识模板
     *
     * @param mid 主键
     */
    @Override
    public void delete(Long mid) {
        deleteCategoryTempBymid(mid);
        docTemplateMapper.delete(new DocTemplate(mid));
    }

    /**
     * 单条查询模板
     *
     * @param mid 主键
     * @return
     */
    @Override
    public DocTemplate query(Long mid) {
        DocTemplate docTemplate = docTemplateMapper.select(new DocTemplate(mid));
        docTemplate.setContents(queryContentById(mid));
        docTemplate.setCategoryIds(docTemplateMapper.queryCategoryById(mid));
        return docTemplate;
    }

    /**
     * 分页查询模板
     *
     * @param template 查询参数对象
     * @param page     分页对象
     * @return
     */
    @Override
    public List<DocTemplate> queryByPage(DocTemplate template, PageParameter page) {
        List<Long> templateIds = new ArrayList<Long>();
        List<DocTemplate> docTemplates = new ArrayList<DocTemplate>();
        if (StringUtils.isBlank(template.getCategoryId())) {
            templateIds = queryIdByCategory(null);
            docTemplates = docTemplateMapper.queryByPage(template, page);
        } else {
            templateIds = queryIdByCategory(Long.parseLong(template.getCategoryId()));
            template.setTemplateids(templateIds);
            if(templateIds != null && templateIds.size() > 0){
                docTemplates = docTemplateMapper.queryByPage(template, page);
            }
        }
        return docTemplates;
    }

    /**
     * 通过类别查询模板id串
     *
     * @param categoryId
     * @return
     */
    public List<Long> queryIdByCategory(Long categoryId) {
        return docTemplateMapper.queryIdByCategory(categoryId);
    }

    /**
     * 添加类别模板（关联表）
     *
     * @param categoryId
     * @param templateId
     */
    public void addCategTemplate(Long categoryId, Long templateId) {
        docTemplateMapper.addCategTemplate(categoryId, templateId);
    }

    /**
     * 删除模板和分类的对应
     *
     * @param mid 分类id
     */
    public void deleteCategoryTempBymid(Long mid) {
        docTemplateMapper.deleteCategoryTempBymid(mid);
    }

    /**
     * 删除分类和模板的关系
     *
     * @param categoryId 分类id
     */
    public void deleteCategoryTempByCategoryId(Long categoryId) {
        docTemplateMapper.deleteCategoryTempByCategoryId(categoryId);
    }

    /**
     * 添加模板分段内容
     *
     * @param templateId      模板id
     * @param orderNum        排序号
     * @param ispub           是否公共（1公共，2私有）
     * @param templateContent 分段内容
     */
    public void addTemplateContent(Long templateId, Integer orderNum, Integer ispub, String templateContent) {
        docTemplateMapper.addTemplateContent(templateId, orderNum, ispub, templateContent);
    }

    /**
     * 删除模板的所有内容
     *
     * @param templateId 模板id
     */
    public void deleteTemplateContent(Long templateId) {
        docTemplateMapper.deleteTemplateContent(templateId);
    }

    public List<BaseDocContent> queryContentById(Long templateId){
       return docTemplateMapper.queryContentById(templateId);
    }
}
