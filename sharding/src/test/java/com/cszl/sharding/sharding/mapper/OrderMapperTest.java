package com.cszl.sharding.sharding.mapper;

import com.cszl.sharding.sharding.ShardingApplication;
import com.cszl.sharding.sharding.domain.OrderDO;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by liuManDong on 2020/8/12
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSelectById() { // 测试从库的负载均衡
        for (int i = 0; i < 2; i++) {
            OrderDO order = orderMapper.selectById(1);
            System.out.println(order);
        }
    }

    @Test
    public void testSelectById02() { // 测试强制访问主库
        try (HintManager hintManager = HintManager.getInstance()) {
            // 设置强制访问主库
            hintManager.setMasterRouteOnly();
            // 执行查询
            OrderDO order = orderMapper.selectById(1);
            System.out.println(order);
        }
    }

    @Test
    public void testInsert() { // 插入
        OrderDO order = new OrderDO();
        order.setUserId(10);
        orderMapper.insert(order);
    }
}