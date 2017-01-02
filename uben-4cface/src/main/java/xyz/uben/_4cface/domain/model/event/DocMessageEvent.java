package xyz.uben._4cface.domain.model.event;


import xyz.uben.common.infra.eventbus.Event;

/**
 * Created by pjy on 2016/11/15.
 */
public class DocMessageEvent implements Event {
    private Long docId;

    public DocMessageEvent() {
    }

    public DocMessageEvent(Long docId) {
        this.docId = docId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
}
