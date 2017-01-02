package xyz.uben._4cface.application;

import xyz.uben.common.application.BaseService;
import xyz.uben._4cface.domain.model.DocTemplate;

/**
 * Created by pjy on 2016/11/1.
        */
public interface DocTemplateService extends BaseService<DocTemplate,Long> {
    void addTemplateContent(Long templateId, Integer orderNum, Integer ispub, String templateContent);

    void deleteTemplateContent(Long templateId);
}
