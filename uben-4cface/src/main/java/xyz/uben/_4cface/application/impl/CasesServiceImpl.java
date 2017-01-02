package xyz.uben._4cface.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.eventbus.EventBusService;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben._4cface.application.CasesService;
import xyz.uben._4cface.domain.model.Cases;
import xyz.uben._4cface.domain.model.event.CasesMessageEvent;
import xyz.uben._4cface.infra.persistent.cases.CasesRepository;

import java.util.List;

/**
 * Created by xiong on 2016/11/9.
 */
@Service
public class CasesServiceImpl implements CasesService{
    @Autowired
    private CasesRepository casesRepository;
    @Autowired
    EventBusService eventBusService;

    @Override
    public void addAndPush(Cases cases,boolean isMessage) {
        casesRepository.add(cases);
        if (isMessage){
            eventBusService.fireEvent(new CasesMessageEvent(cases.getMid()));
        }
    }

    @Override
    public void add(Cases cases) {
        casesRepository.add(cases);
    }

    @Override
    public void update(Cases cases) {
        casesRepository.update(cases);
    }

    @Override
    public void delete(Long mid) {
        casesRepository.delete(mid);
    }

    @Override
    public Cases query(Long mid) {
        return casesRepository.query(mid);
    }

    @Override
    public List<Cases> queryByPage(Cases cases, PageParameter page) {
        return casesRepository.queryByPage(cases, page);
    }

    @Override
    public List<Cases> queryAll() {
        return casesRepository.queryAll();
    }

    @Override
    public Cases queryById(Long mid) {
        return casesRepository.queryById(mid);
    }

    /**
     * 根据文档查询应用案例
     *
     * @param docId 文档id
     * @return
     */
    @Override
    public List<Cases> queryByDocId(Long docId) {
        return casesRepository.queryByDocId(docId);
    }

    /**
     *  根据key分页查询应用案例
     * @param key 查询条件（标题和内容）
     * @param page 分页参数
     * @return
     */
    @Override
    public List<Cases> queryByKey(String key, PageParameter page) {
        Cases cases = new Cases();
        cases.setTitle(key);
        cases.setContent(key);
        return casesRepository.queryByKey(cases,page);
    }
}
