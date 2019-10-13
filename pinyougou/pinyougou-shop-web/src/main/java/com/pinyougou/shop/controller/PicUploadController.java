package com.pinyougou.shop.controller;

import com.pinyougou.common.util.FastDFSClient;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/upload")
@RestController
public class PicUploadController {

    /**
     * 上传并保存文件
     * @param file 上传的文件
     * @return 操作结果（如果上传成功则图片地址放置在了message中，如果失败则提示）
     */
    @PostMapping
    public Result upload(MultipartFile file){
        Result result = Result.fail("上传图片失败");
        try {
            //1、上传图片文件到FastDFS
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastdfs/tracker.conf");
            //文件后缀名
            String file_ext_name = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);

            //图片可访问地址
            String url = fastDFSClient.uploadFile(file.getBytes(), file_ext_name);

            result = Result.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2、返回结果
        return result;
    }
}
