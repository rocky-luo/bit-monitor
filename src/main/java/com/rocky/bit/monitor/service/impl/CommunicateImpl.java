package com.rocky.bit.monitor.service.impl;

import com.github.kevinsawicki.http.HttpRequest;
import com.rocky.bit.monitor.service.ICommunicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by rocky on 18/3/13.
 */
@Service
public class CommunicateImpl implements ICommunicate{
    private final static Logger LOGGER = LoggerFactory.getLogger(CommunicateImpl.class);
    @Override
    public void sendTelegramMsg(String text) {
        int code = HttpRequest.post("http://95.163.200.88:8881/bit-notification/message/telegram/chart/565906163")
                .header("Content-Type", "text/plain;charset=UTF-8")
                .send(text)
                .code();
        if (code != 201) {
            LOGGER.error("调用接口发送消息失败!\n返回值为%s\n发送消息:%s", code, text);
        }
    }
}
