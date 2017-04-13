package com.cc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by cc on 2017/4/11.
 */
@Controller
public class FileController {

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request,
//                         @RequestParam("info") String info,
                         @RequestParam("file") MultipartFile file)throws Exception{

//        System.out.println(info);
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
            file.transferTo(new File("/Users/cc/Desktop"+ File.separator + filename));
//            System.out.println("filename:......."+filename);
            return "success";
        }else {
            return "error";
        }
    }
}
