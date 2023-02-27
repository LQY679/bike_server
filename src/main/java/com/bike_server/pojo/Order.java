package com.bike_server.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 订单映射实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /* 交易类型: 常量值
    * 使用第三方交易平台的交易类型,可选值:AliPay  (支付宝)WeChatPay (微信支付)
    *  */
    public static final String AliPay = "Ali_Pay";
    public static final String WeChatPay = "WeChat_Pay";

    /* 订单状态: 常量值
    * 订单的状态:
        BE_ING:表示订单正在进行中, 订单已经被创建并且正在计时
        WAIT_PAY:  表示订单已经结束计时,正等待支付
        FINISH: 表示订单已经完成,说明用户已经完成支付
        CLOSE: 表示订单被关闭,表示退款成功
    *  */
    public static final String BeIng = "Be_Ing";
    public static final String WaitPay = "Wait_Pay";
    public static final String Finished = "Finished";
    public static final String Close = "Close";

//    订单编号, 由用户id,车辆编号和时间戳三者用 - 连接
    private String order_id;
//    第三方交易号
    private String trade_no;
//   交易类型
    private String trade_type;
    private double amount;
    private String order_status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_time;
}
