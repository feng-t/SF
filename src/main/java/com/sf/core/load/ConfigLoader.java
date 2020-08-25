package com.sf.core.load;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigLoader {
    /**
     * 保存所有配置
     */
    private final Map<String,String> configKey = new ConcurrentHashMap<>();
    public String getConfig(String key){
        return configKey.get(key);
    }
    private ConfigLoader(){}

    public void load() throws IOException {
        loadProper("application");
    }
    public void loadProper(String path) throws IOException {
        if (!path.startsWith("/")){
            path="/"+path;
        }
        if (!path.endsWith(".properties")){
            path=path+".properties";
        }
        InputStream stream = ConfigLoader.class.getResourceAsStream(path);
        Properties p =new Properties();
        p.load(stream);
        Enumeration<?> names = p.propertyNames();
        while (names.hasMoreElements()){
            String key = (String) names.nextElement();
            configKey.put(key,p.getProperty(key));
        }
    }

    public static void main(String[] args) throws IOException {
        ConfigLoader loader = instance.singleton.getConfigLoader();
        loader.loadProper("application");
    }

    public enum instance{
        singleton;
        private final ConfigLoader configLoader;
        instance(){
            configLoader=new ConfigLoader();
        }
        public ConfigLoader getConfigLoader() {
            return configLoader;
        }
    }
}
