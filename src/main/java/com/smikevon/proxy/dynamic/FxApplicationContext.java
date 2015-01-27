package com.smikevon.proxy.dynamic;

import com.smikevon.proxy.Processors.FileMapperProcessor;
import com.smikevon.proxy.Processors.FileServiceProcessor;
import com.smikevon.proxy.annotations.FxMapper;
import com.smikevon.proxy.annotations.FxService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengxiao on 15-1-27.
 */
public class FxApplicationContext {
    
    private static final String JAVA_SOURCE_DIR = "src/main/java";
    private static final String JAVA_PACKAGE_DIR = "com/smikevon/proxy/dynamic";
    private static final String JAVA_PACKAGE_PATH = "com.smikevon.proxy.dynamic";
    
    private static Map<Class,Map<String,Object>> context = new HashMap<Class,Map<String,Object>>();

    /**
     * 容器初始化
     */
    public static void init(){
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
                        Map<String,Object> beans = context.get(FxMapper.class);
                        if(beans == null){
                            beans = new HashMap<String, Object>();
                        }
                        beans.put(key, processor.bind(clazz));
                        context.put(FxMapper.class,beans);
                    }

                    if(clazz.getAnnotation(FxService.class)!=null){

                        String key = clazz.getAnnotation(FxService.class).value();

                        //实例化service的处理类
                        FileServiceProcessor processor = new FileServiceProcessor();
                        Map<String,Object> beans = context.get(FxService.class);
                        if(beans == null){
                            beans = new HashMap<String, Object>();
                        }
                        beans.put(key, processor.bind(clazz.newInstance()));
                        context.put(FxService.class,beans);
                    }
                    
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public static Map<Class,Map<String,Object>> getContext(){
        return context;
    }
    
}
