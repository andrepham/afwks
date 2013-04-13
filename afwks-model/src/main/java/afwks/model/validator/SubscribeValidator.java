package afwks.model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import afwks.model.dto.SubscribingUserDTO;

public class SubscribeValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return SubscribingUserDTO.class.isAssignableFrom(arg0);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "required.firstName","First name is required.");		
	}
	
	private boolean isEmail(String string){
		Pattern p = Pattern.compile("^\\w+@\\w+\\.\\w+$");
		Matcher m = p.matcher(string);
		
		return m.find();
	}

}
