package org.cyclopsgroup.doorman.service.hibernate;

import org.cyclopsgroup.doorman.service.dao.UserDAO;
import org.cyclopsgroup.doorman.service.storage.StoredUser;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Hibernate based implementation of user DAO
 *
 * @author <a href="mailto:jiaqi@cyclopsgroup.org">Jiaqi Guo</a>
 */
class HibernateUserDAO
    extends HibernateDaoSupport
    implements UserDAO
{
    /**
     * @param sessionFactory Hibernate session factory
     */
    HibernateUserDAO( SessionFactory sessionFactory )
    {
        setSessionFactory( sessionFactory );
    }

    /**
     * @inheritDoc
     */
    @Override
    public StoredUser findNonPendingUser( String nameOrId )
    {
        Query findByName = getSession( true ).getNamedQuery( StoredUser.QUERY_BY_NAME_OR_ID );
        return (StoredUser) findByName.setParameter( "nameOrId", nameOrId ).uniqueResult();
    }

    /**
     * @inheritDoc
     */
    @Override
    public StoredUser get( String userId )
    {
        return (StoredUser) getHibernateTemplate().get( StoredUser.class, userId );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void saveUser( StoredUser user )
    {
        user.setLastModified( new DateTime() );
        getHibernateTemplate().saveOrUpdate( user );
    }
}
