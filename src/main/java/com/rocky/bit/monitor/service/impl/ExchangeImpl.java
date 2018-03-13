package com.rocky.bit.monitor.service.impl;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.rocky.bit.monitor.service.IExchange;
import com.rocky.bit.monitor.utils.SHA256Util;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by rocky on 18/3/12.
 */
@Service
public class ExchangeImpl implements IExchange{
    private final static Joiner HTTP_REQUEST_PARAM_JOINER = Joiner.on("&").skipNulls();
    private final static String secretKey = "todo:add one";
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
    @Override
    public String btc2usdt() {
        Map<String, String> reqParam = Maps.newHashMap();
        reqParam.put("AccessKeyId", "0fc9aa37-bb9d5856-0685839c-20def");
        reqParam.put("SignatureMethod", "HmacSHA256");
        reqParam.put("SignatureVersion", "2");
        reqParam.put("symbol", "btcusdt");
        DateTime now = DateTime.now();
        String ts = formatter.withZoneUTC().print(now);
        reqParam.put("Timestamp", ts);

        String signatureArg = "";
        signatureArg += "GET\n";
        signatureArg += "api.huobi.pro\n";
        signatureArg += "/market/detail/merged\n";

        List<String> reqParamKeys = Lists.newArrayList(reqParam.keySet());
        reqParamKeys.sort(Ordering.natural());

        List<String> reqParamPairs = Lists.newArrayList();
        for (String reqParamKey : reqParamKeys) {
            String reqParamValue = reqParam.get(reqParamKey);
            String reqParamValueEn = null;
            try {
                reqParamValueEn = URLEncoder.encode(reqParamValue, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String reqParamPair = reqParamKey + "=" + reqParamValueEn;
            reqParamPairs.add(reqParamPair);
        }
        signatureArg += HTTP_REQUEST_PARAM_JOINER.join(reqParamPairs);
        String signature = SHA256Util.encode(secretKey, signatureArg);
        reqParam.put("Signature", signature);
        String mergedData = HttpRequest.get("https://api.huobipro.com/market/detail/merged", reqParam, true)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36")
                .body();
        return mergedData;
    }
}
