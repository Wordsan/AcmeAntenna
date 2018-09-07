package validators;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.Date;

import javax.transaction.Transactional;

import utilities.AbstractTest;


@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PastOrPresentTest extends AbstractTest {
    // Test the validator works correctly.
    // Null dates, dates in the past, the present, and up to 30 seconds in the future should be accepted.
    // Everything else should be rejected.
    @Test
    public void test()
    {
        PastOrPresentValidator v = new PastOrPresentValidator();
        Assert.assertTrue(v.isValid(null, null));
        Assert.assertTrue(v.isValid(new Date(new Date().getTime() - 100000), null));
        Assert.assertTrue(v.isValid(new Date(new Date().getTime() - 1000), null));
        Assert.assertTrue(v.isValid(new Date(new Date().getTime()), null));
        Assert.assertTrue(v.isValid(new Date(new Date().getTime() + 1000), null));
        Assert.assertFalse(v.isValid(new Date(new Date().getTime() + 100000), null));
    }
}
