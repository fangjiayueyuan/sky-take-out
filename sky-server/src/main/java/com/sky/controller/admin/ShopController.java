package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: ShopController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/12/10 22:49
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("admin/shop")
public class ShopController {
    public static final String Key = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置营业状态为：{}",status == 1?"营业中":"打烊中");
        redisTemplate.opsForValue().set(Key, status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result<String> getStatus(){
        log.info("查询营业状态");
        Integer status = (Integer) redisTemplate.opsForValue().get(Key);
        return Result.success(status == 1?"营业中":"打烊中");
    }
}
