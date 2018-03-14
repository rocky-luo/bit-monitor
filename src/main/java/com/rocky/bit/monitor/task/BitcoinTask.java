package com.rocky.bit.monitor.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rocky.bit.monitor.dao.IMarketQuotationsDao;
import com.rocky.bit.monitor.model.po.MarketQuotationsPo;
import com.rocky.bit.monitor.service.ICommunicate;
import com.rocky.bit.monitor.service.IExchange;
import com.rocky.bit.monitor.service.IReport;
import com.rocky.bit.monitor.utils.CompareUtil;
import com.rocky.bit.monitor.utils.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by rocky on 18/3/11.
 */
@Component
public class BitcoinTask {
    private final static Logger LOGGER = LoggerFactory.getLogger(BitcoinTask.class);
    @Resource
    private IExchange exchange;
    @Resource
    private IMarketQuotationsDao marketQuotationsDao;
    @Resource
    private ICommunicate communicate;
    @Resource
    private IReport report;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void huobi() {

        String res = exchange.btc2usdt();
        JSONObject resObj = JSON.parseObject(res);
        Long ts = resObj.getLong("ts");
        JSONObject tick = resObj.getJSONObject("tick");
        BigDecimal close = tick.getBigDecimal("close");
        MarketQuotationsPo po = new MarketQuotationsPo();
        po.setSymbol("btcusdt");
        po.setRatio(close);
        DateTime beijing = new DateTime(ts).withZone(DateTimeZone.forID("Etc/GMT+8"));
        po.setTs(beijing.toDate());
        marketQuotationsDao.insert(po);
    }


    @Scheduled(cron = "0 0/10 * * * ?")
    public void fallWarning() {
        DateTime now = DateTime.now();
        DateTime twoDayBefore = now.minusDays(2);
        List<MarketQuotationsPo> oneDayList = marketQuotationsDao.getListByPeriod(twoDayBefore.toDate(), now.toDate());
        if (CollectionUtils.isEmpty(oneDayList)) {
            return;
        }
        MarketQuotationsPo maxOneDay = Collections.max(oneDayList, CompareUtil.ratioComparator());
        MarketQuotationsPo lastPo = oneDayList.get(oneDayList.size() - 1);
        BigDecimal fallRatio = maxOneDay.getRatio().subtract(lastPo.getRatio()).divide(maxOneDay.getRatio(), 6, BigDecimal.ROUND_HALF_UP);
        if (fallRatio.compareTo(BigDecimal.valueOf(0.10)) > 0) {
            String fallRatioPer = fallRatio.multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%";
            String warningFormat = "[48小时大跌警报]:\n";
            warningFormat += "峰值(时间):%s(%s)\n";
            warningFormat += "当前(时间):%s(%s)\n";
            warningFormat += "跌幅:%s";
            String warningMsg = String.format(warningFormat, maxOneDay.getRatio(), DateUtil.format(maxOneDay.getTs()),
                    lastPo.getRatio(), DateUtil.format(lastPo.getTs()), fallRatioPer);
            communicate.sendTelegramMsg(warningMsg);
        }
    }

    @Scheduled(cron = "0 0 9,12,18,22 * * ?")
    public void dynamicReport(){
        DateTime now = DateTime.now();
        DateTime oneDayBefore = now.minusDays(1);
        MarketQuotationsPo max = report.maxBtcUsdtPrice(oneDayBefore, now);
        MarketQuotationsPo min = report.minBtcUsdtPrice(oneDayBefore, now);
        MarketQuotationsPo latest = report.latestBtcUsdtPrice();
        communicate.sendTelegramMsg(formatMsg(max, min, latest));
    }



    private String formatMsg(MarketQuotationsPo max, MarketQuotationsPo min, MarketQuotationsPo latest) {
        String template = "[动态播报]\n" +
                "24小时内\n" +
                "最新(时间):%s(%s)\n" +
                "最高(时间):%s(%s)\n" +
                "最低(时间):%s(%s)\n";
        return String.format(template,latest.getRatio(), DateUtil.format(latest.getTs()),
                max.getRatio(), DateUtil.format(max.getTs()),
                min.getRatio(), DateUtil.format(min.getTs()));
    }
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void everyDayReport(){
        DateTime now = DateTime.now();
    }

}
