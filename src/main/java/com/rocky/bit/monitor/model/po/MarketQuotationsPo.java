package com.rocky.bit.monitor.model.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by rocky on 18/3/12.
 */
public class MarketQuotationsPo {
    private Long id;
    private String symbol;
    private BigDecimal ratio;
    private Date ts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }
}
