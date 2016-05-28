package gperso.services;

import gperso.models.Account;
import gperso.models.DemandeFormation;
import gperso.models.Formation;
import gperso.models.Personnel;
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
public class ServiceOfDemandeFormation {

    private final Logger log = LoggerFactory.getLogger(ServiceOfDemandeFormation.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ServiceOfDemandeFormation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSessionFactory() {
        return sessionFactory.getCurrentSession();
    }

       
    @Transactional(readOnly = false)
    public void save(DemandeFormation aDemandeFormation) throws Exception {
        getSessionFactory().save(aDemandeFormation);
        aDemandeFormation.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    @Transactional(readOnly = false)
    public void update(DemandeFormation aDemandeFormation) throws Exception {
        getSessionFactory().update(aDemandeFormation);
        aDemandeFormation.setLastUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Transactional(readOnly = false)
    public void delete(DemandeFormation aDemandeFormation) throws Exception {
        getSessionFactory().delete(aDemandeFormation);
    }

    public List<DemandeFormation> findAll() throws Exception {
        return getSessionFactory().createQuery("from DemandeFormation").list();
    }
    public DemandeFormation findOne(Account account , Formation formation) throws Exception {
        List list= getSessionFactory().createQuery("from DemandeFormation demande where demande.personnel.cin='"+account.getUsername()+"' and demande.formation.id="+formation.getId()).list();
        return list.size()>0?(DemandeFormation)list.get(0):null;
    }
    

}
