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
import pti.sb_squash_mvc.dto.UserDto;
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
	
	//POST::localhost:8080/login?name=Y&password=Z
		@PostMapping("/login")
		public String doLogin(
					Model model,
					@RequestParam("name") String name,
					@RequestParam("password") String password)
		{
			
			String resultHtml = "";
			
			int loginResult = this.service.doLogin(name, password);
			
			if(loginResult == 0)
			{
				ErrorDto errorDto = new ErrorDto(0);
				model.addAttribute("errorDto", errorDto);
				resultHtml = "login.html";
			}
			
			else if(loginResult == 1)
			{
				UserDto userDto = service.getUserDto(name);	
				model.addAttribute("userDto", userDto);
				
				resultHtml = "changepwd.html";

			}

			else if(loginResult == 2)

			{
				MatchWrapperDto matchWrapperDto = service.loginPlayer(name,password);
				
				model.addAttribute("matchWrapperDto", matchWrapperDto);
				resultHtml = "matches.html";

			}
			else if(loginResult == 3)
			{			
				int adminId = this.service.getUserId(name, password);
				AdminDto adminDto = this.service.loadAdminPage(adminId);
			
				model.addAttribute("adminDto", adminDto);
				resultHtml = "admin.html";
			}
			
			return resultHtml;
		}	

	@GetMapping("/matches/filter/user")
	public String filterMatchesByUser(
			Model model,
			@RequestParam("userId") int loggedInUserId,
			@RequestParam("selectedUserId") int selectedUserId) {
			
		
		String html = "";
		Roles role = loginValidator.isUserLoggedIn(loggedInUserId);
		
		if(role == Roles.ADMIN)
		{
			MatchWrapperDto matchWrapperDto = this.service.matchWrapperDtoMaker(loggedInUserId, null, selectedUserId);
			model.addAttribute("matchWrapperDto", matchWrapperDto);
			html = "matches.html";
		}
		else
		{
			ErrorDto errorDto = new ErrorDto(0);
			model.addAttribute("errorDto", errorDto);
			html = "login.html"; 
		}
		
		return html;
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
				ErrorDto errorDto = new ErrorDto(0);
				model.addAttribute("errorDto", errorDto);
				resultHtml = "login.html";
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

			String resultHtml = "";
			
			Roles role = loginValidator.isUserLoggedIn(adminId);
			
			if(role.equals(Roles.ADMIN))
			{
				AdminDto adminDto = this.service.loadAdminPage(adminId);
				
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

		
		
		@PostMapping("/changepwd")
		public String changePassword(
					Model model,
					@RequestParam("userId") int userId,
					@RequestParam("newPassword") String newPassword,
					@RequestParam("confirmPassword") String confirmPassword){
				
			String nextPage = "";
			
			UserDto userDto = service.changePassword(userId, newPassword, confirmPassword);
			model.addAttribute("userDto", userDto);
						
			if(userDto == null) {		
				nextPage = "changepwd.html";
			}
			else {
				nextPage = "redirect:/matches";
			}
			
			return nextPage;
		}
		
		
		
		@PostMapping("/admin/save/user")
		public String saveUser(
		        Model model,
		        @RequestParam("adminId") int adminId,
		        @RequestParam("userName") String userName) {
			
			AdminDto adminDto = this.service.saveUser(adminId, userName);
			
			if(adminDto == null) {
				
				ErrorDto errorDto = new ErrorDto(1);
			    model.addAttribute("errorDto", errorDto);
			    return "login.html";
			}
			
			model.addAttribute("adminDto", adminDto);
			
		    return "admin.html"; 
		}
		
		

		@PostMapping("/logout")
		public String logout(
		        Model model,
		        @RequestParam("loggedInUserId") int loggedInUserId) {

		    ErrorDto errorDto = this.service.logoutUser(loggedInUserId);

		    model.addAttribute("errorDto", errorDto);

		    return "login.html";
		}

	
}
