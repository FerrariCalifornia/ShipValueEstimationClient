package com.cc.controller;

import com.cc.timeTask.MyTimeTaskImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by cc on 2017/4/11.
 */
@Controller
public class FileController {
    private static Logger log =Logger.getLogger(FileController.class);
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request,
                         @RequestParam("info") String info,
                         @RequestParam("file") MultipartFile file)throws Exception{

//        System.out.println(info);
        if (!info.equals("heqi")){
            return "error";
        }
        log.info("start upload");
        if (!file.isEmpty()) {
            System.out.println("start upload.......");
            String path = request.getServletContext().getRealPath("/upload/");
            String filename = file.getOriginalFilename();
            System.out.println("filename:......."+filename);
            File filepath = new File(path, filename);
            System.out.println("filepath:"+filepath);

            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdir();
            }


            String pathname = null;
            Properties prop = new Properties();
            try {
                prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
                pathname=prop.getProperty("savepath");
            } catch(IOException e) {
                e.printStackTrace();
            }
            file.transferTo(new File(pathname+ File.separator + filename));
            System.err.println("filepath:"+pathname);
            return "success";
        }else {
            return "error";
        }
    }
}
