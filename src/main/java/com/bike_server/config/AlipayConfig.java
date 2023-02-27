package com.bike_server.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;

@Configuration
public class AlipayConfig {

//    注入Spring容器
    @Bean
    public AlipayClient getAlipayClient(){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.gatewayUrl,
                AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json",
                AlipayConfig.charset,
                AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);
        return  alipayClient;
    }

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000122608700";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCZRoaLynM4FDNc054R0VstijJbKrJkNJiGvGDRZtgeknzmpT5rDVo2ES4gmRivLwjT+G8YjnXH4z/c+GprkR8p92BaBOX5c6Fn0+pqi6UfMOlhNUsharnAKTLr2lSIfUgh+CET/DHxnA0d5Ql6TNetleACFMLwKJ9zSIn7KfxhAuhSdL6vnVnJHankiBTUSe66ykIY1Mit+/iHCgZVJCWcCmbSmqhPTpOlqpSVF6Of4os7O852NX8XAieM786217Ob/J2ADKFg2ig5WSVNYmKUqpWaX6xMSvnmIfQV2RtdZjthFVcE7He7UKd6UVBQVL2EevQrybM0FTep5xYlMLSXAgMBAAECggEAfcm7MY378NHjNRMWblj6Eu2PVfI8ZLAlXK3MZw2X378occC68PAMUMLYsDf0YhCflhYUVnrTfi+pgZb8/zoh8TbrA/VxJr8WfzcS8k2YPXjOjtBF0C7eMi2ddKHBLiJiyD3yJ4yZEEeZHCeTF6NofTS6mfqf4Kf1845cbpuOW63WR7o9Agu0KA2+AqLkMrXV7xCgXbqDFqsUajoPjqAaSgOVdPslNlriUBHt+CavutRXDuPIDzdTWtDytvJBwt3POtfr8usZpny633mtLWxaINWucoAZhh5aQu8KcXwW0uT+O4df+D5unlrWyCp04Ctdzart0z2eb2pEqHAbsdWLAQKBgQDmlQz3VNj9SkpDTG+Sx3hofF3opMYkLmRVAjsH/T1CMAH7b6y0xtg/6CLDNoCLsqv3mp7zSC5GaxWWf5R0IouPvrkRYsoSqK5qFyghgeBBviBNzyjhpguk+CQNPNzTW0k5xTjZjpZ2nIUev6Ic2kcZ+2L7SHtCL3+T4S1ipiNpMQKBgQCqK+ftc1L5DiSrP1TA2j2p3Jtqtes51eynHGfBQzoff9f8ibp1hmfQ7XNq0JzEhy6lvL0wiXT9G2WNjKq0nCTB2QqMyUOQWWbfKltML5vVTeDV8GKjebJJxEB5mT/eYlGgAwbRAfn5hRq2XujxFAnqsuZqcijZq8hg8aMGYiUIRwKBgQCATcHYQlM3pcXq6rZO00WRxlou4LkMWvAjSUl1qZkTokkIEtzj/p1H+jCVtVcT5MjBzzzRHIH7Vwy9IKKHeM11n+e03+aXOVtL1zTSaEVT76pkAXi7Drz9/8F3yD0MMohtZ+u5Sxk4SOHd+T4IlgDoWTR7pJpJesqfi7XIZa/gkQKBgCefqZVksQFFBczKJlXxe0I+GAI08dy5ry0KNC6QFMyY3BX1i/EppqrDX4iToq0wPPBijjrrBs6YvoDDLEx5vyVUAFzf0FegOY/lMPUAcOyuAZhPLOibwaPjio/F21VQ12jGy6OHLkmncJzeZKcd32FC3twSUOi+Ux4fpbIq0CypAoGBAMU6IWz5NSg/2nePF3wQ0VUytO5wr8HjqSAPsMjzsNIaaAQvrKEIzeMq5Ypl6h1PlvlRx0km8BB1CCr3np/9bySKNV5XLfDfu5i6DVag/ABvuXexQKVMnjSkhnbYJpg133AqDlJV4pI7YzGzt6rGuCWC6JtzuWzcbJIp4qBBoX5F";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzVuIurvzRHaP5AI3nqPAfo4JoHX0aRFlJBHnodbVKej9T+Kbw2eC8UoWZ9x337SDKIbbzt5x2FRNXAvd4j6RY8hdQ1UsyMITV0U9rcyMSSPM5g0ZELWLl99uyzld3ojxU2kBNt500qozFb2nTWNR/wodBUeLy6xoL1Lo/v8P5nsUd54JAkGJhz7MYaruGRdyyGhm5OdLWkEtQ3xinAmcrddjgvUZh5cR1o2WKM7+ON1hHgE8HkUAAkP0ZLy84R+u89OJFCEbqzOZizYcsZ3WoKe5c+Oc5GXYB4krQuBNTeFNlVndlLnthNjzTJWYrNDOq+JkKlT+HEWhDkBNvjV9uQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String notify_url = "http://localhost:8081/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8081/AliPayReturn";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "E:\\Code\\共享单车管理系统\\bike_server\\src\\main\\java\\com\\bike_server";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}