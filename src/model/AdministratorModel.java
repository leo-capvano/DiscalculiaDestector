/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.account.Administrator;
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
public class AdministratorModel implements Storable<Administrator>, AccountOperation {
    
    @Override
    public void doSave(Administrator toSave) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        em.persist(toSave);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
    }

    @Override
    public Administrator doRetrieveById(int id) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Administrator> query = em.createNamedQuery(Administrator.FIND_BY_ID, Administrator.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        List<Administrator> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<Administrator> doRetrieveAll() {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Administrator> query = em.createNamedQuery(Administrator.FIND_ALL, Administrator.class);
        List<Administrator> admins = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return admins;
    }

    @Override
    public Administrator doUpdate(Administrator toUpdate) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	em.getTransaction().begin();
    	
        Administrator admin = em.merge(toUpdate);
        
        em.getTransaction().commit();
        EntityManagerPool.releaseEntityManager(em);
        
        return admin;
    }

    @Override
    public void doDelete(Administrator toDelete) {
    	EntityManager em = EntityManagerPool.getEntityManager();
        em.getTransaction().begin();
    	
    	em.remove(em.merge(toDelete));
        
    	em.getTransaction().commit();
    	EntityManagerPool.releaseEntityManager(em);
    }
    
    @Override
    public Administrator doLogin(String email, String password) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Administrator> query = em.createNamedQuery(Administrator.DO_LOGIN, Administrator.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        query.setMaxResults(1);
        List<Administrator> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public Administrator doRetrieveByEmail(String email) {
        EntityManager em = EntityManagerPool.getEntityManager();
        
    	TypedQuery<Administrator> query = em.createNamedQuery(Administrator.FIND_BY_EMAIL, Administrator.class);
        query.setParameter("email", email);
        query.setMaxResults(1);
        List<Administrator> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);        
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public Administrator doRetrieveByEmailToken(String emailToken) {
    	EntityManager em = EntityManagerPool.getEntityManager();
    	
        TypedQuery<Administrator> query = em.createNamedQuery(Administrator.FIND_BY_EMAIL_TOKEN, Administrator.class);
        query.setParameter("emailToken", emailToken);
        query.setMaxResults(1);
        List<Administrator> resultList = query.getResultList();
        
        EntityManagerPool.releaseEntityManager(em);
        
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    
}
