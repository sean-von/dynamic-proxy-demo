package com.smikevon.proxy.dynamic;

import com.smikevon.proxy.annotations.FxMapper;
import com.smikevon.proxy.annotations.FxResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by fengxiao on 15-1-27.
 */
public class FileConsole {
    
    public static void main(String[] args) throws InvocationTargetException {
        
        //初始化容器,将mapper注册到容器中
        FxApplicationContext.init();
        Map<Class,Map<String,Object>> applicationContext = FxApplicationContext.getContext();

        try {
            Class<?> clazz = Class.forName("com.smikevon.proxy.dynamic.FileService");
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                if (field.getAnnotation(FxResource.class)!=null){
                    String value = field.getAnnotation(FxResource.class).value();
                    Class<?> fieldClazz = field.getType();
                    Map<String,Object> beans = applicationContext.get(FxMapper.class);
                    Object obj = beans.get(value);
                    FileService fileService = (FileService)clazz.newInstance();
                    fileService.fileMapper = (FileMapper)obj;
                    fileService.read();
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
    
    
    
}
