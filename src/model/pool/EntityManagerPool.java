package model.pool;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerPool {

	private static final String PERSISTENCE_UNIT_NAME = "TirocinioPU";
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	public static synchronized EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static synchronized void releaseEntityManager(EntityManager em) {
		em.close();
	}
}
