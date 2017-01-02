package xyz.uben._4cface.domain.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.solr.SolrRepository;
import xyz.uben._4cface.domain.model.Doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hupanfeng
 * @since 16/11/7
 */
@Service
public class IndexService {

    @Autowired
    private SolrRepository solrRepository;

    /**
     * 根据文档内容或标题等建立索引
     *
     * @param doc
     */
    public void create(Doc doc) {
        Map map = new HashMap();
        map.put("id", doc.getMid());
        map.put("title", doc.getTitle());
        map.put("summary", doc.getSummary());
        map.put("category", doc.getCategoryLevelCode());
        map.put("url", doc.getUrl());
        map.put("content", doc.getContent());
        map.put("docpic", doc.getDocpic());
        solrRepository.addDoc(map);
    }

    /**
     * 删除索引
     *
     * @param docId 文档ID
     */
    public void delete(Long docId) {
        solrRepository.deleteById(docId + "");
    }

    /**
     * 根据文档内容或标题等更新索引
     *
     * @param doc
     */
    public void update(Doc doc) {
        Map map = new HashMap();
        map.put("title", doc.getTitle());
        map.put("summary", doc.getSummary());
        map.put("category", doc.getCategoryLevelCode());
        map.put("url", doc.getUrl());
        map.put("content", doc.getContent());
        map.put("docpic", doc.getDocpic());
        solrRepository.updateDoc(doc.getMid() + "", map);
    }

    /**
     * 通过关键字查询
     *
     * @param key      关键字
     * @param category 类别层级码
     * @param page     分页信息
     * @return
     */
    public List<Doc> search(String key, String category, PageParameter page) {
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(key)) {
            map.put("title", key);
            map.put("summary", key);
            map.put("content", key);
        }
        if (StringUtils.isNotEmpty(category)) {
            map.put("category", category + "*");
        }
        SolrDocumentList solrDocuments = solrRepository.queryDocs(map, page);
        List<Doc> docs = new ArrayList<>();
        if (null != solrDocuments && solrDocuments.size() > 0) {
            solrDocuments.forEach(solrDocument -> {
                        Doc doc = new Doc();
                        doc.setMid(Long.parseLong((String) solrDocument.get("id")));
                        doc.setTitle((String) solrDocument.get("title"));
                        doc.setSummary((String) solrDocument.get("summary"));
                        doc.setUrl((String) solrDocument.get("url"));
                        String pic = (String) solrDocument.get("docpic");
                        doc.setDocpic(pic);
                        docs.add(doc);
                    }
            );
        }
        return docs;
    }


}
