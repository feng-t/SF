package com.sf.app.paraprocess;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数处理
 */
public class ProcessIncomingParameters {
    /**
     * 存储一些参数
     */
    private static final Map<String, String> parameters = new HashMap<>();

    public void process(String[] ages)throws Exception{
        for (String age : ages) {
            String[] kv = age.split(":");
            parameters.put(kv[0],kv[1]);
        }
    }
    String getParameter(String key)throws Exception{
        return parameters.get(key);
    }
}
