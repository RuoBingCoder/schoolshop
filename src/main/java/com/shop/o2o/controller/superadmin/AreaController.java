package com.shop.o2o.controller.superadmin;

import com.shop.o2o.entity.Area;
import com.shop.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 石建雷
 * @date :2019/4/8
 */
@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaService areaService;
    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @GetMapping("/listArea")
    @ResponseBody
    public Map<String, Object> listArea() {
        logger.info("=======start========");
        Long startTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Area> areaList = areaService.getAreaList();
        try {
            map.put("code", 0);
            map.put("message", "成功");
            map.put("data", areaList);
        } catch (Exception e) {

            e.printStackTrace();
        }

        logger.error("======test error======");
        Long endTime = System.currentTimeMillis();
        Long time = endTime - startTime;
        logger.debug("time:{}", time);
        return map;
    }
}
