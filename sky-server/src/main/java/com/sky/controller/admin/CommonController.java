package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: CommonController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/12/9 13:10
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("开始上传{}",file);
        // 注意：前端会对不符合规范的图片做拦截，导致可能不存在file对象被传过来，页面报500错误
        if(file == null || file.isEmpty()){
            return Result.error(MessageConstant.FILE_EMPTY);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));

        String fileName = UUID.randomUUID().toString() + extension;
        try{
            String filePath = aliOssUtil.upload(file.getBytes(), fileName);
            return Result.success(filePath);
        }catch (IOException e) {
            log.error("文件上传失败:{}", e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
