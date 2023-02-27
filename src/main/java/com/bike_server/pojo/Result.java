package com.bike_server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *  业务统一结果集
 *  code:
 *      1xxx 表示业务处理成功
 *      2xxx 表示业务处理失败
 *      3xxx 表示参数存在问题
 *      4xxx 表示内部服务存在异常
 *  msg: 中文提示信息,表明具体的业务处理结果
 *  sub: 英文提示信息,与msg类似,英文描述结果主题
 *  data: 请求要返回的数据,如果不需要放回数据则或者请求失败返回null, 否则返回实际请求数据
 */
@Data
@AllArgsConstructor
@ToString
public class Result {
    private int code;
    private String msg;
    private String sub;
    private Object data;

    public Result() {
        this.setCode(1000);
        this.setSub("Successful");
    }
}
