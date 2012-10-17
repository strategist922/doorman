package org.cyclopsgroup.doorman.service.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.cyclopsgroup.caff.util.UUIDUtils;
import org.cyclopsgroup.doorman.service.storage.StoredUser;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Test of {@link HibernateUserDAO}
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
@ContextConfiguration( locations = { "classpath:unit-test-context.xml" } )
@Ignore
public class HibernateUserDAOTest
    extends AbstractJUnit4SpringContextTests
{
    private HibernateUserDAO dao;

    /**
     * Prepare testing version of DAO
     */
    @Before
    public void setUpDao()
    {
        SessionFactory sf = (SessionFactory) applicationContext.getBean( SessionFactory.class );
        dao = new HibernateUserDAO( sf );
    }

    /**
     * Insert a record and find it by name
     */
    @Test
    public void testFindByName()
    {
        String id = UUID.randomUUID().toString();

        StoredUser user = dao.findNonPendingUser( id + "@cyclopsgroup.org" );
        assertNull( user );

        user = Utils.createStoredUser( id );
        dao.getHibernateTemplate().save( user );
        dao.getHibernateTemplate().flush();

        user = dao.findNonPendingUser( id + "@cyclopsgroup.org" );
        assertNotNull( user );
        assertEquals( "pass", user.getPassword() );
    }

    /**
     * Verify save user works
     */
    @Test
    public void testSaveUser()
    {
        String id = UUIDUtils.randomStringId();
        StoredUser request = Utils.createStoredUser( id );

        dao.saveUser( request );
        StoredUser user = dao.findNonPendingUser( id + "@cyclopsgroup.org" );
        assertNotNull( user );
        assertEquals( "pass", user.getPassword() );
    }
}
