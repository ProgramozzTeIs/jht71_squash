package pti.sb_squash_mvc.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pti.sb_squash_mvc.db.UserRepository;
import pti.sb_squash_mvc.model.User;

@Component
public class LoginValidator {

	private UserRepository userRepository;
	
	
	@Autowired
	public LoginValidator(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	/** MŰKÖDÉSE:
	 * 				HA USERID NEM LÉTEZIK - UNKNOWN
	 * 				HA USERID LÉTEZIK ÉS LOGGED_IN = 0 - UNKNOWN 
	 * 								  ÉS LOGGED_IN = 1 - A ROLE ÉRTÉKE (ADMIN VAGY PLAYER) */

	public Roles isUserLoggedIn(int userId)
	{
		Roles result = Roles.UNKNOWN;
		User user = null;
		
		/** GET USER'S DATA FROM REPO */
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(userOpt.isEmpty() == false)
		{
			user = userOpt.get();
			/** IF USER IS LOGGED IN, RETURN THE ROLE (ADMIN, PLAYER)*/
			if(user.isLoggedIn() == true)
			{
				result = user.getRole();
			}
			/** IF NOT LOGGED IN, RETURN UNKNOWN WHICH IS THE DEFAULT VALUE OF RESULT */
		}
			
		return result;
		
	}
	
}
