package com.cc.timeTask;

import com.cc.service.FileService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by cc on 2017/4/19.
 */

@Component
public class MyTimeTaskImpl implements MyTimeTask{
    @Resource
    private FileService fileService;


//    @Scheduled(cron="0/9 * * * * ?")
    @Scheduled(cron="0 2 0 1 * ? ") //每月一号凌晨两点执行
    @Override
    public void upload_model() {
        System.out.println("start time task");
        String filepath=null;
        String upload_url = null;
        String info = null;
        Properties prop = new Properties();
        try {
            prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
            filepath=prop.getProperty("filepath");
            upload_url=prop.getProperty("upload_url");
            info=prop.getProperty("info");
        } catch(IOException e) {
            e.printStackTrace();
        }
        File file = new File(filepath);
        System.out.println(filepath);

        fileService.uploadFile(file,upload_url,info);
        System.out.println("upload success");

    }


    @Scheduled(cron="0 4 0 1 * ? ") //每月一号凌晨两点执行
    @Override
    public void updateDataTrain() {
        fileService.updateDataTrain();
    }
}
