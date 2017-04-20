package com.cc.service;

import java.io.File;

/**
 * Created by cc on 2017/4/19.
 */
public interface FileService {

    void uploadFile(File file, String url, String info);
    void updateDataTrain20();
    void updateDataTrain90();

}
