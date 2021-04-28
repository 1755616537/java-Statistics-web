package cn.miteng.util;

import com.alibaba.fastjson.JSONObject;

public class Public {
    public JSONObject Return(String code , String msg){
        JSONObject Return = new JSONObject();
        Return.put("code",code);
        Return.put("msg",msg);
        return Return;
    }
    public JSONObject Return(String code , String msg, Object data){
        JSONObject Return = new JSONObject();
        Return.put("code",code);
        Return.put("msg",msg);
        Return.put("data",data);
        return Return;
    }
}
