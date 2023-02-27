package com.bike_server.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.bike_server.pojo.Trade;
import com.bike_server.utility.JsonUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliPayService {
    @Autowired
    AlipayClient alipayClient;
    JsonUtility ju = new JsonUtility();

    /**
     * 发起一笔支付宝交易,并且获取支付页面内容
     * @param returnURL 支付完成后跳转页面的url, 当用户支付完成后支付宝服务端会请求此url
     * @param order_id 商家订单号 , 注意这里区别于支付宝官方的 交易号
     * @param subject 订单名称
     * @param amount 本次交易金额
     * @return 返回支付页面的 html 文本内容
     */
    public String getPayPage(String returnURL,String order_id, String subject, double amount){
        String pageContent = null;
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnURL);

        Trade trade = new Trade();
        trade.setOut_trade_no(order_id);
        trade.setSubject(subject);
        trade.setTotal_amount(amount);
        // FAST_INSTANT_TRADE_PAY:固定值,详情参考支付宝开发文档: https://opendocs.alipay.com/open/028r8t?scene=22
        trade.setProduct_code("FAST_INSTANT_TRADE_PAY");

        ObjectMapper mapper = ju.mapperFactory(true, true);
        String tradeJsonString = ju.jsonParser_ByObject(mapper, trade);

        try {
            alipayRequest.setBizContent(tradeJsonString);
            pageContent = alipayClient.pageExecute(alipayRequest).getBody();
        }
        catch (AlipayApiException e){
            System.err.println(tradeJsonString + "请求支付页面异常!");
            e.printStackTrace();
        }
        return pageContent;
    }

    /**
     * 根据支付宝交易号 查询支付宝 交易记录
     * @param trade_no 支付宝交易号
     * @return 查询成功返回一个交易对象实例,失败返回null
     */
    public Trade tradeQuery(String trade_no){
        Trade returnTrade = null;  // 方法返回的对象

        Trade trade = new Trade();
        trade.setTrade_no(trade_no);
        // 设置映射规则, 将trade对象序列化时忽略为null和默认值的属性
        ObjectMapper mapper = ju.mapperFactory(true, true);
        String param = ju.jsonParser_ByObject(mapper, trade);
        String resultStr = null;

        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        try {
            alipayRequest.setBizContent(param);
            resultStr =  alipayClient.execute(alipayRequest).getBody();
            JsonNode jsonNode =  ju.readJsonToNode(mapper, resultStr);
            ObjectNode queryResponse = (ObjectNode) jsonNode.get("alipay_trade_query_response");

            if (10000 == queryResponse.get("code").asInt()) {  // 如果 支付宝接口返回码为10000,表明业务请求成功,
                returnTrade = new Trade();
                returnTrade.setOut_trade_no(queryResponse.get("out_trade_no").asText());
                returnTrade.setTrade_no(queryResponse.get("trade_no").asText());
                returnTrade.setTotal_amount(queryResponse.get("total_amount").asDouble());
                returnTrade.setTrade_status(queryResponse.get("trade_status").asText());
                returnTrade.setBuyer_logon_id(queryResponse.get("buyer_logon_id").asText());
            }
            else {
                System.err.println("支付宝交易查询失败! 提示如下:\n "+resultStr);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return returnTrade;
    }
}
