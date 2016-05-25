package gperso.services;

import gperso.models.Punition;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 */
@Repository
@Transactional(readOnly = true)
public class ServiceOfPunition {

    private final Logger log = LoggerFactory.getLogger(ServiceOfPunition.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfPunition(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Punition aPunition) throws Exception {
        getSessionFactory().save(aPunition);
        aPunition.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Punition aPunition) throws Exception {
        getSessionFactory().update(aPunition);
        aPunition.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Punition aPunition) throws Exception {
        getSessionFactory().delete(aPunition);
    }

    public List<Punition> findAll() throws Exception {
        return getSessionFactory().createQuery("from Punition").list();
    }

}
