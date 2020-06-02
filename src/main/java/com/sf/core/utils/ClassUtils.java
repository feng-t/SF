package com.sf.core.utils;

import java.io.File;

public class ClassUtils {
    private static String getPackName(Class<?> aClass){
        String simpleName = aClass.getSimpleName();
        String packName = aClass.getName();
        packName = packName.substring(0,packName.lastIndexOf(simpleName)-1);
        return packName;
    }

    public static String packRevPath(Class<?> aClass){
        return getPackName(aClass).replace(".", File.separator);
    }
}
