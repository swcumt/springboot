package com.baipengx.coin.wechat.service;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baipengx.coin.wechat.message.template.request.BaseTemplateMessage;
import com.baipengx.coin.wechat.message.template.response.TemplateMsgResponse;
import com.baipengx.common.BaseLogger;
import com.baipengx.util.HttpUtil;
import com.baipengx.util.JsonUtil;

@Component
public class SendTemplateMessageUtil extends BaseLogger {
    private static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    @Autowired
    private TokenService tokenService;

    /**
     * 发送模版消息
     *
     * @param msg
     * @return
     */
    public String sendMsg(BaseTemplateMessage msg) {
        String url = TEMPLATE_URL.replace("ACCESS_TOKEN", tokenService.getToken());
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(JsonUtil.toJson(msg), "utf-8"));
        TemplateMsgResponse response = null;
        try {
            String result = HttpUtil.executePOST(HttpUtil.defaultClient(), httpPost, 1, new HttpClientContext());
            response = JsonUtil.json2Obj(result, TemplateMsgResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SendTemplateMsgException("发送失败了", e);
        }

        if (response.getErrcode() == 0) {
            return response.getMsgid();
        } else if (response.getErrcode() == 43004) {
            throw new SendTemplateMsgException("微信号未关注");
        }
        return null;
    }

    public static class SendTemplateMsgException extends RuntimeException {

        /**
         *
         */
        private static final long serialVersionUID = -1054536187248544055L;

        public SendTemplateMsgException(String message) {
            super(message);
        }

        public SendTemplateMsgException(String message, Throwable e) {
            super(message, e);
        }
    }
}
