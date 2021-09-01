/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.quiz.QuestionType;
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
public class QuestionTypeModel implements Storable<QuestionType> {
    
    @Override
    public void doSave(QuestionType toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public QuestionType doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuestionType> query = em.createNamedQuery(QuestionType.FIND_BY_ID, QuestionType.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<QuestionType> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<QuestionType> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuestionType> query = em.createNamedQuery(QuestionType.FIND_ALL, QuestionType.class);
        List<QuestionType> questionTypes = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return questionTypes;
    }

    @Override
    public QuestionType doUpdate(QuestionType toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        QuestionType questionType = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return questionType;
    }

    @Override
    public void doDelete(QuestionType toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }
    
}
