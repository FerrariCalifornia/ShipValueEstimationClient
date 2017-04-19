package com.cc.service.Imlp;

import com.cc.service.FileService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.springframework.stereotype.Service;


import java.io.File;

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
