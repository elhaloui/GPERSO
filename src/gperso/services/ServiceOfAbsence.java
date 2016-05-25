package gperso.services;

import gperso.models.Absence;
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
public class ServiceOfAbsence {

    private final Logger log = LoggerFactory.getLogger(ServiceOfAbsence.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfAbsence(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Absence aAbsence) throws Exception {
        getSessionFactory().save(aAbsence);
        aAbsence.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Absence aAbsence) throws Exception {
        getSessionFactory().update(aAbsence);
        aAbsence.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Absence aAbsence) throws Exception {
        getSessionFactory().delete(aAbsence);
    }

    public List<Absence> findAll() throws Exception {
        return getSessionFactory().createQuery("from Absence").list();
    }

}
