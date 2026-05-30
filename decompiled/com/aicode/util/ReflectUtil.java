package com.aicode.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ya */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/ReflectUtil.class */
public class ReflectUtil {

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ Map<String, Class<?>> f727enum = new ConcurrentHashMap();

    /* renamed from: try, reason: not valid java name */
    private static final /* synthetic */ Map<Class<?>, Field[]> f724try = new ConcurrentHashMap();

    /* renamed from: final, reason: not valid java name */
    private static final /* synthetic */ Map<Class<?>, Method[]> f723final = new ConcurrentHashMap();

    /* renamed from: byte, reason: not valid java name */
    private static final /* synthetic */ Field[] f726byte = new Field[0];

    /* renamed from: float, reason: not valid java name */
    private static final /* synthetic */ Method[] f725float = new Method[0];

    public static /* synthetic */ <T> T getStaticField(Class<?> cls, String str) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return (T) declaredField.get(null);
    }

    public static /* synthetic */ Object getObjField(Object a, String a2) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = a.getClass().getDeclaredField(a2);
        declaredField.setAccessible(true);
        return declaredField.get(a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: public, reason: not valid java name */
    private static /* synthetic */ Field[] m436public(Class<?> cls) {
        Field[] fieldArr = f724try.get(cls);
        if (fieldArr == null) {
            try {
                Field[] declaredFields = cls.getDeclaredFields();
                f724try.put(cls, declaredFields.length == 0 ? f726byte : declaredFields);
                return declaredFields;
            } catch (Throwable th) {
                throw new IllegalStateException("Failed to introspect Class [" + cls.getName() + "] from ClassLoader [" + cls.getClassLoader() + "]", th);
            }
        }
        return fieldArr;
    }

    public static /* synthetic */ List<Method> getAllMethod(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        Class a = cls;
        do {
            Method[] m435implements = m435implements(a);
            if (m435implements != null && m435implements.length > 0) {
                arrayList.addAll(Arrays.asList(m435implements));
            }
            Class superclass = a.getSuperclass();
            a = superclass;
            if (superclass == null) {
                break;
            }
        } while (a != Object.class);
        return arrayList;
    }

    public static /* synthetic */ List<Field> getAllFields(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        Class a = cls;
        do {
            Field[] m436public = m436public(a);
            if (m436public != null && m436public.length > 0) {
                arrayList.addAll(Arrays.asList(m436public));
            }
            Class superclass = a.getSuperclass();
            a = superclass;
            if (superclass == null) {
                break;
            }
        } while (a != Object.class);
        return arrayList;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ Method getMethod(Class<?> cls, String a, Class<?>... clsArr) {
        Method method;
        Class<?> cls2 = cls;
        do {
            Method method2 = null;
            try {
                method2 = cls.getDeclaredMethod(a, clsArr);
                method = method2;
            } catch (NoSuchMethodException unused) {
                method = method2;
            }
            if (method != null) {
                return method2;
            }
            Class<? super Object> superclass = cls2.getSuperclass();
            cls2 = superclass;
            if (superclass == null) {
                return null;
            }
        } while (cls2 != Object.class);
        return null;
    }

    public static /* synthetic */ Class<?> classForName(String a) throws ClassNotFoundException {
        Class<?> cls = f727enum.get(a);
        Class<?> cls2 = cls;
        if (cls == null) {
            cls2 = Class.forName(a);
            f727enum.put(a, cls2);
        }
        return cls2;
    }

    public static /* synthetic */ void replaceField(Field a, Object a2) throws Exception {
        a.setAccessible(true);
        a.set(null, a2);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: implements, reason: not valid java name */
    private static /* synthetic */ Method[] m435implements(Class<?> cls) {
        Method[] methodArr = f723final.get(cls);
        if (methodArr == null) {
            try {
                Method[] declaredMethods = cls.getDeclaredMethods();
                f723final.put(cls, declaredMethods.length == 0 ? f725float : declaredMethods);
                return declaredMethods;
            } catch (Throwable th) {
                throw new IllegalStateException("Failed to introspect Class [" + cls.getName() + "] from ClassLoader [" + cls.getClassLoader() + "]", th);
            }
        }
        return methodArr;
    }

    public static /* synthetic */ Object getField(Field a, Object a2) throws Exception {
        a.setAccessible(true);
        return a.get(a2);
    }
}
