package com.smikevon.proxy.dynamic;

import com.smikevon.proxy.Processors.FileMapperProcessor;
import com.smikevon.proxy.Processors.FileServiceProcessor;
import com.smikevon.proxy.annotations.FxMapper;
import com.smikevon.proxy.annotations.FxService;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by fengxiao on 15-1-27.
 */
public class FxApplicationContext {
    
    private static final String JAVA_SOURCE_DIR = "src/main/java";
    private static final String JAVA_PACKAGE_DIR = "com/smikevon/proxy/dynamic";
    private static final String JAVA_PACKAGE_PATH = "com.smikevon.proxy.dynamic";
    
    private Map<String,Object> context = new HashMap<String,Object>();
    
    private FxApplicationContext(){
        //init();
    }

    /**
     * 容器初始化
     */
    public FxApplicationContext init(){
        try {
            File file1 = new File(JAVA_SOURCE_DIR+File.separator+JAVA_PACKAGE_DIR);
            File[] files = file1.listFiles();
            for(File file : files){
                if(file.getName().endsWith("java")){
                    String name = file.getName().substring(0,file.getName().indexOf("."));
                    Class<?> clazz = Class.forName(JAVA_PACKAGE_PATH+"."+name);
                    if(clazz.getAnnotation(FxMapper.class)!=null){
                        String key = clazz.getAnnotation(FxMapper.class).value();
                        //实例化mapper的处理类
                        FileMapperProcessor processor = new FileMapperProcessor();
                        if(context.get(key) == null){
                            context.put(key,processor.bind(clazz));
                        }
                    }
                    if(clazz.getAnnotation(FxService.class)!=null){
                        String key = clazz.getAnnotation(FxService.class).value();
                        //实例化service的处理类
                        FileServiceProcessor processor = new FileServiceProcessor();
                        if(context.get(key) == null){
                            context.put(key,processor.bind(clazz));
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;

    }

    public Object getBean(String beanId){
        Iterator<Map.Entry<String, Object>> iterator = context.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            if(key.equals(beanId)){
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 静态内部类方式实现单例
     */
    private static class FxApplicationContextHolder{
        public static FxApplicationContext instance = new FxApplicationContext();
    }
    
    public static FxApplicationContext getInstance(){
        return FxApplicationContextHolder.instance;
    }
    
}
