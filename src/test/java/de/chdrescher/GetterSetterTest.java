package de.chdrescher;


import de.chdrescher.model.TestModel;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class GetterSetterTest {

    @Test
    public void shouldTestGetterAndSetter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        TestUtil.testGetterAndSetter(TestModel.class);
    }
}
