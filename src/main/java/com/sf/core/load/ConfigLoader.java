package com.sf.core.load;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
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
        System.out.println(p);
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
