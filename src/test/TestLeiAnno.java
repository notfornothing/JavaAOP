package test;

import org.junit.Test;
import proxy.AOPHandle;
import proxy.DogImp;
import proxy.annon.AnnoInjection;
import proxy.annon.Lei;
import proxy.bean.Person;
import proxy.imp.AOPMethod;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestLeiAnno {
    @Test
    public void test1() throws Exception {


        aopTestOrigin();
    }


    public static void aopTestOrigin() throws Exception {
        DogImp obj = DogImp.class.newInstance();
        //获取代理对象
        Object o = Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new AOPHandle(AnnoInjection.getBean(obj), new AOPMethod() {
                    @Override
                    public void after(Object proxy, Method method, Object[] args) {
                        System.out.println("after");
                    }

                    @Override
                    public void before(Object proxy, Method method, Object[] args) {
                        System.out.println("before");
                    }
                })
        );
        DogImp o1 = (DogImp) o;
        o1.say();
    }

    public static void aopTest(Class clazz) throws Exception {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(),
                clazz.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("preeeeeeeeee");
                        Object ret = method.invoke(proxy, args);
                        System.out.println("afterrrrrrrrrrr");
                        return ret;
                    }
                }
        );
        Person person = (Person) o;
        person.say();
    }


    /**
     * 获取类上的注解
     *
     * @param clazz
     * @return
     */
    public static void getAnnotationAndExecute(Class clazz) throws Exception {
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println("field.getName()" + field.getName());
            Lei lei = field.getAnnotation(Lei.class);
            if (lei != null) {
                System.out.println("Filed 这里有Lei注解" + lei.value());
                Method method = clazz.getMethod("set" + field.getName().substring(0, 1).toUpperCase()
                        + field.getName().substring(1));
                method.invoke(clazz.newInstance(), lei.value());
            }
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("method.getName()" + method.getName());
            Lei lei = method.getAnnotation(Lei.class);
            if (lei != null) {
                System.out.println("Method 这里有Lei注解 : " + "\t 方法名:" + method.getName() + "\t 注解value:" + lei.value());
                method.invoke(clazz.newInstance());
            }
        }
    }

    /**
     * 获取类里的注解
     *
     * @param clazz
     * @return
     */
    public static void getAnnotation(Class clazz) {
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println("field.getName()" + field.getName());
            Lei lei = field.getAnnotation(Lei.class);
            if (lei != null) {
                System.out.println("Filed 这里有Lei注解" + lei.value());
            }
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("method.getName()" + method.getName());
            Lei lei = method.getAnnotation(Lei.class);
            if (lei != null) {
                System.out.println("Method 这里有Lei注解 : " + "\t 方法名:" + method.getName() + "\t 注解value:" + lei.value());
            }
        }
    }

}
