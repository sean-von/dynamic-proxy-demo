package com.smikevon.proxy.dynamic;

import com.smikevon.proxy.annotations.FxResource;
import com.smikevon.proxy.annotations.FxService;

/**
 * Created by fengxiao on 15-1-27.
 */
@FxService("fileService")
public class FileService {
    
    String file = "rFile.txt";
    
    @FxResource(value="fileMapper")
    public FileMapper fileMapper;

    public void read(){
        String txt = fileMapper.read(file);
        System.out.println("message:"+txt);
    }
    
}
