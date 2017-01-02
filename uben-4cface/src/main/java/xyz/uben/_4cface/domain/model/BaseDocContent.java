package xyz.uben._4cface.domain.model;

/**
 * 模板内容对象
 * Created by pjy on 2016/11/4.
 */
public class BaseDocContent {
    private Long templateId;
    private Integer ispub;
    private String content;
    private Integer orderNum;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Integer getIspub() {
        return ispub;
    }

    public void setIspub(Integer ispub) {
        this.ispub = ispub;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
