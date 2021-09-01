/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.QuizStatistics;
import entity.account.DyscalculiaPatient;
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
public class QuizStatisticsModel implements Storable<QuizStatistics> {

    @Override
    public void doSave(QuizStatistics toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public QuizStatistics doRetrieveById(int id) {
        EntityManager em = EntityManagerPool.getEntityManager();
    	
    	TypedQuery<QuizStatistics> query = em.createNamedQuery(QuizStatistics.FIND_BY_ID, QuizStatistics.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<QuizStatistics> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<QuizStatistics> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<QuizStatistics> query = em.createNamedQuery(QuizStatistics.FIND_ALL, QuizStatistics.class);
        List<QuizStatistics> quizStatistics = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return quizStatistics;
    }

    @Override
    public QuizStatistics doUpdate(QuizStatistics toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        QuizStatistics quizStatistics = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return quizStatistics;
    }

    @Override
    public void doDelete(QuizStatistics toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }
    
    public List<QuizStatistics> doRetrieveByDyscalculiaPatient(DyscalculiaPatient patient) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
    	TypedQuery<QuizStatistics> query = em.createNamedQuery(QuizStatistics.FIND_BY_PATIENT, QuizStatistics.class);
        query.setParameter("patient", patient);
        List<QuizStatistics> quizStatistics = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return quizStatistics;
    }
}
