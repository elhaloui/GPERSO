package gperso.services;

import gperso.models.Adresse;
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
public class ServiceOfAdresse {

    private final Logger log = LoggerFactory.getLogger(ServiceOfAdresse.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfAdresse(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Adresse aAdresse) throws Exception {
        getSessionFactory().save(aAdresse);
        aAdresse.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Adresse aAdresse) throws Exception {
        getSessionFactory().update(aAdresse);
        aAdresse.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Adresse aAdresse) throws Exception {
        getSessionFactory().delete(aAdresse);
    }

    public List<Adresse> findAll() throws Exception {
        return getSessionFactory().createQuery("from Adresse").list();
    }

}
