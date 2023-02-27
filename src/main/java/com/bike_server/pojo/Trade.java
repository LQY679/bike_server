package com.bike_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 交易对象实体类,用于映射支付宝api的json公共请求响应参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Trade {
    private String out_trade_no;  // 商家交易号, 即订单号
    private String trade_no; // 交易平台订单号
    private double total_amount; // 交易总金额(包括优惠金额在内)
    private String subject;  // 订单名称
    private String product_code;  // 销售产品码，与支付宝签约的产品码名称
    private String trade_status; // 支付宝订单交易状态
    private String buyer_logon_id; // 付款支付平台账号
}
