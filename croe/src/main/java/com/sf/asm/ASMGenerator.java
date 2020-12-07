package com.sf.asm;

import com.sf.bean.Resource;
import com.sf.bean.factory.ParentBeanFactory;

import java.util.Set;

public class ASMGenerator extends ParentBeanFactory {
    public ASMGenerator(Set<Resource> resourceSet) throws ClassNotFoundException {
        super(resourceSet);
    }
}
