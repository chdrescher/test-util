package de.chdrescher;

import org.junit.Assert;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Utility class that provides test support methods based on reflection
 */
public class TestUtil {

    private TestUtil(){
        // Cannot be instantiated - only provides static methods.
    }

    /**
     * Tests all setter and getter methods of the given class.
     * @param curClass Class under test.
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static void testGetterAndSetter(Class curClass) throws IllegalAccessException, InstantiationException,
            InvocationTargetException {
        Method[] allMethods = curClass.getMethods();

        Map<String, Method> setters = new HashMap<>();
        Map<String, Method> getters = new HashMap<>();

        for(Method method : allMethods) {
            String methodName = method.getName();
            if(methodName.startsWith("set")) {
                setters.put(getFieldNameFromMethod(method), method);
            }
            if(methodName.startsWith("get") || methodName.startsWith("is")) {
                getters.put(getFieldNameFromMethod(method), method);
            }
        }

        Object classUnterTest = curClass.newInstance();

        for (Map.Entry<String, Method> setterEntry : setters.entrySet()){
            String paramName = getFieldNameFromMethod(setterEntry.getValue());
            Object testValue = buildTestValue(setterEntry.getValue().getParameterTypes()[0]);
            setterEntry.getValue().invoke(classUnterTest, testValue);

            Method getter = getters.get(paramName);
            Assert.assertNotNull(getter);

            Assert.assertEquals(testValue, getter.invoke(classUnterTest));
        }
    }

    private static Object buildTestValue(Class parameterType) throws IllegalAccessException, InstantiationException {
        if (parameterType.equals(String.class)){
            return UUID.randomUUID().toString();
        }
        if (parameterType.equals(Boolean.class) || parameterType.getName().equals("boolean")){
            return false;
        }
        if (parameterType.equals(Double.class) || parameterType.getName().equals("double")){
            Random random = new Random();
            return random.nextDouble();
        }
        if (parameterType.getName().equals("char")){
            return 'c';
        }
        if (parameterType.equals(Integer.class) || parameterType.getName().equals("int")){
            Random random = new Random();
            return new Integer(random.nextInt());
        }
        if (parameterType.equals(List.class)){
            return new ArrayList<>();
        }
        if (parameterType.equals(Set.class)){
            return new HashSet<>();
        }
        if (parameterType.isArray()){
            Class c = parameterType.getComponentType();
            return Array.newInstance(c, 0);
        }
        throw new IllegalArgumentException("cannot create test value for parameter of type '"
                + parameterType.toString() + "'");
    }

    private static String getFieldNameFromMethod(Method method) {
        if (method.getName().startsWith("is")){
            return method.getName().substring(2);
        }
        return method.getName().substring(3);
    }
}
