package afwks.dao;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import afwks.dao.StationDao;
import afwks.model.Line;
import afwks.model.Station;
import afwks.model.StationConstants;
import afwks.model.StationLine;


@ContextConfiguration(locations={"/afwks-dao-test.xml", "/sessionfactory-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
public class StationDaoTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Resource(name="stationDao")
	private StationDao stationDao;
	
	@Before
	public void init(){
		executeSqlScript("classpath:/stations.sql", true);
		Station station = new Station(StationConstants.CHATELET);
		stationDao.storeStation(station);
		station = new Station(StationConstants.STALINGRAD);
		stationDao.storeStation(station);
		station = new Station(StationConstants.JAURES);
		stationDao.storeStation(station);
		
	}
	
	@Test
	public void storeLine(){
		Assert.assertEquals(3,stationDao.getStations().size());
		//First line
		List<Station> stations = new LinkedList<Station>();
		Line line = new Line("Verte");
		stations.add(stationDao.getStationInstanceByName(StationConstants.CHATELET));
		stations.add(stationDao.getStationInstanceByName(StationConstants.STALINGRAD));
		line.addStations(stations);
		stationDao.storeLine(line);
			
		Assert.assertEquals(1,stationDao.getLines().size());
		
		Iterator<StationLine> iterator = line.getStationslines().iterator();
		
		Assert.assertEquals(1,iterator.next().getOrder().intValue());
		Assert.assertEquals(2,iterator.next().getOrder().intValue());
				
		//Second line
		line = new Line("Rouge");
		stations = new LinkedList<Station>();
		stations.add(stationDao.getStationInstanceByName(StationConstants.CHATELET));
		stations.add(stationDao.getStationInstanceByName(StationConstants.JAURES));
		line.addStations(stations);
		stationDao.storeLine(line);
		
		Assert.assertEquals(2,stationDao.getLines().size());
		Assert.assertEquals(3,stationDao.getStations().size());
		
	}
	
	@Test
	public void test_getLineByName(){
		
		List<Station> stations = new LinkedList<Station>();
		stations.add(stationDao.getStationInstanceByName(StationConstants.CHATELET));
		stations.add(stationDao.getStationInstanceByName(StationConstants.STALINGRAD));
		Line line = new Line("Verte");
		line.addStations(stations);
		stationDao.storeLine(line);
		
		Line result = stationDao.getLineByName("Verte");
		Assert.assertNotNull(result);
		Assert.assertEquals("Verte", result.getName());
	}
	
	@Test
	public void test_RemoveLine(){
			
		//First line
		List<Station> stations = new LinkedList<Station>();
		Line line = new Line("Verte");
		stations.add(stationDao.getStationInstanceByName(StationConstants.CHATELET));
		stations.add(stationDao.getStationInstanceByName(StationConstants.STALINGRAD));
		line.addStations(stations);
		stationDao.storeLine(line);
		Assert.assertEquals(2,stationDao.getStationsLines().size());
		
		//Second line
		line = new Line("Rouge");
		stations = new LinkedList<Station>();
		stations.add(stationDao.getStationInstanceByName(StationConstants.CHATELET));
		stations.add(stationDao.getStationInstanceByName(StationConstants.JAURES));
		line.addStations(stations);
		stationDao.storeLine(line);
		Assert.assertEquals(4,stationDao.getStationsLines().size());
		Assert.assertEquals(2,stationDao.getLines().size());
		
		stationDao.removeLine(stationDao.getLineByName("Rouge"));
		
		Assert.assertEquals(1,stationDao.getLines().size());
		Assert.assertEquals("Verte",stationDao.getLines().get(0).getName());
		Assert.assertEquals(3,stationDao.getStations().size());
		Assert.assertEquals(2,stationDao.getStationsLines().size());
	}
	
	@Test
	public void test_createAnonymousLine(){
		stationDao.createAnonymousLine();
		Line line = stationDao.getLineByName("anonymous3");
		Assert.assertNotNull(line);
	}
	
	@Test
	public void test_reading(){
		List<Station> stations = new LinkedList<Station>();
		Line line = new Line("Verte");
		stations.add(stationDao.getStationInstanceByName(StationConstants.CHATELET));
		stations.add(stationDao.getStationInstanceByName(StationConstants.STALINGRAD));
		line.addStations(stations);
		stationDao.storeLine(line);
		line.setName("Rouge");
		
		line = (Line)stationDao.getLineByName("Rouge");
		Assert.assertNull(line);
		
		line = (Line)stationDao.getLineByName("Verte");
		Assert.assertNotNull(line);
	}
	
	/**
	 *Call to getAnyLine make Hibernate synchronize with database
	 * => update sql request is pushed (to update the color)
	 * 
	 **/
	@Test
	public void test_synchronize_database(){
		List<Station> stations = new LinkedList<Station>();
		Line line = new Line("Verte");
		stations.add(stationDao.getStationInstanceByName(StationConstants.CHATELET));
		stations.add(stationDao.getStationInstanceByName(StationConstants.STALINGRAD));
		line.addStations(stations);
		stationDao.storeLine(line);
		line = stationDao.getLineByName("Verte");
		line.setName("Rouge");
		line = stationDao.getAnyLine(stationDao.getLineByName("Rouge").getId());
		Assert.assertEquals("Rouge", line.getName());
	}
}
