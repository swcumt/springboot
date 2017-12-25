package com.baipengx.coin.controller;

import com.baipengx.coin.wechat.message.template.request.BaseTemplateMessage;
import com.baipengx.coin.wechat.message.template.request.BusinessProcessData;
import com.baipengx.coin.wechat.message.template.request.TestMessage;
import com.baipengx.coin.wechat.oauth2.AccessToken;
import com.baipengx.coin.wechat.oauth2.WechatOAuth2Service;
import com.baipengx.coin.wechat.oauth2.WechatUserInfo;
import com.baipengx.coin.wechat.service.SendTemplateMessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
    @Resource
    private WechatOAuth2Service oauthService;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private SendTemplateMessageUtil util;
    @RequestMapping("/authed")
    public void authed(HttpServletRequest request, HttpServletResponse response, String state, String code, @RequestParam(value = "request-uri", required = false) String requestUrl) {
        try {
            AccessToken token = oauthService.getToken(code);
            if(token == null || token.getAccess_token() == null){
                response.sendRedirect(requestUrl);
                return;
            }
            WechatUserInfo user = oauthService.getUserInfo(token.getAccess_token(), token.getOpenid());
            saveSession(request, BaseController.SESSION_USER_KEY, user);
            log.debug("authed user is :", user);
            response.sendRedirect(requestUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/")
    @ResponseBody
    public Object index(HttpServletRequest requset){
        return getCurrentAuthed(requset);
    }

    @GetMapping("/error")
    @ResponseBody
    public Object error(HttpServletRequest requset){
        return getCurrentAuthed(requset);
    }

    @GetMapping("/send")
    @ResponseBody
    public Object send(HttpServletRequest request){
        WechatUserInfo user = (WechatUserInfo) getCurrentAuthed(request);
        BaseTemplateMessage testMsg = new TestMessage();
        testMsg.setTouser(user.getOpenid());
        testMsg.setUrl(getRootPath(request));
        BusinessProcessData data = new BusinessProcessData();
        data.setFirst("hello," + user.getNickname());
        data.setKeyword1(user.getNickname());
        data.setKeyword2(sdf.format(new Date()));
//        data.setKeyword3("egg");
//        data.setKeyword4(new Date());
        data.setRemark("模版消息发送成功了");
        testMsg.setData(data);
        util.sendMsg(testMsg);
        return user;
    }
}
