package com.cc.timeTask;

import com.cc.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by cc on 2017/4/19.
 */

@Component
public class MyTimeTaskImpl implements MyTimeTask{
    @Resource
    private FileService fileService;
    private static Logger log =Logger.getLogger(MyTimeTaskImpl.class);


    //每隔三个月执行一次 凌晨零点15分
    @Scheduled(cron="0 15 0 1 1/3 ? ")
    @Override
    public void upload_model20() {
        System.out.println("start time task");
        String filepath=null;
        String upload_url = null;
        String info = null;
        Properties prop = new Properties();
        try {
            prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
            filepath=prop.getProperty("filepath20");
            upload_url=prop.getProperty("upload_url20");
            info=prop.getProperty("info");
        } catch(IOException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        DateFormat format  = new SimpleDateFormat("yyyyMMdd");
        String modeldate = format.format(date);
        File file = new File(filepath+"train_"+modeldate+".RData");
        System.out.println(filepath);

        fileService.uploadFile(file,upload_url,info);
        File tranRFile= new File(filepath+"train.R");
        tranRFile.delete();
        System.out.println("upload success");

    }

    //每隔三个月执行一次 凌晨零点15分
    @Scheduled(cron="0 15 0 1 1/3 ? ")
    @Override
    public void upload_model90() {
        System.out.println("start time task");
        String filepath=null;
        String upload_url = null;
        String info = null;
        Properties prop = new Properties();
        try {
            prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
            filepath=prop.getProperty("filepath90");
            upload_url=prop.getProperty("upload_url90");
            info=prop.getProperty("info");
        } catch(IOException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        DateFormat format  = new SimpleDateFormat("yyyyMMdd");
        String modeldate = format.format(date);
        File file = new File(filepath+"train_"+modeldate+".RData");
        System.out.println(filepath);

        fileService.uploadFile(file,upload_url,info);
        File tranRFile= new File(filepath+"train.R");
        tranRFile.delete();
        System.out.println("upload success");
    }



    //每隔三个月执行一次
    @Scheduled(cron="0 0 0 1 1/3 ?  ")
    @Override
    public void updateDataTrain() {
        fileService.updateDataTrain20();
        fileService.updateDataTrain90();
    }


    //每隔三个月执行一次 凌晨零点10分
    @Scheduled(cron="0 10 0 1 1/3 ? ")
    @Override
    public void train20() {

        String filepath20=null;
        Properties prop = new Properties();
        try {
            prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
            filepath20=prop.getProperty("train_path20");
        } catch(IOException e) {
            e.printStackTrace();
        }


        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("Rscript "+filepath20);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //每隔三个月执行一次 凌晨零点10分
    @Scheduled(cron="0 10 0 1 1/3 ? ")
    @Override
    public void train90() {


        String filepath90=null;


        Properties prop = new Properties();
        try {
            prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
            filepath90=prop.getProperty("train_path90");

        } catch(IOException e) {
            e.printStackTrace();
        }
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("Rscript "+filepath90);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
