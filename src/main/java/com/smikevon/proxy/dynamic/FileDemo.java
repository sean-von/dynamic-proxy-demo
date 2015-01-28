package com.smikevon.proxy.dynamic;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by fengxiao on 15-1-27.
 */
public class FileDemo {
    
    public static void main(String[] args) throws InvocationTargetException {
        FxApplicationContext fxApplicationContext = FxApplicationContext.getInstance().init();
        FileService fileService = (FileService)fxApplicationContext.getBean("fileService");
        fileService.read();
    }
    
}
