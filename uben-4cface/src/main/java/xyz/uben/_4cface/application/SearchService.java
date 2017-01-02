package xyz.uben._4cface.application;

import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben._4cface.domain.model.Doc;

import java.util.List;

/**
 * @author hupanfeng
 * @since 16/11/7
 */
public interface SearchService {
    /**
     * 按关键字分页查询
     *
     * @param key      关键字
     * @param category 类别码
     * @param page     分页参数
     */
    public List<Doc> search(String key, String category, PageParameter page);
}
