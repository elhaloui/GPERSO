package gperso.services;

import gperso.models.Diplome;
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
public class ServiceOfDiplome {

    private final Logger log = LoggerFactory.getLogger(ServiceOfDiplome.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfDiplome(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Diplome aDiplome) throws Exception {
        getSessionFactory().save(aDiplome);
        aDiplome.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Diplome aDiplome) throws Exception {
        getSessionFactory().update(aDiplome);
        aDiplome.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Diplome aDiplome) throws Exception {
        getSessionFactory().delete(aDiplome);
    }

    public List<Diplome> findAll() throws Exception {
        return getSessionFactory().createQuery("from Diplome").list();
    }

}
