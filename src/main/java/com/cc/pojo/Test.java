package com.cc.pojo;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.File;

/**
 * Created by cc on 2017/4/11.
 */
public class Test {
    public static void main(String[] args) {

       File file = new File("/Users/cc/Desktop/silkroadlog.txt");
       String url ="http://202.200.234.191/ShipValueEstimationClient/upload20";
       uploadFile(file,url,"heqi");
    }

     static void uploadFile(File file, String url, String gold) {

        if (!file.exists()) {
            return;
        }
        PostMethod postMethod = new PostMethod(url);
        try {
            //FilePart：用来上传文件的类
            FilePart fp = new FilePart("file", file);
            //StringPart：用来上传字符串的类
            StringPart sp = new StringPart("info",gold);

            Part[] parts = { sp,fp };

            //对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
            postMethod.setRequestEntity(mre);
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置连接时间
            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
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


}
