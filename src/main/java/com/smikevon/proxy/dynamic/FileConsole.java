package com.smikevon.proxy.dynamic;

import com.smikevon.proxy.annotations.FxMapper;
import com.smikevon.proxy.annotations.FxResource;
import com.smikevon.proxy.annotations.FxService;
import com.smikevon.proxy.common.Constant;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by fengxiao on 15-1-27.
 */
public class FileConsole {
    
    public static void main(String[] args) throws InvocationTargetException {
        FileService fileService = (FileService)Constant.getApplicationContext().get(FxService.class).get("fileService");
        fileService.read();
    }
    
    
    
}
