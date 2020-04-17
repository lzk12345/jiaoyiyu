package com.jiaoyiyu.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * ImageUploadUtil class
 *
 * @author maochaoying
 * @date 2019/9/3
 */

public class ImageUploadUtil {

    public static String imgUpload(MultipartFile multipartFile){
        String url = "http://116.62.46.30";
        try {
            ClassPathResource resource = new ClassPathResource("tracker.conf");
            ClientGlobal.initByTrackers("116.62.46.30:22122");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorageClient storageClient = new StorageClient(trackerServer, null);
        try {
            byte[] bytes = multipartFile.getBytes();
            String originalFilename = multipartFile.getOriginalFilename();
            int i1 = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(i1 + 1);
            String[] upload_file = storageClient.upload_file(bytes, extName, null);
            for (int i = 0; i < upload_file.length; i++) {
                url += "/" +  upload_file[i];
            }
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


}
