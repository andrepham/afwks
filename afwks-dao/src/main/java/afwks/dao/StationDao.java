package afwks.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Required;
import afwks.model.Line;
import afwks.model.Station;
import afwks.model.StationLine;


public class StationDao {

	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void storeStation(Station station){
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try{
		tx.begin();
		session.merge(station);
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
	
	public void storeLine(Line line){
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try{
		tx.begin();
		session.merge(line);
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
	
	public List<Line> getLines(){
		
		Session session = sessionFactory.getCurrentSession();
		List<Line> lines= (List<Line>)session.createQuery("from Line").list();
		session.close();
		
		return lines;
	}
	
	public List<Line> getStations(){
		
		Session session = sessionFactory.getCurrentSession();
		List<Line> stations= (List<Line>)session.createQuery("from Station").list();
		session.close();
		
		return stations;
	}
	
	public Line getLineByName(String name){
		
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Line where name=:name");
		query.setParameter("name", name);
		Line result =  (Line) query.uniqueResult();
		session.close();
		
		return result;

	}
	
	public Station getStationInstanceByName(String name){
		
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Station where name=:name");
		query.setParameter("name", name);
		Station station = (Station)query.uniqueResult();
		session.close();
		
		if(station!=null){
			return station;
		}	
		
		return null;
	}
	
	public List<StationLine>getStationsLines(){
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("from StationLine");
		List<StationLine>stationLines = query.list();
		session.close();
		return stationLines;
	}
	
	private List<StationLine>getStationsLines(Line line){
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("from StationLine where line=:line");
		query.setParameter("line", line);
		List<StationLine>stationLines = query.list();
		session.close();
		return stationLines;
	}
	
	public void removeLine(Line line){
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		/*
		 * NO NEED => orphanRemoval is true
		 * 
		 * for(StationLine stationLineToDel : getStationsLines(line)){
			session.delete(stationLineToDel);
		}*/
		session.delete(line);
		tx.commit();
		session.close();
		
	}
	
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
