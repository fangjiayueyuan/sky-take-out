package com.sky.controller.user;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: ShopController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/12/12 08:25
 * @version: 1.0
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {
    public static final String Key = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    public Result<String> getStatus(){
        log.info("查询营业状态");
        Integer status = (Integer) redisTemplate.opsForValue().get(Key);
        return Result.success(status == 1?"营业中":"打烊中");
    }
}
