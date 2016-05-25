package gperso.services;

import gperso.models.Formation;
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
public class ServiceOfFormation {

    private final Logger log = LoggerFactory.getLogger(ServiceOfFormation.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfFormation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

       
    @Transactional(readOnly = false)
    public void save(Formation aFormation) throws Exception {
        getSessionFactory().save(aFormation);
        aFormation.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Formation aFormation) throws Exception {
        getSessionFactory().update(aFormation);
        aFormation.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Formation aFormation) throws Exception {
        getSessionFactory().delete(aFormation);
    }

    public List<Formation> findAll() throws Exception {
        return getSessionFactory().createQuery("from Formation").list();
    }

}
