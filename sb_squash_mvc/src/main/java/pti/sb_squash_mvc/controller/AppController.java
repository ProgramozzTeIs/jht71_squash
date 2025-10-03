package pti.sb_squash_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.dto.MatchWrapperDto;
import pti.sb_squash_mvc.service.AppService;
import pti.sb_squash_mvc.util.LoginValidator;
import pti.sb_squash_mvc.util.Roles;

@Controller
public class AppController {
	
	private AppService service;
	private LoginValidator loginValidator;
	

	@Autowired
	public AppController(AppService service, LoginValidator loginValidator) {
		super();
		this.service = service;
		this.loginValidator = loginValidator;
	}


	
	@GetMapping("/matches/filter/place")
	public String filterMatchesListByPlace(
				Model model,
				@RequestParam("loggedinuserid") int loggedInUserId,
				@RequestParam("selectedplaceid") int selectedPlaceId) {
		
		String html = "";
		
		Roles role = loginValidator.isUserLoggedIn(loggedInUserId);
		
		if(role != Roles.UNKNOWN)
		{
			MatchWrapperDto matchWrapperDto = this.service.matchWrapperDtoMaker(loggedInUserId, selectedPlaceId);
			model.addAttribute("matchWrapperDto", matchWrapperDto);
			html = "matches.html";
		}
		else
		{
			/** GYÖNGYI EZ ITT A TE RÉSZED */
		}

		
		return html;
		
	}
}
