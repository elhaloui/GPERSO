package gperso.services;

import gperso.models.Conge;
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
public class ServiceOfConge {

    private final Logger log = LoggerFactory.getLogger(ServiceOfConge.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfConge(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Conge aConge) throws Exception {
        getSessionFactory().save(aConge);
        aConge.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Conge aConge) throws Exception {
        getSessionFactory().update(aConge);
        aConge.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Conge aConge) throws Exception {
        getSessionFactory().delete(aConge);
    }

    public List<Conge> findAll() throws Exception {
        return getSessionFactory().createQuery("from Conge").list();
    }

}
