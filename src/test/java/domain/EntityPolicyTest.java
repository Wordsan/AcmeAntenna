package domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import utilities.AbstractTest;
import utilities.ReflectionUtils;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EntityPolicyTest extends AbstractTest {
    @Test
    public void testEntitiesFollowPolicy() throws Exception
    {
        boolean ok = true;
        Collection<? extends Class<?>> entities = ReflectionUtils.findAllDomainEntityClasses();
        for (Class<?> entity : entities) {
            for (Method method: entity.getDeclaredMethods()) {
                // If it's a getter:
                if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                    ok &= checkEntityGetterFollowsPolicy(entity, method);
                }
            }
        }

        Assert.assertTrue(ok);
    }

    private boolean checkEntityGetterFollowsPolicy(Class<?> entity, Method method)
    {
        boolean result = true;

        if (method.getReturnType().equals(String.class) && !method.isAnnotationPresent(NotNull.class)) {
            result = false;
            System.err.println("Method " + entity.getSimpleName() + "." + method.getName() + " returns String but it's not using NotNull.");
        }

        return result;
    }
}
