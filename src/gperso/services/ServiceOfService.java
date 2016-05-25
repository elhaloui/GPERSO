package gperso.services;

import gperso.models.Service;
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
public class ServiceOfService {

    private final Logger log = LoggerFactory.getLogger(ServiceOfService.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Service aService) throws Exception {
        getSessionFactory().save(aService);
        aService.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(Service aService) throws Exception {
        getSessionFactory().update(aService);
        aService.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(Service aService) throws Exception {
        getSessionFactory().delete(aService);
    }

    public List<Service> findAll() throws Exception {
        return getSessionFactory().createQuery("from Service").list();
    }

}
