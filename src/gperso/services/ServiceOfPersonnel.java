package gperso.services;

import gperso.models.Personnel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
    
    public Personnel findOne(String cin) throws Exception {
        return getSessionFactory().get(Personnel.class, cin);
     }
    
     public List<String> findByCritere(String value) throws Exception {
        Criteria cr = getSessionFactory().createCriteria(Personnel.class);
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property("cin"));
        cr.setProjection(projList);
        cr.add(Restrictions.like("cin", value+"%"));
        return cr.list();

     }
}
