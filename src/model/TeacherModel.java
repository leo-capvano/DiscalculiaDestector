/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.account.Teacher;
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
public class TeacherModel implements Storable<Teacher>, AccountOperation {
    
    @Override
    public void doSave(Teacher toSave) {  
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public Teacher doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Teacher> query = em.createNamedQuery(Teacher.FIND_BY_ID, Teacher.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<Teacher> resultList = query.getResultList();
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<Teacher> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Teacher> query = em.createNamedQuery(Teacher.FIND_ALL, Teacher.class);
        List<Teacher> teachers = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return teachers;
    }

    @Override
    public Teacher doUpdate(Teacher toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        Teacher teacher = em.merge(toUpdate);

        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return teacher;
    }

    @Override
    public void doDelete(Teacher toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.remove(em.merge(toDelete));
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public Teacher doLogin(String email, String password) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Teacher> query = em.createNamedQuery(Teacher.DO_LOGIN, Teacher.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        query.setMaxResults(1);
        List<Teacher> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public Teacher doRetrieveByEmail(String email) {
        EntityManager em = EntityManagerPool.getEntityManager();
    	
    	TypedQuery<Teacher> query = em.createNamedQuery(Teacher.FIND_BY_EMAIL, Teacher.class);
        query.setParameter("email", email);
        query.setMaxResults(1);
        List<Teacher> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public Teacher doRetrieveByEmailToken(String emailToken) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Teacher> query = em.createNamedQuery(Teacher.FIND_BY_EMAIL_TOKEN, Teacher.class);
        query.setParameter("emailToken", emailToken);
        query.setMaxResults(1);
        List<Teacher> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
}
