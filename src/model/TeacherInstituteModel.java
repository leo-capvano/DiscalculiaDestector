package model;

import entity.TeacherInstitute;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.interfaces.Storable;
import model.pool.EntityManagerPool;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class TeacherInstituteModel implements Storable<TeacherInstitute> {
    
    @Override
    public void doSave(TeacherInstitute toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public TeacherInstitute doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
    	TypedQuery<TeacherInstitute> query = em.createNamedQuery(TeacherInstitute.FIND_BY_ID, TeacherInstitute.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<TeacherInstitute> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<TeacherInstitute> doRetrieveAll() {
        EntityManager em = EntityManagerPool.getEntityManager();
    	
    	TypedQuery<TeacherInstitute> query = em.createNamedQuery(TeacherInstitute.FIND_ALL, TeacherInstitute.class);
        List<TeacherInstitute> teacherInstitutes = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return teacherInstitutes;
    }

    @Override
    public TeacherInstitute doUpdate(TeacherInstitute toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
    	TeacherInstitute teacherInstitute = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        return teacherInstitute;
    }

    @Override
    public void doDelete(TeacherInstitute toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

}
