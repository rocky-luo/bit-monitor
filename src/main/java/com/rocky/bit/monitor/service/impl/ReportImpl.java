package com.rocky.bit.monitor.service.impl;

import com.rocky.bit.monitor.dao.IMarketQuotationsDao;
import com.rocky.bit.monitor.model.po.MarketQuotationsPo;
import com.rocky.bit.monitor.service.IReport;
import com.rocky.bit.monitor.utils.CompareUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by rocky on 18/3/14.
 */
@Service
public class ReportImpl implements IReport{
    @Resource
    private IMarketQuotationsDao marketQuotationsDao;
    @Override
    public MarketQuotationsPo minBtcUsdtPrice(DateTime from, DateTime to) {
        List<MarketQuotationsPo> pos = marketQuotationsDao.getListByPeriod(from.toDate(), to.toDate());
        if (CollectionUtils.isEmpty(pos)) {
            return null;
        }
        return Collections.min(pos, CompareUtil.ratioComparator());

    }

    @Override
    public MarketQuotationsPo maxBtcUsdtPrice(DateTime from, DateTime to) {
        List<MarketQuotationsPo> pos = marketQuotationsDao.getListByPeriod(from.toDate(), to.toDate());
        if (CollectionUtils.isEmpty(pos)) {
            return null;
        }
        return Collections.max(pos, CompareUtil.ratioComparator());
    }

    @Override
    public MarketQuotationsPo latestBtcUsdtPrice() {
        return marketQuotationsDao.latest();
    }
}
