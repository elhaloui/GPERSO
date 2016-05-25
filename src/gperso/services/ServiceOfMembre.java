package gperso.services;

import gperso.models.Membre;
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
public class ServiceOfMembre {

    private final Logger log = LoggerFactory.getLogger(ServiceOfMembre.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfMembre(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Membre aMembre) throws Exception {
        getSessionFactory().save(aMembre);
        aMembre.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Membre aMembre) throws Exception {
        getSessionFactory().update(aMembre);
        aMembre.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Membre aMembre) throws Exception {
        getSessionFactory().delete(aMembre);
    }

    public List<Membre> findAll() throws Exception {
        return getSessionFactory().createQuery("from Membre").list();
    }

}
