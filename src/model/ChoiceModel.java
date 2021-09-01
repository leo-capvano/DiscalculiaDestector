/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.question.Choice;
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
public class ChoiceModel implements Storable<Choice> {
    
    @Override
    public void doSave(Choice toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
    }

    @Override
    public Choice doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Choice> query = em.createNamedQuery(Choice.FIND_BY_ID, Choice.class);
        query.setParameter("id", id);
        List<Choice> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<Choice> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Choice> query = em.createNamedQuery(Choice.FIND_ALL, Choice.class);
        
        List<Choice> choices = query.getResultList();
        EntityManagerPool.releaseEntityManager(em);
        
        return choices;
    }

    @Override
    public Choice doUpdate(Choice toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        Choice choice = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return choice;
    }

    @Override
    public void doDelete(Choice toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
        em.getTransaction().begin();
    	
    	em.remove(em.merge(toDelete));
    	
    	em.getTransaction().commit();
    	EntityManagerPool.releaseEntityManager(em);
    }
    
}
