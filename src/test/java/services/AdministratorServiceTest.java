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

/**
 * Tests the following use cases:
 *
 * Display a dashboard with the following information:
 * - The average and the standard deviation of the number of antennas per user.
 * - The average and the standard deviation of the quality of the antennas.
 * - A chart with the number of antennas per model.
 * - The top-3 antenna models in terms of popularity.
 * Display a dashboard with the following information:
 * - The average and the standard deviation of the number of tutorials per user.
 * - The average and the standard deviation of the number of comments per tutorial.
 * - The actors who have published a number of tutorials that is above the average
 *   plus the standard deviation.
 */
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {
    /**
     * Dashboard test mode:
     *
     * `true` logs the correct values to console, to be able to quickly verify them
     * and copy them to the checks, and always fails the test to ensure it's changed
     * to false later.
     *
     * `false` prints nothing to the console (lecturers don't like tests that print
     * to the console for some reason), and fails the test if it doesn't match.
     */
    private static boolean DASHBOARD_LOGGING_MODE = false;

    @Autowired private AdministratorService administratorService;

    // Test dashboard values are correct.
    // Also tests that it fails if unauthenticated.
    // Also tests that it fails if authenticated as user.
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

        // Ensure it's not left in logging mode accidentally.
        Assert.isTrue(!DASHBOARD_LOGGING_MODE, "DASHBOARD_LOGGING_MODE == true");
    }

    // Helper function that tests that the statistic matches the expected value, and that it fails if not requested as admin.
    // We use reflection here so we have the method name for the log print call.
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

        // Call the dashboard service method.
        Object actual = method.invoke(administratorService);

        // Check if the actual value matches what we expected.
        boolean ok = dashboardItemEqual(expected, actual);

        if (DASHBOARD_LOGGING_MODE) {
            // For development use only. Print to console.
            System.err.println(String.format("%4s %-40s %45s %45s", ok ? "OK" : "FAIL", methodName, printDashboardItem(expected), printDashboardItem(actual)));
        } else {
            // If in normal mode, just ensure it returns the expected value.
            Assert.isTrue(ok);
        }
    }

    // Helper function to check if a dashboard item is equal to the expected value.
    private boolean dashboardItemEqual(Object expected, Object actual)
    {
        // A dashboard item's actual value matches the expected value if:
        boolean ok = false;

        // They're equal.
        if (actual.equals(expected)) ok = true;

        // The string representation we use for this test for the actual value is equal to the expected value.
        if (!ok && printDashboardItem(actual).equals(expected)) ok = true;

        // They're both numbers, and are equal if rounded to 3 decimal places.
        if (!ok && expected instanceof Number && actual instanceof Number && Math.round(1000 * (double) expected) == Math.round(1000 * (double) actual)) ok = true;
        return ok;
    }

    // Helper function to get a custom string representation of a dashboard item.
    private Object printDashboardItem(Object object)
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
                sb.append(printDashboardItem(child));
            }
            sb.append("]");
            return sb.toString();
        } else {
            return object.toString();
        }
    }
}
