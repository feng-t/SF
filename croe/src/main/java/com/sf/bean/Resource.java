package com.sf.bean;

import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class Resource implements Comparable<Resource>{
    private final URL url;
    private final String className;
    private final int constructorNum;
    private final int minParameterNum;
    private final int maxParameterNum;
    private AtomicInteger count=new AtomicInteger(0);

    public Resource(URL url,String packName){
        this(url,packName,1);
    }
    public Resource(URL url,String packName,int constructorNum){
        this(url,packName,constructorNum,0,0);
    }
    public Resource(URL url,String packName,int constructorNum,int maxParameterNum,int minParameterNum){
        this.url=url;
        this.className=packName;
        this.constructorNum=constructorNum;
        this.maxParameterNum=maxParameterNum;
        this.minParameterNum=minParameterNum;
    }


    public String getBeanClassName() {
        return className;
    }

    public URL getUrl() {
        return url;
    }

    public int addCount(){
        return count.addAndGet(1);
    }
    public int getCount() {
        return count.get();
    }

    /**
     * 小的在前
     * @param o
     * @return
     */
    @Override
    public int compareTo(Resource o) {
        final int t = getCount();
        final int ot = o.getCount();
        if (t==ot){
            return minParameterNum>o.minParameterNum?1:-1;
        }
        return t>ot?1:-1;
    }
}
