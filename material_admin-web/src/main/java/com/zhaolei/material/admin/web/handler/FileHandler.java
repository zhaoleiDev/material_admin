package com.zhaolei.material.admin.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * 文件处理
 * @author ZHAOLEI
 */
@Component
@Slf4j
public class FileHandler {
    /**
     * 这两个值的映射关系需要在tomcat中配置
     */
    private static final String REAL_PATH = "D:\\unload\\";
    private static final String VM_PATH = "/onload/";
    /**
     * 将文件写入磁盘并且返回虚拟地址
     * @param file 文件
     * @return 返回值
     */
    public String upload(MultipartFile file){
        //获取图片原始名称
        String originalFileName = file.getOriginalFilename()+"";
        //新图片名称
        String newFileName = UUID.randomUUID()
                + originalFileName.substring(originalFileName
                .lastIndexOf("."));
        File realFile = new File(REAL_PATH+newFileName);
        try {
            //将内存中的文件写入磁盘
            file.transferTo(realFile);
        } catch (IOException e) {
            log.error("图片写入磁盘失败",e);
        }
        return VM_PATH+newFileName;
    }
}
