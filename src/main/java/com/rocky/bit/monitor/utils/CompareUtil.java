package com.rocky.bit.monitor.utils;

import com.rocky.bit.monitor.model.po.MarketQuotationsPo;

import java.util.Comparator;

/**
 * Created by rocky on 18/3/14.
 */
public class CompareUtil {

    public final static Comparator<MarketQuotationsPo> ratioComparator() {
        return new Comparator<MarketQuotationsPo>() {
            @Override
            public int compare(MarketQuotationsPo o1, MarketQuotationsPo o2) {
                return o1.getRatio().compareTo(o2.getRatio());
            }
        };
    }
}
