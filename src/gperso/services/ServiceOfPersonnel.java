package gperso.services;

import gperso.models.Personnel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 */
@Repository
@Transactional(readOnly = true)
public class ServiceOfPersonnel {

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfPersonnel(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = false)
    public void save(Personnel anPersonnel) throws Exception {
        getSessionFactory().save(anPersonnel);
    }

    @Transactional(readOnly = false)
    public void update(Personnel anPersonnel) throws Exception {
        getSessionFactory().update(anPersonnel);
    }

    @Transactional(readOnly = false)
    public void delete(Personnel anPersonnel) throws Exception {
        getSessionFactory().delete(anPersonnel);
    }

    public List<Personnel> findAll() throws Exception {
        return getSessionFactory().createQuery("from Personnel").list();
    }
}
