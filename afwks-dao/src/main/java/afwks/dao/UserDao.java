package afwks.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Required;
import afwks.model.User;


public class UserDao {
	
	private SessionFactory sessionFactory;

	public void store(User user){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
		tx.begin();
		session.merge(user);
		tx.commit();
		}
		catch(RuntimeException re){
			tx.rollback();
			throw re;
		}
		finally{
			session.close();
		}
	}
	
	public User findUserFromId(Long id){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = null;
		try{
			tx.begin();
			user = (User)session.get(User.class, id);
			tx.commit();
		}
		catch(RuntimeException re){
			tx.rollback();
			throw re;
		}
		finally{
			session.close();
		}
		return user;
	}
	
	public User findUserEmail(String email){
		Session session = sessionFactory.openSession();
		List<Object> users = session.createCriteria(User.class).add(Restrictions.eq("email", email)).list();
		session.close();
		if(users!=null && !users.isEmpty()){
			return (User)users.get(0);
		}
		return null;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
