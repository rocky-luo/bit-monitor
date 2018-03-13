package com.rocky.bit.monitor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by rocky on 18/3/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:applicationContext.xml"})
public class ICommunicateTest {
    @Resource
    private ICommunicate communicate;
    @Test
    public void sendTelegramMsg() throws Exception {
        communicate.sendTelegramMsg("I Love u, me me da~");
    }

}