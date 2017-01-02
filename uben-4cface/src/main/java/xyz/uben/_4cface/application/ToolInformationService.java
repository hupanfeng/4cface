package xyz.uben._4cface.application;

import xyz.uben.common.application.BaseService;
import xyz.uben._4cface.domain.model.TooInlformation;

import java.util.List;

/**
 * Created by xiong on 2016/11/7.
 */
public interface ToolInformationService extends BaseService<TooInlformation, Long> {
    public List<TooInlformation> queryAll();

    /**
     * 根据文档查询工具
     * @param docId 文档id
     * @return
     */
    List<TooInlformation> queryByDoc(Long docId);
}
