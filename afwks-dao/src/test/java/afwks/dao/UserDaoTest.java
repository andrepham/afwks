package afwks.dao;

import javax.annotation.Resource;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import afwks.dao.UserDao;
import afwks.model.Address;
import afwks.model.User;

@ContextConfiguration(locations={"/afwks-dao-test.xml", "/sessionfactory-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Resource(name="userDao")
	private UserDao userDao;
	
	@Test
	public void testFindUserFromId() {
		
		User u1 = new User();
		Address a1 = new Address();
		a1.setNumber(1);
		a1.setStreet("street");
		a1.setTown("Paris");
		a1.setCountry("France");
		u1.setAddress(a1);
		u1.setEmail("email");
		u1.setFirstName("Jack");
		u1.setLastName("Chambers");
		u1.setPassword("password");
		userDao.store(u1);
		
		User retrievedUser1= userDao.findUserFromId(new Long(1));
		Assert.assertNotNull(retrievedUser1);
		
		User retrievedUser2= userDao.findUserFromId(new Long(2));
		Assert.assertNotNull(retrievedUser2);
		
		User retrievedUser3= userDao.findUserFromId(new Long(3));
		Assert.assertNull(retrievedUser3);
	}
	
	@Test
	public void testFindUserFromEmail(){
		String mail = "test@yahoo.fr";
		User user = userDao.findUserEmail(mail);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getEmail(), mail);
		
		String wrongMail = "WRONG@yahoo.fr";
		user = userDao.findUserEmail(wrongMail);
		Assert.assertNull(user);
	}
	
	@Before
	public void init(){
		executeSqlScript("classpath:/users.sql", true);
	}
}
