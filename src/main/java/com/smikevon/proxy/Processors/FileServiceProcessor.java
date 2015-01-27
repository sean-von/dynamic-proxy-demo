package com.smikevon.proxy.Processors;

import com.smikevon.proxy.annotations.FxMapper;
import com.smikevon.proxy.annotations.FxResource;
import com.smikevon.proxy.common.Constant;
import com.smikevon.proxy.dynamic.FileMapper;
import com.smikevon.proxy.dynamic.FileService;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by fengxiao on 15-1-27.
 */
public class FileServiceProcessor implements MethodInterceptor{

    private Object target;
    
    public Object bind(Class clazz) {
        try {
            target = clazz.newInstance();
            
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(FileService.class);
            enhancer.setCallback(this);
            Object obj = enhancer.create();
    
            //将mapper的实例bean赋值给对应的引用
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                if (field.getAnnotation(FxResource.class)!=null){
                    String value = field.getAnnotation(FxResource.class).value();
                    Class<?> fieldClazz = field.getType();
                    Map<String,Object> beans = Constant.getApplicationContext().get(FxMapper.class);
                    Object object = beans.get(value);
                    ((FileService)target).fileMapper = (FileMapper)object;
                }
            }
            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        method.invoke(target,objects);
        return null;
    }
}
