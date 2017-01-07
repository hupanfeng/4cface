package xyz.uben._4cface.application.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.mybatis.page.PageParameter;
import xyz.uben._4cface.application.DocService;
import xyz.uben._4cface.application.ToolInformationService;
import xyz.uben._4cface.domain.model.Doc;
import xyz.uben._4cface.domain.model.TooInlformation;
import xyz.uben._4cface.infra.persistent.member.MemberRepository;
import xyz.uben._4cface.infra.persistent.toolInformation.ToolInformationRepository;

import java.util.List;

/**
 * Created by xiong on 2016/11/7.
 */
@Service
public class ToolInformationServiceImpl implements ToolInformationService {
    @Autowired
    private ToolInformationRepository toolInformationRepository;
    @Autowired
    private MemberRepository warehouseRepository;
    @Autowired
    private DocService docService;

    @Override
    public void add(TooInlformation toolInformation) {
        if (null == toolInformation.getCode()) {
            String random = "";
            random += (int) (Math.random() * 9 + 1);
            for (int i = 0; i < 5; i++) {
                random += (int) (Math.random() * 10);
            }
            Long aLong = Long.parseLong(random);
            toolInformation.setCode(aLong);
        }
        Doc doc = docService.query(Long.parseLong(toolInformation.getToolName()));
        toolInformation.setToolName(doc.getTitle());
        toolInformation.setDocId(doc.getMid());
        toolInformationRepository.add(toolInformation);
    }

    @Override
    public void update(TooInlformation toolInformation) {
        boolean result = toolInformation.getToolName().matches("[0-9]+");
        if (result) {
            Doc doc = docService.query(Long.parseLong(toolInformation.getToolName()));
            toolInformation.setDocId(doc.getMid());
            toolInformation.setToolName(doc.getTitle());
        }
        toolInformationRepository.update(toolInformation);
    }

    @Override
    public void delete(Long mid) {
        toolInformationRepository.delete(mid);
    }

    @Override
    public TooInlformation query(Long mid) {
        return toolInformationRepository.query(mid);
    }

    @Override
    public List<TooInlformation> queryByPage(TooInlformation toolInformation, PageParameter page) {
        return toolInformationRepository.queryByPage(toolInformation, page);
    }

    @Override
    public List<TooInlformation> queryAll() {
        return toolInformationRepository.queryAll();
    }

    /**
     * 根据文档查询工具
     *
     * @param docId 文档id
     * @return
     */
    @Override
    public List<TooInlformation> queryByDoc(Long docId) {
        return toolInformationRepository.queryByDoc(docId);
    }
}
