package proxy;

import proxy.annon.AnnoInjection;
import proxy.imp.AOPMethod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PersonFactory {

    public static Object getObj(Object obj, AOPMethod method) {
        Object o = Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object invoke = method.invoke(obj, args);
                        return invoke;
                    }
                }
        );

        return o;

    }
}
