package com.smikevon.proxy.Processors;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Method;

/**
 * Created by fengxiao on 15-1-27.
 */
public class FileMapperProcessor implements MethodInterceptor{

    public Object bind(Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(this);
        enhancer.setSuperclass(clazz);
        Object obj = enhancer.create();
        return obj;
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        StringBuilder sb = new StringBuilder();
        if(method.getName().toLowerCase().startsWith("read")){
            if (objects.length > 0) {
                String fileDir = (String) objects[0];
                File file = new File(fileDir);
                BufferedReader br = new BufferedReader(new FileReader(file));
                
                String line = null;
                while((line = br.readLine())!=null){
                    sb.append(line);
                }
            }
        }

        return sb.toString();
    }
}
