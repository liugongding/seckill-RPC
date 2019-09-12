package com.dingding.seckill.controller;

import com.dingding.seckill.dto.Exposer;
import com.dingding.seckill.dto.SeckillResult;
import com.dingding.seckill.dto.SeckillStatusExecution;
import com.dingding.seckill.entity.Commodity;
import com.dingding.seckill.service.impl.CommodityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 秒杀应用接口
 * @Author liugongding
 * @Date 2019-09-10
 */
@Controller
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommodityServiceImpl commodityService;

    private final String SECKILLEXECUTON = "SeckillExecution";

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Commodity> list = commodityService.getCommodityList();
        //获取列表页
        model.addAttribute("list", list);
        return "list";
    }

    /**
     * seckillId -> commodityId
     *
     * @param commodityId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{commodityId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("commodityId") Integer commodityId, Model model) {
        if (commodityId == null) {
            return "redirect:/seckill/list";
        }
        Commodity commodity = commodityService.getById(commodityId);
        if (commodity == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("commodity", commodity);
        return "seckill_detail";
    }

    /**
     * seckillId -> commodityId
     *
     * @param commodityId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{commodityId}/exposer", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("commodityId") Integer commodityId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = commodityService.exportSeckillUrl(commodityId);
            result = new SeckillResult<>(true, exposer);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new SeckillResult<>(false, e.getMessage());
        }
        return result;
    }

    /**
     * seckillId -> commodityId
     *
     * @param commodityId
     * @param md5
     * @param userPhone
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{commodityId}/{md5}/execute", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public SeckillResult<SeckillStatusExecution> execute(@PathVariable("commodityId") Integer commodityId,
                                                                    @PathVariable("md5") String md5,
                                                                    @CookieValue(value = "killPhone", required = false) Long userPhone) {

        SeckillResult<SeckillStatusExecution> seckillResult = null;
        SeckillStatusExecution seckillExecution = commodityService.executeSeckill(commodityId, userPhone, md5);
        if (seckillExecution != null) {
            return new SeckillResult<>(true,seckillExecution);
        }
        return seckillResult;
    }

//    @ResponseBody
//    @PostMapping(value = "/{commodityId}/seckillStatus")
//    public SeckillResult<SeckillExecution> querySeckillStatus(@PathVariable("commodityId") Integer commodityId,@CookieValue(value = "killPhone", required = false) Long userPhone){
//        SeckillExecution seckillExecution = commodityService.querySeckillStatus(commodityId,userPhone);
//        return new SeckillResult<>(true,seckillExecution);
//    }
    /**
     * 获取系统当前时间
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> execute(Model model) {
        return new SeckillResult<>(true, System.currentTimeMillis());
    }
}
