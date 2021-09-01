/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Institute;
import entity.account.DyscalculiaPatient;
import static entity.account.DyscalculiaPatient.FIND_ALL;
import static entity.account.DyscalculiaPatient.FIND_BY_ID;
import static entity.account.DyscalculiaPatient.FIND_BY_NAME;
import static entity.account.DyscalculiaPatient.FIND_BY_SCHOOL;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.interfaces.AccountOperation;
import model.interfaces.Storable;
import model.pool.EntityManagerPool;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */ 
public class DyscalculiaPatientModel implements Storable<DyscalculiaPatient>, AccountOperation {

    @Override
    public void doSave(DyscalculiaPatient toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public DyscalculiaPatient doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaPatient> query = em.createNamedQuery(FIND_BY_ID, DyscalculiaPatient.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<DyscalculiaPatient> resultList = query.getResultList();
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
    @Override
    public List<DyscalculiaPatient> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaPatient> query = em.createNamedQuery(FIND_ALL, DyscalculiaPatient.class);
        List<DyscalculiaPatient> patients = query.getResultList();
        EntityManagerPool.releaseEntityManager(em);
        
        return patients;
    }

    @Override
    public DyscalculiaPatient doUpdate(DyscalculiaPatient toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        DyscalculiaPatient patient = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        return patient;
    }

    @Override
    public void doDelete(DyscalculiaPatient toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }
    
    public List<DyscalculiaPatient> doRetrieveByName(String name) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaPatient> query = em.createNamedQuery(FIND_BY_NAME, DyscalculiaPatient.class);
        query.setParameter("name", name);
        List<DyscalculiaPatient> patients = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return patients;
    }
    
    public DyscalculiaPatient doRetrieveBySchool(String classroomCode, Institute institute, String schoolRegistar) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaPatient> query = em.createNamedQuery(FIND_BY_SCHOOL, DyscalculiaPatient.class);
        query.setParameter("classroomCode", classroomCode);
        query.setParameter("institute", institute.getId());
        query.setParameter("schoolRegister", schoolRegistar);
        query.setMaxResults(1);
        List<DyscalculiaPatient> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public DyscalculiaPatient doLogin(String email, String password) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaPatient> query = em.createNamedQuery(DyscalculiaPatient.DO_LOGIN, DyscalculiaPatient.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        query.setMaxResults(1);
        List<DyscalculiaPatient> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public DyscalculiaPatient doRetrieveByEmail(String email) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaPatient> query = em.createNamedQuery(DyscalculiaPatient.FIND_BY_EMAIL, DyscalculiaPatient.class);
        query.setParameter("email", email);
        query.setMaxResults(1);
        List<DyscalculiaPatient> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public DyscalculiaPatient doRetrieveByEmailToken(String emailToken) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaPatient> query = em.createNamedQuery(DyscalculiaPatient.FIND_BY_EMAIL_TOKEN, DyscalculiaPatient.class);
        query.setParameter("emailToken", emailToken);
        query.setMaxResults(1);
        List<DyscalculiaPatient> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
    /**
     * 
     * @return a List of patients who have taken at least one quiz
     */
    public List<DyscalculiaPatient> doRetrieveAllQuizzed() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<DyscalculiaPatient> query = em.createNamedQuery(DyscalculiaPatient.FIND_ALL_QUIZZED, DyscalculiaPatient.class);
        List<DyscalculiaPatient> patients = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return patients;
    }
}
