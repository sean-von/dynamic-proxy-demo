package com.smikevon.proxy.dynamic;

import com.smikevon.proxy.annotations.FxMapper;

/**
 * Created by fengxiao on 15-1-27.
 */
@FxMapper("fileMapper")
public interface FileMapper {
    
    public String read(String fileDir);
    
    public void append(String fileDir , String message);
    
}
