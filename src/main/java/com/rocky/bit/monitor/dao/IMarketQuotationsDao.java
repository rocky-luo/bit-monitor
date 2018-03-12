package com.rocky.bit.monitor.dao;

import com.rocky.bit.monitor.model.po.MarketQuotationsPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by rocky on 18/3/12.
 */
@Repository
public interface IMarketQuotationsDao {
    void insert(MarketQuotationsPo po);


    List<MarketQuotationsPo> getListByPeriod(@Param("start") Date start,
                                             @Param("end") Date end);

}
