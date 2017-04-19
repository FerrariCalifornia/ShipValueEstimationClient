package com.cc.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by cc on 2017/4/11.
 */
public class Test {
    public static void main(String[] args) {

        Properties prop = new Properties();

        try {
            prop.load(Test.class.getClassLoader().getResourceAsStream("file.properties"));

            System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


}
