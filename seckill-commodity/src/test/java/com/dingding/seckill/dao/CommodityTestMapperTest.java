package com.dingding.seckill.dao;

import com.dingding.seckill.CommodityApplication;
import com.dingding.seckill.entity.Commodity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommodityApplication.class)
@Slf4j
public class CommodityTestMapperTest {

    @Resource
    private CommodityTestMapper commodityTestMapper;

    @Test
    public void insertCommodity() {
        Commodity commodity = new Commodity();
        commodity.setCommodityId(1004);
        commodity.setName("2000秒杀prodair");
        commodityTestMapper.insertCommodity(commodity);
    }
}