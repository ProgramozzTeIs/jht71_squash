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
		
		/** ADATOK LEKÉRDEZÉSE USERREPO-BÓL */
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(userOpt.isEmpty() == false)
		{
			user = userOpt.get();
			/** HA A USER BE VAN JELENTKEZVE, AKKOR A ROLE-JÁT ADJA VISSZA (ADMIN, PLAYER)*/
			if(user.isLoggedIn() == true)
			{
				result = user.getRole();
			}
			/** HA A USER NINCS JELENTKEZVE, AKKOR A UNKNOWN ÉRTÉKET AD VISSZA (EZ AZ ALAPÉRTÉKE A RESULT VÁLTOZÓNAK) */
		}
			
		return result;
		
	}
	
}
