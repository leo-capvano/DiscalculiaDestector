/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Institute;
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
public class InstituteModel implements Storable<Institute> {
    
    @Override
    public void doSave(Institute toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public Institute doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Institute> query = em.createNamedQuery(Institute.FIND_BY_ID, Institute.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<Institute> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<Institute> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Institute> query = em.createNamedQuery(Institute.FIND_ALL, Institute.class);
        List<Institute> institutes = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        return institutes;
    }

    @Override
    public Institute doUpdate(Institute toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        Institute institute = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return institute;
    }

    @Override
    public void doDelete(Institute toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }
    
}
