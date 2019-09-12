package com.dingding.seckill.service.impl;

import com.dingding.seckill.CommodityApplication;
import com.dingding.seckill.dto.Exposer;
import com.dingding.seckill.dto.SeckillExecution;
import com.dingding.seckill.entity.Commodity;
import com.dingding.seckill.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommodityApplication.class)
@Slf4j
public class CommodityServiceImplTest {

    @Autowired
    private CommodityService commodityService;

    @Test
    public void getCommodityList() {
        List<Commodity> commodityList = commodityService.getCommodityList();
        commodityList.forEach(item -> {
            log.info("秒杀对象:{}", item);
        });
    }

    @Test
    public void getById() {
        Integer commodityId = 1001;
        Commodity commodity = commodityService.getById(commodityId);
        log.info("秒杀对象：{}", commodity);
    }

    @Test
    public void exportSeckillUrl() {
        Integer commodityId = 1002;
        Exposer exposer = commodityService.exportSeckillUrl(commodityId);
        log.info("秒杀地址：{}" , exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        Integer commodityId = 1002;
        Long userPhone = 15586531956L;
        String md5 = "39510cba88dd6fed426232e138ae6292";
    }

    @Test
    public void querySeckillStatus() throws InterruptedException {
        String SECKILLEXECUTON = "SeckillExecution";
        Integer commodityId_userPhone = -1593336227;
        System.out.println(commodityService.querySeckillStatus(commodityId_userPhone));
    }
}