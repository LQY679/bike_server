package com.bike_server;

import com.alipay.api.AlipayClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AliPayTest {

    @Autowired
    AlipayClient alipayClient;
    @Test
    public void test01(){
        System.out.println(alipayClient);
    }
}
