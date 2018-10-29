package com.shisj.study.dinner.order.service;

import com.shisj.study.dinner.order.dataobject.OrderDetail;
import com.shisj.study.dinner.order.dataobject.OrderMaster;
import com.shisj.study.dinner.order.util.KeyGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void findOne() throws Exception {
        OrderMaster save = new OrderMaster();
        save.setOrderId(KeyGenerator.gen());
        save.setPriceSum(new BigDecimal("100.22"));
        save.setStatus(99);

        orderService.saveOrderMaster(save);
        Assert.assertTrue(true);
    }

    @Test
    public void findTwo() throws Exception {
        OrderDetail save = new OrderDetail();
        save.setOrderId(KeyGenerator.gen());
        save.setProductId("1800003");
        save.setProductName("淮南牛肉汤");
        save.setProductPrice(new BigDecimal("23.44"));
        save.setProductIcon("icon-hn");
        orderService.saveOrderDetail(save);
        Assert.assertTrue(true);
    }
}