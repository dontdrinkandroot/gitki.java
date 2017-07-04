package net.dontdrinkandroot.gitki.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"classpath:spring/context.xml"})
public abstract class AbstractIntegrationTest
{
}
