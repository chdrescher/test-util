# test-util
Utility that provides test support methods based on reflection

# Usage example

## Test all setter and getter methods of a class

```java
public class GetterSetterTest {

    @Test
    public void shouldTestGetterAndSetter() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        TestUtil.testGetterAndSetter(TestModel.class);
    }
}
```

# Copyright & License

(c) Christian Drescher, 2016.

[Apache Public License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

