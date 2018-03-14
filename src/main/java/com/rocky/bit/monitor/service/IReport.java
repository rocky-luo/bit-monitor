package com.rocky.bit.monitor.service;

import com.rocky.bit.monitor.model.po.MarketQuotationsPo;
import org.joda.time.DateTime;

/**
 * Created by rocky on 18/3/14.
 */
public interface IReport {

    MarketQuotationsPo minBtcUsdtPrice(DateTime from, DateTime to);

    MarketQuotationsPo maxBtcUsdtPrice(DateTime from, DateTime to);

    MarketQuotationsPo latestBtcUsdtPrice();
}
