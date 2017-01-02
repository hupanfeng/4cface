package xyz.uben._4cface.application.impl;

import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.util.ConfigUtil;
import xyz.uben.common.wx.application.TextMsgHandler;
import xyz.uben._4cface.application.SearchService;
import xyz.uben._4cface.domain.model.Doc;
import xyz.uben._4cface.domain.service.IndexService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hupanfeng
 * @since 16/11/7
 */
@Service
public class SearchServiceImpl implements SearchService, TextMsgHandler {

    private static final String defaultAppid = ConfigUtil.getConfig("defaultAppid");
    private static final String baseurl = ConfigUtil.getConfig("baseurl") + "/weixin/oauth/forward/";
    @Autowired
    IndexService indexService;

    @Override
    public List<Doc> search(String key, String category, PageParameter page) {
        return indexService.search(key, category, page);
    }

    /**
     * 将用户请求的消息当做查询关键字,返回前10条消息
     *
     * @param msg 请求消息
     * @return
     */
    @Override
    public BaseMsg handle(TextReqMsg msg) {
        List<Doc> docs = indexService.search(msg.getContent(), null, new PageParameter());
        if (null != docs && docs.size() > 0) {
            List<Article> articles = new ArrayList<>();
            for (Doc doc : docs) {
                Article article = new Article();
                article.setTitle(doc.getTitle());
                String url = null;
                try {
                    url = baseurl + defaultAppid + "/" + msg.getFromUserName() + "?redirect=" + URLEncoder.encode("/wap/docItem.html?" + doc.getMid(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                article.setUrl(url);
                article.setPicUrl(ConfigUtil.getConfig("baseurl") + doc.getDocpic());
                article.setDescription(doc.getSummary());
                articles.add(article);
            }
            return new NewsMsg(articles);
        } else {
            return new TextMsg("未搜索到任何信息,请换个关键字搜索,多个关键字之间通过空格分割");
        }
    }
}
