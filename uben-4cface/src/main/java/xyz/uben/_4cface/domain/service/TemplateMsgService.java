package xyz.uben._4cface.domain.service;

import com.github.sd4324530.fastweixin.api.TemplateMsgAPI;
import com.github.sd4324530.fastweixin.api.entity.TemplateMsg;
import com.github.sd4324530.fastweixin.api.entity.TemplateParam;
import com.github.sd4324530.fastweixin.api.response.SendTemplateResponse;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.uben.common.infra.eventbus.EventBusService;
import xyz.uben.common.infra.eventbus.Subscriber;
import xyz.uben.common.infra.util.ConfigUtil;
import xyz.uben.common.rbac.domain.model.User;
import xyz.uben.common.rbac.infra.persistent.user.UserRepository;
import xyz.uben.common.wx.application.FanService;
import xyz.uben.common.wx.domain.ApiConfigFactory;
import xyz.uben.common.wx.domain.Fan;
import xyz.uben._4cface.domain.model.Cases;
import xyz.uben._4cface.domain.model.Doc;
import xyz.uben._4cface.domain.model.event.CasesMessageEvent;
import xyz.uben._4cface.domain.model.event.DocMessageEvent;
import xyz.uben._4cface.domain.model.event.RegisterMessageEvent;
import xyz.uben._4cface.infra.persistent.cases.CasesRepository;
import xyz.uben._4cface.infra.persistent.doc.DocRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pjy on 2016/11/16.
 */
@Service
public class TemplateMsgService extends Subscriber {

    private static final String defaultAppid = ConfigUtil.getConfig("defaultAppid");
    private static final String baseurl = ConfigUtil.getConfig("baseurl") + "/weixin/oauth/forward/";
    private static final String docTemplate = ConfigUtil.getConfig("docTemplate");
    private static final String registerTemplate = ConfigUtil.getConfig("registerTemplate");
    private static final String casesTemplate = ConfigUtil.getConfig("casesTemplate");

    @Autowired
    DocRepository docRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CasesRepository casesRepository;
    @Autowired
    FanService fanService;
    @Autowired
    private ApiConfigFactory apiConfigFactory;
    @Autowired
    EventBusService eventBusService;


