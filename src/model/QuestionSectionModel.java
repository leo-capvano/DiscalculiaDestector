/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.quiz.DyscalculiaQuiz;
import entity.quiz.QuestionSection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.interfaces.Storable;
import model.pool.EntityManagerPool;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
public class QuestionSectionModel implements Storable<QuestionSection> {
    
    @Override
    public void doSave(QuestionSection toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public QuestionSection doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuestionSection> query = em.createNamedQuery(QuestionSection.FIND_BY_ID, QuestionSection.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<QuestionSection> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<QuestionSection> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuestionSection> query = em.createNamedQuery(QuestionSection.FIND_ALL, QuestionSection.class);
        List<QuestionSection> questionSections = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return questionSections;
    }

    @Override
    public QuestionSection doUpdate(QuestionSection toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        QuestionSection questionSection = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return questionSection;
    }

    @Override
    public void doDelete(QuestionSection toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }
    
    public QuestionSection doRetrieveByNameAndQuiz(String name, DyscalculiaQuiz quiz) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuestionSection> query = em.createNamedQuery(QuestionSection.FIND_BY_NAME_AND_QUIZ, QuestionSection.class);
        query.setParameter("name", name);
        query.setParameter("quiz", quiz);
        query.setMaxResults(1);
        List<QuestionSection> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
}
