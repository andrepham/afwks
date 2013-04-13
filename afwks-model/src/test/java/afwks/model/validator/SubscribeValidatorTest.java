package afwks.model.validator;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import afwks.model.validator.SubscribeValidator;

@ContextConfiguration(locations={"/afwks-model-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SubscribeValidatorTest{

	
	@Resource(name="subscribeValidator")
	private SubscribeValidator subscribeValidator;
	
	@Test
	public void test() {
		String email = "abcd5_e@provider.com";
		String notEmail = "abcd5_e";
		
		try {
			Method method = subscribeValidator.getClass().getDeclaredMethod("isEmail", String.class);
			method.setAccessible(true);
			boolean isEmail =(Boolean)method.invoke(subscribeValidator, email);
			Assert.assertTrue(isEmail);
//			isEmail =(Boolean)method.invoke(subscribeValidator, notEmail);
//			Assert.assertFalse(isEmail);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
