package com.smikevon.proxy.Processors;

import com.smikevon.proxy.annotations.FxResource;
import com.smikevon.proxy.dynamic.FxApplicationContext;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by fengxiao on 15-1-27.
 */
public class FileServiceProcessor implements MethodInterceptor{

    private Object target;
    
    public Object bind(Class clazz) {
        try {
            target = clazz.newInstance();
            
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(this);
            Object obj = enhancer.create();
    
            //将mapper的实例bean赋值给对应的引用
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                if (field.getAnnotation(FxResource.class)!=null){
                    String value = field.getAnnotation(FxResource.class).value();
                    Object object = FxApplicationContext.getInstance().getBean(value);
                    //看到没下面这行代码和注释掉的代码效果是一样的,反射的强大之处
                    field.set(target,object);
                    //((FileService)target).fileMapper = (FileMapper)object;
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
