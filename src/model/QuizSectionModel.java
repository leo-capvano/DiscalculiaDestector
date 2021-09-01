/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.quiz.QuizSection;
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
public class QuizSectionModel implements Storable<QuizSection> {

    @Override
    public void doSave(QuizSection toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public QuizSection doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuizSection> query = em.createNamedQuery(QuizSection.FIND_BY_ID, QuizSection.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<QuizSection> resultList = query.getResultList();
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<QuizSection> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuizSection> query = em.createNamedQuery(QuizSection.FIND_ALL, QuizSection.class);
        List<QuizSection> quizSections = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        return quizSections;
    }

    @Override
    public QuizSection doUpdate(QuizSection toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        QuizSection quizSection = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return quizSection;
    }

    @Override
    public void doDelete(QuizSection toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }
}
