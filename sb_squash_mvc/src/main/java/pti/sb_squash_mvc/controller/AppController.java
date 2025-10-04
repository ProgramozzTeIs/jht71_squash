package pti.sb_squash_mvc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.dto.AdminDto;
import pti.sb_squash_mvc.dto.ErrorDto;
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
	
	@GetMapping("/")
	public String loadLoginPage() {
			
		return "login.html";	
	}


	//GET::localhost:8080/matches/filter/place?loggedinuserid=X&selectedplaceid=Y
	@GetMapping("/matches/filter/place")
	public String filterMatchesListByPlace(
				Model model,
				@RequestParam("loggedinuserid") Integer loggedInUserId,
				@RequestParam("selectedplaceid") Integer selectedPlaceId) {
		
		String resultHtml = "";
		
		Roles role = loginValidator.isUserLoggedIn(loggedInUserId);
		
		if(role != Roles.UNKNOWN)
		{
			MatchWrapperDto matchWrapperDto = this.service.matchWrapperDtoMaker(0, selectedPlaceId, selectedPlaceId);
			model.addAttribute("matchWrapperDto", matchWrapperDto);
			resultHtml = "matches.html";
		}
		else
		{
			/** GYÖNGYI EZ ITT A TE RÉSZED */
		}

		
		return resultHtml;
		
	}
	
	@PostMapping("/admin/save/match")
	public String saveNewMatch(
			Model model,
			@RequestParam("placeId") int placeId,
			@RequestParam("user1Id") int user1Id,
			@RequestParam("user1Points") int user1Points,
			@RequestParam("user2Id") int user2Id,
			@RequestParam("user2Points") int user2Points,
			@RequestParam("date") LocalDate date,
			@RequestParam("adminId") int adminId
			){
		
		
		String resultHtml = "admin.html";
		
		if(loginValidator.isUserLoggedIn(adminId) == Roles.ADMIN) {
			
			resultHtml = "admin.html";
			
			 int code = service.saveNewMatch(
					 placeId,
					 user1Id,
					 user1Points,
					 user2Id,
					 user2Points,
					 date);
			 
			 AdminDto adminDto = service.getAdminDto(code ,adminId);
			 model.addAttribute("adminDto" ,adminDto);
			 
		}
			
		else{
			
			resultHtml = "login.html";
			
			 }
		
		return resultHtml;
		
	}
	
	

	//POST::localhost:8080/matches/filter/place?adminid=X&name=Y&address=Z&rentfeehuf=XYZ
	@PostMapping("/admin/save/place")
	public String savePlaceInRepo(
					Model model,
					@RequestParam("adminId") int adminId,
					@RequestParam("placename") String placeName,
					@RequestParam("address") String address,
					@RequestParam("rentfeehuf") int rentFeeHuf 
					)
	{
		String resultHtml = "";
		
		Roles role = loginValidator.isUserLoggedIn(adminId);
		
		if(role.equals(Roles.ADMIN))
		{
			AdminDto adminDto = this.service.savePlaceInRepo(adminId, placeName, address, rentFeeHuf);
			
			model.addAttribute("adminDto", adminDto);
			resultHtml = "admin.html";
		}
		else
		{
			ErrorDto errorDto = new ErrorDto(1);
			
			model.addAttribute("errorDto", errorDto);
			resultHtml = "login.html";
		}

		
		return resultHtml;
	}
	
	@GetMapping("/admin")
	public String admin(
				Model model,
				@RequestParam("adminid") int adminId)
	{
		AdminDto adminDto = this.service.loadAdminPage(adminId);
		model.addAttribute("adminDto", adminDto);
		
		return "admin.html";
	}

}
