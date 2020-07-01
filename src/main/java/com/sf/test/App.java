package com.sf.test;

import com.sf.core.annotation.Service;
import com.sf.core.app.Application;

@Service(hour = 1)
@Service(hour = 2)
@Service(hour = 3)
public class App{

    public static void main(String[] args) throws Exception {
        Application.run(App.class,args);
    }

}
