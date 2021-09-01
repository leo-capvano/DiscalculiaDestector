/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.quiz.DyscalculiaQuiz;
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
public class DyscalculiaQuizModel implements Storable<DyscalculiaQuiz> {
    
    @Override
    public void doSave(DyscalculiaQuiz toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public DyscalculiaQuiz doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaQuiz> query = em.createNamedQuery(DyscalculiaQuiz.FIND_BY_ID, DyscalculiaQuiz.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<DyscalculiaQuiz> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<DyscalculiaQuiz> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaQuiz> query = em.createNamedQuery(DyscalculiaQuiz.FIND_ALL, DyscalculiaQuiz.class);
        List<DyscalculiaQuiz> quizzes = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        return quizzes;
    }

    @Override
    public DyscalculiaQuiz doUpdate(DyscalculiaQuiz toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        DyscalculiaQuiz quiz = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return quiz;
    }

    @Override
    public void doDelete(DyscalculiaQuiz toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }
    
    public DyscalculiaQuiz doRetrieveByName(String name) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaQuiz> query = em.createNamedQuery(DyscalculiaQuiz.FIND_BY_NAME, DyscalculiaQuiz.class);
        query.setParameter("name", name);
        query.setMaxResults(1);
        List<DyscalculiaQuiz> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
    public List<DyscalculiaQuiz> doRetrieveAllTrusted() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaQuiz> query = em.createNamedQuery(DyscalculiaQuiz.FIND_ALL_TRUSTED, DyscalculiaQuiz.class);
        List<DyscalculiaQuiz> quizzes = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return quizzes;
    }
    
    public List<DyscalculiaQuiz> doRetrieveTrustedBySectionID(int sectionID) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaQuiz> query = em.createNamedQuery(DyscalculiaQuiz.FIND_TRUSTED_BY_SECTION_ID, DyscalculiaQuiz.class);
        query.setParameter("sectionID", sectionID);
        List<DyscalculiaQuiz> quizzes = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return quizzes;
    }     
    
    public List<DyscalculiaQuiz> doRetrieveByTeacher(int teacherID) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaQuiz> query = em.createNamedQuery(DyscalculiaQuiz.FIND_BY_TEACHER_ID, DyscalculiaQuiz.class);
        query.setParameter("teacherID", teacherID);
        
        List<DyscalculiaQuiz> quizzes = query.getResultList();
        EntityManagerPool.releaseEntityManager(em);
        
        return quizzes;
    }
}
