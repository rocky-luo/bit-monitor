package com.rocky.bit.monitor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by rocky on 18/3/12.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:applicationContext.xml"})
public class IExchangeTest {
    @Resource
    private IExchange exchange;

    @Test
    public void marketData() throws Exception {
        String res = exchange.btc2usdt();
        System.out.println(res);
    }

}