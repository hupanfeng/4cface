package xyz.uben._4cface.application;

import xyz.uben.common.application.BaseService;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben._4cface.domain.model.Cases;

import java.util.List;

/**
 * Created by xiong on 2016/11/9.
 */
public interface CasesService extends BaseService<Cases,Long> {

    /**
     * 新增案例
     * @param cases 案例对象
     * @param isMessage 是否推送微信端消息
     */
    void addAndPush(Cases cases,boolean isMessage);

    public List<Cases> queryAll();

    public Cases queryById(Long mid);

    /**
     * 根据文档查询应用案例
     *
     * @param docId 文档id
     * @return
     */
    List<Cases> queryByDocId(Long docId);

    /**
     * 根据key分页查询应用案例
     * @param key 查询条件（标题和内容）
     * @param page 分页参数
     * @return
     */
    List<Cases> queryByKey(String key, PageParameter page);
}
