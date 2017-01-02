package xyz.uben._4cface.infra.persistent.cases;

import org.apache.ibatis.annotations.Param;
import xyz.uben.common.infra.mybatis.mapper.annotation.SimpleMapper;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben.common.infra.persistent.mapper.BaseMapper;
import xyz.uben._4cface.domain.model.Cases;

import java.util.List;

/**
 * Created by xiong on 2016/11/9.
 */
@SimpleMapper
public interface CasesMapper extends BaseMapper<Cases> {
    public List<Cases> queryAll();

    public Cases queryById(@Param("mid") Long mid);

    /**
     * 根据文档查询应用案例
     *
     * @param docId 文档id
     * @return
     */
    List<Cases> queryByDocId(@Param("docId") Long docId);

    /**
     * 根据key分页查询应用案例
     *
     * @param cases 填充好key的案例参数
     * @param page  分页参数
     * @return
     */
    List<Cases> queryByKey(@Param("cases") Cases cases, @Param("page") PageParameter page);
}
