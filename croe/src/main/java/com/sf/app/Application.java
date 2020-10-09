package com.sf.app;

import com.sf.app.paraprocess.ProcessIncomingParameters;

public class Application {
    private static final Application app = new Application();
    private static final ProcessIncomingParameters incomingParameters=new ProcessIncomingParameters();
    private Application() {
    }

    public static Application getApp() {
        return app;
    }

    public static void run(Class<?> clazz, String[] ages) throws Exception {
        incomingParameters.process(ages);
        app.run(clazz);
    }
    public void run(Class<?> clazz) {

    }

}
