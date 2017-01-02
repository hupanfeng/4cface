package xyz.uben._4cface.domain.model.event;

import xyz.uben.common.infra.eventbus.Event;

/**
 * Created by pjy on 2016/11/22.
 */
public class CasesMessageEvent implements Event {
    private Long casesId;

    public CasesMessageEvent() {
    }

    public CasesMessageEvent(Long casesId) {
        this.casesId = casesId;
    }

    public Long getCasesId() {
        return casesId;
    }

    public void setCasesId(Long casesId) {
        this.casesId = casesId;
    }
}

