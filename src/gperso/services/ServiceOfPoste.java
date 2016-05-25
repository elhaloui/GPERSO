package gperso.services;

import gperso.models.Poste;
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
public class ServiceOfPoste {

    private final Logger log = LoggerFactory.getLogger(ServiceOfPoste.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfPoste(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Poste aPoste) throws Exception {
        getSessionFactory().save(aPoste);
        aPoste.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Poste aPoste) throws Exception {
        getSessionFactory().update(aPoste);
        aPoste.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Poste aPoste) throws Exception {
        getSessionFactory().delete(aPoste);
    }

    public List<Poste> findAll() throws Exception {
        return getSessionFactory().createQuery("from Poste").list();
    }

}
