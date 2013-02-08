import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * Author: Nick De Waele
 * Date: 8/02/13
 */
@ContextConfiguration(locations = "/persistence-beans.xml")
public class IntegrationTest extends AbstractJUnit4SpringContextTests {
    @Autowired
   	private SessionFactory sessionFactory;

   	@Test
   	public void testHibernateConfiguration() {
   		// Spring IOC container instantiated and prepared sessionFactory
   		assertNotNull (sessionFactory);
   	}
}
