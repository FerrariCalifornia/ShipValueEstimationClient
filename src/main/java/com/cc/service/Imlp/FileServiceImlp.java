package com.cc.service.Imlp;

import com.cc.service.FileService;
import com.cc.timeTask.MyTimeTaskImpl;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.springframework.stereotype.Service;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by cc on 2017/4/19.
 */

@Service("fileService")
public class FileServiceImlp implements FileService {
    @Override
    public void uploadFile(File file, String url, String info) {
        if (!file.exists()) {
            System.err.println("file not exists");
            return;
        }
        System.err.println("start fileService upload");
        PostMethod postMethod = new PostMethod(url);
        try {
            //FilePart：用来上传文件的类
            FilePart fp = new FilePart("file", file);
            //StringPart：用来上传字符串的类
            StringPart sp = new StringPart("info",info);

            Part[] parts = { sp,fp };

            //对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
            postMethod.setRequestEntity(mre);
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置连接时间
            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                file.delete();
                System.out.println(postMethod.getResponseBodyAsString());
            } else {
                System.out.println("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放连接
            postMethod.releaseConnection();
        }


    }




    @Override
    public void updateDataTrain20() {


        String train_data_path = null;
        String data_train_csv_path = null;
        Properties prop = new Properties();
        try {
            prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
            train_data_path=prop.getProperty("train_data_path");
            data_train_csv_path=prop.getProperty("data_train_csv_path20");
        } catch(IOException e) {
            e.printStackTrace();
        }
        //找到文件夹中用户最近上传的csv文件
        File uploadFiles = new File(train_data_path);
        //File [] files = uploadFiles.listFiles();
        String [] uploadFilesNamesOrign = uploadFiles.list();
        String [] uploadFilesNames = uploadFiles.list();
        //String userName = uploadFilesNames[0].substring(0, uploadFilesNames[0].lastIndexOf('.') - 14);
        int uploadFilesSize = uploadFilesNames.length;

        //当文件夹中的文件数不为0的时候才做拼接
        if(uploadFilesSize != 0){
            //取到文件夹中每一个csv文件，拼接到data_train.csv后面
            for(int i = 0;i < uploadFilesSize;i ++){
                //得到每一个csv文件全路径
                String inputFileNameString = train_data_path + uploadFilesNamesOrign[i];

                //将用户上传的csv文件补全
                //读取用户新上传的csv文件(按照utf-8格式来读)
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(inputFileNameString);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(fis, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(isr);

                //按行读取csv文件内容
                String line = null;
                //存储读取到的csv文件内容
                String FileContent = "";
                //计数器(用户上传的csv文件第一行的头文件不需要写到data_train.csv中，只需要写入数据行)
                int LineNumber = 1;
                //使用当前时间(YYYYMMDDHHMMSS)作为编号
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
                String shipIDStr=format.format(date);
                //同一个用户文件中的编号值依次增加1
                long shipID = Long.parseLong(shipIDStr);

                try{
                    while ((line = br.readLine()) != null) {
                        //给每一行补上编号
                        if(LineNumber != 1){
                            FileContent += String.valueOf(shipID) + ",";
                            FileContent += line;
                            FileContent += "\r\n"; // 补上换行符
                        }
                        LineNumber++;
                        shipID++;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(FileContent);

                //将读取到的用户上传csv文件的内容续写到data_train.csv中(按照utf-8格式)
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(data_train_csv_path,true);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(fos, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    osw.write(FileContent);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    osw.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //关闭文件流
                try {
                    fis.close();
                    isr.close();
                    fos.close();
                    osw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public void updateDataTrain90() {


        String train_data_path = null;
        String data_train_csv_path = null;
        Properties prop = new Properties();
        try {
            prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
            train_data_path=prop.getProperty("train_data_path");
            data_train_csv_path=prop.getProperty("data_train_csv_path90");
        } catch(IOException e) {
            e.printStackTrace();
        }
        //找到文件夹中用户最近上传的csv文件
        File uploadFiles = new File(train_data_path);
        //File [] files = uploadFiles.listFiles();
        String [] uploadFilesNamesOrign = uploadFiles.list();
        String [] uploadFilesNames = uploadFiles.list();
        //String userName = uploadFilesNames[0].substring(0, uploadFilesNames[0].lastIndexOf('.') - 14);
        int uploadFilesSize = uploadFilesNames.length;

        //当文件夹中的文件数不为0的时候才做拼接
        if(uploadFilesSize != 0){
            //取到文件夹中每一个csv文件，拼接到data_train.csv后面
            for(int i = 0;i < uploadFilesSize;i ++){
                //得到每一个csv文件全路径
                String inputFileNameString = train_data_path + uploadFilesNamesOrign[i];

                //将用户上传的csv文件补全
                //读取用户新上传的csv文件(按照utf-8格式来读)
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(inputFileNameString);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(fis, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(isr);

                //按行读取csv文件内容
                String line = null;
                //存储读取到的csv文件内容
                String FileContent = "";
                //计数器(用户上传的csv文件第一行的头文件不需要写到data_train.csv中，只需要写入数据行)
                int LineNumber = 1;
                //使用当前时间(YYYYMMDDHHMMSS)作为编号
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
                String shipIDStr=format.format(date);
                //同一个用户文件中的编号值依次增加1
                long shipID = Long.parseLong(shipIDStr);

                try{
                    while ((line = br.readLine()) != null) {
                        //给每一行补上编号
                        if(LineNumber != 1){
                            FileContent += String.valueOf(shipID) + ",";
                            FileContent += line;
                            FileContent += "\r\n"; // 补上换行符
                        }
                        LineNumber++;
                        shipID++;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(FileContent);

                //将读取到的用户上传csv文件的内容续写到data_train.csv中(按照utf-8格式)
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(data_train_csv_path,true);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(fos, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    osw.write(FileContent);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    osw.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //关闭文件流
                try {
                    fis.close();
                    isr.close();
                    fos.close();
                    osw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }

    }
}
