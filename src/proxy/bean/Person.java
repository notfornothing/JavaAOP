package proxy.bean;

import proxy.annon.Lei;
import proxy.imp.PreStandard;

public class Person implements PreStandard {
    @Override
    public void getProperty() {
        System.out.println("override getProperty");
    }

    @Override
    public void setProperty(String Property) {
        System.out.println("override setProperty");
    }
    
    @Lei
    public void say() {
        System.out.println("saying~~~~~~~~~");
    }
}
