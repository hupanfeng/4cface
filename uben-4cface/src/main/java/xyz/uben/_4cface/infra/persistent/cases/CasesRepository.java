package xyz.uben._4cface.infra.persistent.cases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.BaseRepository;
import xyz.uben.common.infra.persistent.IdGenerateStrategy;
import xyz.uben._4cface.domain.model.Cases;

import java.util.List;

/**
 * Created by xiong on 2016/11/9.
 */
@Repository
public class CasesRepository extends BaseRepository<Cases, Long> {
    @Autowired
    private CasesMapper casesMapper;

    @Override
    public void add(Cases cases) {
        if (idGenerateStrategy.equals(IdGenerateStrategy.CUSTOM)) {
            cases.setMid((Long) idGenerator.getNextId());
        }
        getMapper(CasesMapper.class).insert(cases);
    }

    @Override
    public void update(Cases cases) {

        casesMapper.update(cases);
    }

    @Override
    public void delete(Long mid) {
        casesMapper.delete(new Cases(mid));
    }

    @Override
    public Cases query(Long mid) {
        return casesMapper.select(new Cases(mid));
    }


    @Override
    public List<Cases> queryByPage(Cases cases, PageParameter page) {
        return casesMapper.queryByPage(cases, page);
    }

    public List<Cases> queryAll() {
        return casesMapper.queryAll();
    }

    public Cases queryById(Long mid) {
        return casesMapper.queryById(mid);
    }

    /**
     * 根据文档查询应用案例
     *
     * @param docId 文档id
     * @return
     */
    public List<Cases> queryByDocId(Long docId) {
        return casesMapper.queryByDocId(docId);
    }

    /**
     * 根据key分页查询应用案例
     *
     * @param cases 填充好key的案例参数
     * @param page  分页参数
     * @return
     */
    public List<Cases> queryByKey(Cases cases, PageParameter page) {
        return casesMapper.queryByKey(cases, page);
    }
}
