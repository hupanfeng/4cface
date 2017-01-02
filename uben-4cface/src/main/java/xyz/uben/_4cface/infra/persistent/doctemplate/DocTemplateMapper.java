package xyz.uben._4cface.infra.persistent.doctemplate;

import org.apache.ibatis.annotations.Param;
import xyz.uben.common.infra.mybatis.mapper.annotation.SimpleMapper;
import xyz.uben.common.infra.persistent.mapper.BaseMapper;
import xyz.uben._4cface.domain.model.DocTemplate;
import xyz.uben._4cface.domain.model.BaseDocContent;

import java.util.List;

/**
 * 模板mapper
 * Created by pjy on 2016/11/1.
 */
@SimpleMapper
public interface DocTemplateMapper extends BaseMapper<DocTemplate> {
    List<Long> queryIdByCategory(@Param("categoryId") Long categoryId);

    void addCategTemplate(@Param("categoryId") Long categoryId, @Param("templateId") Long templateId);

    List<Long> queryCategoryById(@Param("mid") Long mid);

    void deleteCategoryTempBymid(@Param("mid") Long mid);

    void deleteCategoryTempByCategoryId(@Param("categoryId") Long categoryId);

    void addTemplateContent(@Param("templateId") Long templateId, @Param("orderNum") Integer orderNum, @Param("ispub") Integer ispub, @Param("templateContent") String templateContent);

    void deleteTemplateContent(@Param("templateId") Long templateId);

    List<BaseDocContent> queryContentById(@Param("templateId") Long templateId);
}
