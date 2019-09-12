package com.dingding.seckill.dao;

import com.dingding.seckill.CommodityApplication;
import com.dingding.seckill.entity.Commodity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommodityApplication.class)
@Slf4j
public class CommodityMapperTest {

    @Resource
    private CommodityMapper commodityMapper;

    @Test
    public void reduceNumber() {
        Integer id = 1001;
        Date killTime = new Date();
        log.info("秒杀时间"+ killTime);
        int updateCount = commodityMapper.reduceNumber(id, killTime);
        log.info("结果: {}", updateCount);
    }

    @Test
    public void queryById() {
        long id = 1000;
        Commodity commodity = commodityMapper.queryById(id);
        System.out.println(commodity.getName());
        System.out.println(commodity);
    }

    @Test
    public void queryAll() {
        Integer pageNum = 0;
        Integer pageSize = 2;
        PageHelper.startPage(pageNum,pageSize);
        List<Commodity> seckills = commodityMapper.queryAll();
        PageInfo<Commodity> pageinfo = PageInfo.of(seckills);
        pageinfo.getList().forEach(item -> {
            log.info("秒杀对象:{}", item);
        });
    }
}