    @Subscribe
    public void onDocMessage(DocMessageEvent docMessageEvent) {
        Long docId = docMessageEvent.getDocId();
        Doc doc = docRepository.query(docId);
        TemplateMsg templateMsg = new TemplateMsg();
        List<Fan> fanList = fanService.queryAllFanByAppid(defaultAppid);
        for (Fan fan : fanList) {
            templateMsg.setTouser(fan.getOpenid());
            templateMsg.setTemplateId(docTemplate);
            templateMsg.setData(getDocData(doc));
            templateMsg.setTopcolor("#173177");
            String url  = null;
            try {
                url = baseurl + defaultAppid + "/" + fan.getOpenid() + "?redirect=" + URLEncoder.encode("/wap/docItem.html?"+docId,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            templateMsg.setUrl(url);
            SendTemplateResponse response = new TemplateMsgAPI(apiConfigFactory.getApiConfig(defaultAppid)).send(templateMsg);
        }
    }

    private Map<String, TemplateParam> getDocData(Doc doc) {
        Map<String, TemplateParam> data = new HashMap<String, TemplateParam>();
        TemplateParam first = new TemplateParam();
        TemplateParam keyword1 = new TemplateParam();
        TemplateParam keyword2 = new TemplateParam();
        TemplateParam keyword3 = new TemplateParam();
        TemplateParam remark = new TemplateParam();
        first.setValue("有新的工具文档发布");
        first.setColor("#173177");
        keyword1.setValue(userRepository.query(doc.getAuthor()).getName());
        keyword1.setColor("#173177");
        keyword2.setValue(doc.getTitle());
        keyword2.setColor("#173177");
//        keyword3.setValue(DateFormatUtils.format(doc.getCreateTime(), "yyyy-MM-dd"));
        keyword3.setValue("");
        keyword3.setColor("#173177");
        remark.setValue("点击查看文档详情");
        remark.setColor("#173177");
        data.put("first", first);
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        data.put("remark", remark);
        return data;
    }

    @Subscribe
    public void onRegisterMessage(RegisterMessageEvent registerMessageEvent) {
        String mobile = registerMessageEvent.getMobile();
        List<User> userList = userRepository.queryByResourceUrl("/rbac/user/unlock");
        TemplateMsg templateMsg = new TemplateMsg();
        for (User userlist : userList) {
            Integer userid = Integer.parseInt(userlist.getMid().toString());
            List<Fan> List = fanService.queryFan(defaultAppid, userid);
            for (Fan fan : List) {
                templateMsg.setTouser(fan.getOpenid());
                templateMsg.setTemplateId(registerTemplate);
                templateMsg.setData(getRegisterData(registerMessageEvent));
                templateMsg.setTopcolor("#173177");
                /*String url = baseurl + "/wap/check.html?" + mobile;*/
                String url  = null;
                try {
                    url = baseurl + defaultAppid + "/" + fan.getOpenid() + "?redirect=" + URLEncoder.encode("/wap/check.html?" + mobile,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                templateMsg.setUrl(url);
                SendTemplateResponse response = new TemplateMsgAPI(apiConfigFactory.getApiConfig(defaultAppid)).send(templateMsg);
            }
        }
    }

    private Map<String, TemplateParam> getRegisterData(RegisterMessageEvent registerMessageEvent) {
        Map<String, TemplateParam> data = new HashMap<String, TemplateParam>();
        TemplateParam first = new TemplateParam();
        TemplateParam keyword1 = new TemplateParam();
        TemplateParam keyword2 = new TemplateParam();
        TemplateParam remark = new TemplateParam();
        String name = registerMessageEvent.getName();
        first.setValue("有新的用户注册，请尽快审核！");
        keyword1.setValue(name);
        keyword2.setValue(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        remark.setValue("点击查看详情，审核");
        data.put("first", first);
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("remark", remark);
        return data;
    }

    @Subscribe
    public void onCasesMessage(CasesMessageEvent casesMessageEvent) {
        Long caseId = casesMessageEvent.getCasesId();
        Cases cases = casesRepository.queryById(caseId);
        TemplateMsg templateMsg = new TemplateMsg();
        List<Fan> fanList = fanService.queryAllFanByAppid(defaultAppid);
        for (Fan fan : fanList) {
            templateMsg.setTouser(fan.getOpenid());
            templateMsg.setTemplateId(casesTemplate);
            templateMsg.setData(getCasesData(cases));
            templateMsg.setTopcolor("#173177");
            String url  = null;
            try {
                url = baseurl + defaultAppid + "/" + fan.getOpenid() + "?redirect=" + URLEncoder.encode("/wap/caseItem.html?"+caseId,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            templateMsg.setUrl(url);
            SendTemplateResponse response = new TemplateMsgAPI(apiConfigFactory.getApiConfig(defaultAppid)).send(templateMsg);
        }
    }

    private Map<String, TemplateParam> getCasesData(Cases cases) {
        Map<String, TemplateParam> data = new HashMap<String, TemplateParam>();
        TemplateParam first = new TemplateParam();
        TemplateParam keyword1 = new TemplateParam();
        TemplateParam keyword2 = new TemplateParam();
        TemplateParam keyword3 = new TemplateParam();
        TemplateParam remark = new TemplateParam();
        first.setValue("有新的应用案例发布");
        first.setColor("#173177");
        keyword1.setValue(cases.getAuthor());
        keyword2.setValue(cases.getTitle());
        keyword3.setValue("无");
        remark.setValue("点击查看案例详情");
        remark.setColor("#173177");
        data.put("first", first);
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        data.put("remark", remark);
        return data;
    }
}
