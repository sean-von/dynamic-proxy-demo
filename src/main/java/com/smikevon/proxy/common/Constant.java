package com.smikevon.proxy.common;

import com.smikevon.proxy.dynamic.FxApplicationContext;

import java.util.Map;

/**
 * Created by fengxiao on 15-1-27.
 */
public class Constant {

    //初始化容器
    static{
        FxApplicationContext.init();
    }
    
    public static Map<Class, Map<String, Object>> getApplicationContext(){
        return  FxApplicationContext.getContext();
    }
    
}
