package com.sf.core.event;

import java.util.List;

public interface EventAction<T> {

    /**
     * 注册事件
     * @param name
     * @param t
     */
    void regEvent(String name, T t);

    /**
     * 返回事件
     * @param name
     * @return
     */
    List<T> getEvent(String name);

    /**
     * 激活事件
     * @param name
     */
    void actEvent(String name);
}
