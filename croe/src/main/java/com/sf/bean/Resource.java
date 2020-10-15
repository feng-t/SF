package com.sf.bean;

import java.net.URL;

public class Resource {
    private URL url;
    private String className;
    public Resource(URL url,String packName){
        this.url=url;
        this.className=packName;
    }
}
