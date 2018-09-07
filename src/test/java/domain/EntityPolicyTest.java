package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotNull;
import utilities.AbstractTest;
import utilities.ReflectionUtils;
import validators.NullOrNotBlank;

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
            for (Method method : entity.getDeclaredMethods()) {
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

        if (method.isAnnotationPresent(Transient.class)) return true;

        if ((method.isAnnotationPresent(ManyToOne.class) || method.isAnnotationPresent(OneToOne.class) || method.isAnnotationPresent(ManyToMany.class) || method.isAnnotationPresent(OneToMany.class))) {
            if (!method.isAnnotationPresent(Valid.class)) {
                result = false;
                System.err.println("Method for association " + entity.getSimpleName() + "." + method.getName() + " not using Valid.");
            }
        }
        if (method.isAnnotationPresent(OneToOne.class) && !method.getAnnotation(OneToOne.class).optional() && !method.isAnnotationPresent(NotNull.class)) {
            result = false;
            System.err.println("Method " + entity.getSimpleName() + "." + method.getName() + " using OneToOne(optional = false) but not using NotNull.");
        }
        if (method.isAnnotationPresent(ManyToOne.class) && !method.getAnnotation(ManyToOne.class).optional() && !method.isAnnotationPresent(NotNull.class)) {
            result = false;
            System.err.println("Method " + entity.getSimpleName() + "." + method.getName() + " using ManyToOne(optional = false) but not using NotNull.");
        }

        if (method.getReturnType().equals(String.class) && !method.isAnnotationPresent(NotBlank.class) && !method.isAnnotationPresent(NullOrNotBlank.class)) {
            result = false;
            System.err.println("Method " + entity.getSimpleName() + "." + method.getName() + " returns String but it's not using NotBlank or NullOrNotBlank.");
        }

        if (method.getReturnType().equals(Date.class) && !method.isAnnotationPresent(Temporal.class)) {
            result = false;
            System.err.println("Method " + entity.getSimpleName() + "." + method.getName() + " returns Date but it's not using Temporal.");
        }

        if (method.getReturnType().equals(Date.class) && !method.isAnnotationPresent(DateTimeFormat.class)) {
            result = false;
            System.err.println("Method " + entity.getSimpleName() + "." + method.getName() + " returns Date but it's not using DateTimeFormat.");
        }
        if (method.isAnnotationPresent(EachNotBlank.class) && !method.isAnnotationPresent(EachNotNull.class)) {
            result = false;
            System.err.println("Method " + entity.getSimpleName() + "." + method.getName() + " using EachNotBlank but not using EachNotNull. (unlike NotBlank, EachNotBlank does NOT enforce the not-null constraint)");
        }
        if (method.isAnnotationPresent(NotBlank.class) && method.isAnnotationPresent(NotNull.class)) {
            result = false;
            System.err.println("Method " + entity.getSimpleName() + "." + method.getName() + " using NotBlank and using NotNull. (NotBlank already enforces the not-null constraint, using both doubles the error message)");
        }

        return result;
    }
}
