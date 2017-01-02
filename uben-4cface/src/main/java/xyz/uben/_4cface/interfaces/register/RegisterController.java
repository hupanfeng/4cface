package xyz.uben._4cface.interfaces.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.uben.common.domain.model.Message;
import xyz.uben.common.infra.eventbus.EventBusService;
import xyz.uben.common.infra.service.SmsService;
import xyz.uben.common.infra.util.ConfigUtil;
import xyz.uben.common.infra.util.StringFormatter;
import xyz.uben.common.rbac.application.UserService;
import xyz.uben.common.rbac.domain.model.User;
import xyz.uben._4cface.domain.model.event.RegisterMessageEvent;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiong on 2016/11/12.
 */
@Controller
@RequestMapping(value = "register")
public class RegisterController {
    @Autowired
    UserService userService;
    @Autowired
    SmsService smsService;
    @Autowired
    EventBusService eventBusService;

    @RequestMapping("/mobile")
    @ResponseBody
    public User getMobile(String mobile) {
        return userService.queryUser(mobile);
    }

    /**
     * 根据手机号查询
     * 随机产生短信验证
     *
     * @param mobile
     * @return
     */
    @RequestMapping("/random")
    @ResponseBody
    public Message getRandom(String mobile, HttpSession session) {
        Message j = new Message();
        User user = userService.queryUser(mobile);
        if (user == null) {
            String random = "";
            random += (int) (Math.random() * 9 + 1);
            for (int i = 0; i < 5; i++) {
                random += (int) (Math.random() * 10);
            }
            session.setAttribute("random1", random);
            session.setMaxInactiveInterval(900);
            Map data = new HashMap<String, String>();
            data.put("verifycode", random);
            String sms = StringFormatter.format(ConfigUtil.getConfig("sms.template.resetpassword"), data);
            smsService.sendSms(mobile, sms);
            j.setMsg("下发成功");
            j.setSuccess(true);
        } else {
            j.setMsg("手机号已存在");
        }
        return j;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Message reset(User user, String verification, HttpSession session) {
        Message j = new Message();
        List<Long> list = new ArrayList<>();
        list.add(133L);
        user.setRoles(list);
        user.setStateid(-2);
        User user1 = userService.queryUser(user.getMobile());
        if (null == user1) {
            user.setLoginname(user.getMobile());
            userService.add(user);
            j.setMsg("注册成功");
            j.setSuccess(true);
            eventBusService.fireEvent(new RegisterMessageEvent(user.getMobile(), user.getName()));
        } else {
            j.setMsg("手机号码已存在");
        }
        return j;
    }
}
