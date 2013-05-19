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
			//session.close();
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
			//session.close();
		}
	}
	
	public List<Line> getLines(){
		
		Session session = sessionFactory.getCurrentSession();
		List<Line> lines= (List<Line>)session.createQuery("from Line").list();
		
		return lines;
	}
	
	public List<Line> getStations(){
		
		Session session = sessionFactory.getCurrentSession();
		List<Line> stations= (List<Line>)session.createQuery("from Station").list();
		
		return stations;
	}
	
	public Line getLineByName(String name){
		
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Line where name=:name");
		query.setParameter("name", name);
		Line line =  (Line) query.uniqueResult();
		//session.close();
		
		return line;

	}
	
	public Station getStationInstanceByName(String name){
		
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Station where name=:name");
		query.setParameter("name", name);
		Station station = (Station)query.uniqueResult();
		//session.close();
		
		return station;
	}
	
	public List<StationLine>getStationsLines(){
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("from StationLine");
		List<StationLine>stationLines = query.list();
		//session.close();
		return stationLines;
	}
	
	private List<StationLine>getStationsLines(Line line){
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("from StationLine where line=:line");
		query.setParameter("line", line);
		List<StationLine>stationLines = query.list();
		//session.close();
		return stationLines;
	}
	
	public void removeLine(Line line){
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		 // NEED if no orphanRemoval attribute
		for(StationLine stationLineToDel : getStationsLines(line)){
			session.delete(stationLineToDel);
		}
		session.delete(line);
		tx.commit();
		//session.close();
		
	}
	
	public void createAnonymousLine(){
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Line line = new Line();
		line.setName("anonymous");
		session.saveOrUpdate(line);
		tx.commit();
		session.close();
		line.setName("anonymous2");
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		
		/////////////////////////////////////////////////////////
		/* 'merge' return a reference to the managed instance.
		 * So the following 'line.setName(...)' has persistent effect.
		*/
		line = (Line)session.merge(line); //this affectation is decisive, otherwise this test fails
		line.setName("anonymous3");
//	    ///////////////////////////////////////////////////////
		tx.commit();
	}
	
	public Line getAnyLine(Long id){
		Session session = sessionFactory.getCurrentSession();
		Line line = (Line)session.get(Line.class, id);
		return line;
	}
	
	public Line loadAnyLine(Long id){
		Session session = sessionFactory.getCurrentSession();
		Line line = (Line)session.load(Line.class, id);
		return line;
	}
	
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
