package xyz.uben._4cface.domain.model.event;

import xyz.uben.common.infra.eventbus.Event;

/**
 * Created by xiong on 2016/11/16.
 */
public class RegisterMessageEvent implements Event {
    public String mobile;

    public String name;

    public RegisterMessageEvent(String mobile, String name) {
        this.mobile = mobile;
        this.name = name;
    }

    public RegisterMessageEvent() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
