package xyz.uben._4cface.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben._4cface.application.DocTemplateService;
import xyz.uben._4cface.domain.model.DocTemplate;
import xyz.uben._4cface.infra.persistent.doctemplate.DocTemplateRepository;

import java.util.List;

/**
 * Created by pjy on 2016/11/1.
 */
@Service
public class DocTemplateServiceImpl implements DocTemplateService {
    @Autowired
    DocTemplateRepository docTemplateRepository;

    @Override
    public void add(DocTemplate template) {
        docTemplateRepository.add(template);
    }

    @Override
    public void update(DocTemplate template) {
        docTemplateRepository.update(template);
    }

    @Override
    public void delete(Long mid) {
        docTemplateRepository.delete(mid);
    }

    @Override
    public DocTemplate query(Long mid) {
        return docTemplateRepository.query(mid);
    }

    @Override
    public List<DocTemplate> queryByPage(DocTemplate template, PageParameter page) {
        return docTemplateRepository.queryByPage(template, page);
    }

    @Override
    public void addTemplateContent(Long templateId, Integer orderNum, Integer ispub, String templateContent) {
        docTemplateRepository.addTemplateContent(templateId, orderNum, ispub, templateContent);
    }

    @Override
    public void deleteTemplateContent(Long templateId) {
        docTemplateRepository.deleteTemplateContent(templateId);
    }
}
