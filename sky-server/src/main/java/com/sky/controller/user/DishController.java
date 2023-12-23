package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: DishController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/12/21 23:54
 * @version: 1.0
 */
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    public Result<List<DishVO>> list(Long categoryId){
        // 在redis中查询
        String cacheKey = "dish" + categoryId;
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(cacheKey);
        if(list != null && !list.isEmpty()){
            return Result.success(list);
        }
        // 从数据库中查询
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        list = dishService.listWithFlavor(dish);
        // 放入redis缓存
        redisTemplate.opsForValue().set(cacheKey, list);
        return Result.success(list);

    }


}
