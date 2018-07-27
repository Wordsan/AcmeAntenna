package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.transaction.Transactional;

import domain.Actor;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {
    private static boolean DASHBOARD_LOGGING_MODE = false;

    @Autowired private AdministratorService administratorService;

    // Test that each statistic matches the expected value, and that it fails if not requested as admin.
    private void checkStatistic(String methodName, Object expected) throws Exception
    {
        // Get the method for the statistic.
        Method method = null;
        for (Method method2 : administratorService.getClass().getDeclaredMethods()) {
            if (method2.getName().equals(methodName)) {
                method = method2;
                break;
            }
        }

        // Ensure we didn't mistype it.
        Assert.notNull(method);

        // Ensure it fails if not authenticated.
        boolean failsWithoutPermissions = false;
        try {
            unauthenticate();
            method.invoke(administratorService);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof AccessDeniedException) {
                failsWithoutPermissions = true;
            } else {
                throw e;
            }
        }

        // Abort if it doesn't.
        Assert.isTrue(failsWithoutPermissions);

        // Ensure it fails if just a user.
        failsWithoutPermissions = false;
        try {
            authenticate("user1");
            method.invoke(administratorService);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof AccessDeniedException) {
                failsWithoutPermissions = true;
            } else {
                throw e;
            }
        }

        // Abort if it doesn't.
        Assert.isTrue(failsWithoutPermissions);

        // As admin:
        authenticate("admin");

        // Call the method.
        Object actual = method.invoke(administratorService);
        boolean ok = false;
        if (actual.equals(expected)) ok = true;
        if (!ok && printObject(actual).equals(expected)) ok = true;
        if (!ok && expected instanceof Number && actual instanceof Number && Math.round(1000 * (double) expected) == Math.round(1000 * (double) actual)) ok = true;
        if (DASHBOARD_LOGGING_MODE) {
            // For development use only.
            System.err.println(String.format("%4s %-40s %45s %45s", ok ? "OK" : "FAIL", methodName, printObject(expected), printObject(actual)));
        } else {
            // Ensure it returns the expected value.
            Assert.isTrue(ok);
        }
    }

    private Object printObject(Object object)
    {
        if (object instanceof Double) {
            return String.format("%.3f", Math.round(1000 * (Double) object) / 1000.0);
        } else if (object instanceof Actor) {
            return ((Actor) object).getUserAccount().getUsername();
        } else if (object instanceof Iterable || object instanceof Object[] ) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean first = true;
            if (object instanceof Object[]) object = Arrays.asList(((Object[]) object));
            for (Object child : ((Iterable) object)) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(printObject(child));
            }
            sb.append("]");
            return sb.toString();
        } else {
            return object.toString();
        }
    }

    @Test
    public void testDashboard() throws Exception
    {
        checkStatistic("findAvgAntennaCountPerUser", 3.333);
        checkStatistic("findStdDevAntennaCountPerUser", 1.247);
        checkStatistic("findAntennaCountPerModel", "[[Model 1, 5], [Model 2, 3], [Model 3, 1], [Model 4, 1]]");
        checkStatistic("findMostPopularAntennas", "[[Model 1, 5], [Model 2, 3], [Model 3, 1]]");
        checkStatistic("findAvgAntennaSignalQuality", 49.400);
        checkStatistic("findStdDevAntennaSignalQuality", 33.948);
        checkStatistic("findAvgTutorialCountPerUser", 2.000);
        checkStatistic("findStdDevTutorialCountPerUser", 0.817);
        checkStatistic("findAvgCommentCountPerTutorial", 4.333);
        checkStatistic("findStdDevCommentCountPerTutorial", 6.600);
        checkStatistic("findTopTutorialContributors", "[user1]");

        // Ensure it's not left in logging mode accidentally, as the lecturers don't like tests
        // that print to console for some reason.
        Assert.isTrue(!DASHBOARD_LOGGING_MODE, "DASHBOARD_LOGGING_MODE == true");
    }

}
