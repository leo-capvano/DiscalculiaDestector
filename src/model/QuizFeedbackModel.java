/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.quiz.QuizFeedback;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.interfaces.Storable;
import model.pool.EntityManagerPool;

/**
 *
 * @author Francesco Caprigline
 * @version 1.0
 */
public class QuizFeedbackModel implements Storable<QuizFeedback>{
    
    @Override
    public void doSave(QuizFeedback toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
    	em.getTransaction().commit();
    	EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public QuizFeedback doRetrieveById(int id) {
        EntityManager em = EntityManagerPool.getEntityManager();
    	
    	TypedQuery<QuizFeedback> query = em.createNamedQuery(QuizFeedback.FIND_BY_ID, QuizFeedback.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<QuizFeedback> resultList = query.getResultList();
        
    	EntityManagerPool.releaseEntityManager(em);
    	
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<QuizFeedback> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuizFeedback> query = em.createNamedQuery(QuizFeedback.FIND_ALL, QuizFeedback.class);
        List<QuizFeedback> quizFeedbacks = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return quizFeedbacks;
    }

    @Override
    public QuizFeedback doUpdate(QuizFeedback toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        QuizFeedback quizFeedback = em.merge(toUpdate);
        
        em.getTransaction().commit();
    	EntityManagerPool.releaseEntityManager(em);
    	
    	return quizFeedback;
    }

    @Override
    public void doDelete(QuizFeedback toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
    	EntityManagerPool.releaseEntityManager(em);
    }
    
    public List<QuizFeedback> doFindByPatientID(int patientID) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
    	TypedQuery<QuizFeedback> query = em.createNamedQuery(QuizFeedback.FIND_BY_PATIENT_ID, QuizFeedback.class);
        query.setParameter("patientID", patientID);
        List<QuizFeedback> quizFeedbacks = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return quizFeedbacks;
    }
    
}
