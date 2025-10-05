package pti.sb_squash_mvc.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pti.sb_squash_mvc.db.MatchRepository;
import pti.sb_squash_mvc.db.PlaceRepository;
import pti.sb_squash_mvc.db.UserRepository;
import pti.sb_squash_mvc.dto.AdminDto;
import pti.sb_squash_mvc.dto.MatchDto;
import pti.sb_squash_mvc.dto.MatchWrapperDto;
import pti.sb_squash_mvc.dto.PlaceDto;
import pti.sb_squash_mvc.dto.UserDto;
import pti.sb_squash_mvc.model.Match;
import pti.sb_squash_mvc.model.Place;
import pti.sb_squash_mvc.model.User;
import pti.sb_squash_mvc.util.LoginValidator;
import pti.sb_squash_mvc.util.Roles;

@Service
public class AppService {

	private MatchRepository matchRepository;
	private PlaceRepository placeRepository;
	private UserRepository userRepository;
	private LoginValidator loginValidator;
	
	
	@Autowired
	public AppService(MatchRepository matchRepository, PlaceRepository placeRepository, UserRepository userRepository,
			LoginValidator loginValidator) {
		super();
		this.matchRepository = matchRepository;
		this.placeRepository = placeRepository;
		this.userRepository = userRepository;
		this.loginValidator = loginValidator;
	}



	/**
	 * Belépés
	 * 0 --> Nem sikerült bejelentkezni
	 * 1 --> Első belépés, jelszó változtatás
	 * 2 --> Sikeres belépés, Játékos
	 * 3 --> Sikeres belépés, Admin	
	 */
	public int doLogin(String name, String password) {
		
		int loginResult = 0;
		
		User user = userRepository.getUserByName(name);
		
		if(user != null && user.getPassword().equals(password)) {
			
			if(user.isFirstLogin()) {
				loginResult = 1;
			}		
			else {		
				user.setLoggedIn(true);
				userRepository.save(user);		
							
				if(user.getRole() == Roles.PLAYER) {
				loginResult = 2;
				}		
				else if(user.getRole() == Roles.ADMIN) {
					loginResult = 3;
				}
			}
		}
		
		return loginResult;
	}
	

	public int getUserId(String name, String password)
	{
		int result = 0;
		
		User user = userRepository.getUserByName(name);
		
		if(user != null && user.getPassword().equals(password))
		{
			result = user.getId();
		}
			
		return result;
	}
	
	public MatchWrapperDto matchWrapperDtoMaker(int loggedInUserId, Integer selectedPlaceId ,Integer selectedUserId) {
			
			MatchWrapperDto matchWrapperDto = null;
			List<MatchDto> matchDtoList = new ArrayList<>();
			List<Match> matchList = new ArrayList<>();
			
			//1
			if (selectedPlaceId != null && selectedUserId == null)
			{
				matchList = matchRepository.getMatchesListByPlace(selectedPlaceId);
			}	
			//2
			else if(selectedPlaceId == null && selectedUserId != null)
			{
				matchList = matchRepository.getMatchesListByUserId(selectedUserId);
			}	
			//3
			else if(selectedPlaceId == null && selectedUserId == null)
			{
				Iterable<Match> matchIterable = matchRepository.findAll();
				for(Match match : matchIterable) {
					matchList.add(match);
				}		
			}
			//4
			/*Ha minden a két értéket megadják a frontendről akkor bejárjuk mind  két kapott listát a repositoryból,
			 * és összevonjuk őket egy listába ismétlések nélkűl.
			 */
			if (selectedPlaceId != null && selectedUserId != null) {
				
				matchList = matchRepository.getMatchesListByPlace(selectedPlaceId);
				
				List<Match> matchListByUserId = matchRepository.getMatchesListByUserId(selectedUserId);
				
				for(Match matchFromPlaceId : matchList) {
					for(Match matchFromUserId :matchListByUserId) {
						
						if(matchFromPlaceId.getId() != matchFromUserId.getId()) {
							matchList.add(matchFromUserId);
						}
					}
				}
						
			}
			

				for(Match match : matchList)
				{
					/** GET PLACE DATA FROM REPO BY ID */
					Optional<Place> placeOpt = placeRepository.findById(match.getPlaceId());
					Place place = placeOpt.get();
						/** CREATE PLACEDTO */
						PlaceDto placeDto = new PlaceDto(
														place.getId(),
														place.getName(),
														place.getAddress(),
														place.getRentFee());
						
					/** GET USER1 DATA FROM REPO BY ID */
					Optional<User> user1Opt = userRepository.findById(match.getUser1Id());
					User user1 = user1Opt.get();
						/** CREATE USERDTO */
						UserDto user1Dto = new UserDto(
														user1.getId(),
														user1.getName());
					
					/** GET USER2 DATA FROM REPO BY ID */
					Optional<User> user2Opt = userRepository.findById(match.getUser2Id());
					User user2 = user2Opt.get();
						/** CREATE USERDTO */
						UserDto user2Dto = new UserDto(
														user2.getId(),
														user2.getName());
					
					/** CREATE MATCHDTO */
					MatchDto matchDto = new MatchDto(
													match.getDate(),
													placeDto,
													user1Dto,
													match.getUser1Points(),
													user2Dto,
													match.getUser2Points());
					
					matchDtoList.add(matchDto);
				}
				
				matchWrapperDto = new MatchWrapperDto(
														matchDtoList,
														getAllUserFromRepo(),
														getAllPlaceFromRepo(),
														loggedInUserId
														);
				
				
			
			
			return matchWrapperDto;
		}
		
	


	private Set<UserDto> getAllUserFromRepo()
	{
		Set<UserDto> userDtoSet = new HashSet<>();
		
		Iterable<User> userList = this.userRepository.findAll();
		
		for(User user : userList)
		{
			UserDto userDto = new UserDto(
											user.getId(),
											user.getName());
			
			userDtoSet.add(userDto);
		}
		
		return userDtoSet;
	}
	
	private Set<PlaceDto> getAllPlaceFromRepo()
	{
		Set<PlaceDto> placeDtoList = new HashSet<>();
		
		Iterable<Place> placeList = this.placeRepository.findAll();
		
		for(Place place : placeList)
		{
			PlaceDto placeDto = new PlaceDto(
											place.getId(),
											place.getName(),
											place.getAddress(),
											place.getRentFee());
			
			placeDtoList.add(placeDto);
		}
		
		return placeDtoList;
	}


	public int saveNewMatch(int placeId, int user1Id, int user1Points, int user2Id, int user2Points,
			LocalDate date) {
		
		int result = 0;
	
		return result;
	}

	public AdminDto getAdminDto(int code ,int adminId) {
		
		AdminDto result = null;
		
		return result;
	}


	public AdminDto savePlaceInRepo(int adminId, String name, String address, int rentFeeHuf) {
		
		AdminDto adminDto = null;
		
		Place place = new Place(
								0,
								name,
								address,
								rentFeeHuf);
		
		Place savedPlace = this.placeRepository.save(place);
		
		if(savedPlace != null)
		{
			adminDto = new AdminDto(
									getAllUserFromRepo(),
									getAllPlaceFromRepo(),
									adminId,
									1 //saving was OK
									);
		}
		else
		{
			adminDto = new AdminDto(
									getAllUserFromRepo(),
									getAllPlaceFromRepo(),
									adminId,
									2 //saving was not OK
									);
		}
		
		return adminDto;
	}


	public AdminDto loadAdminPage(int adminId) {
		
		AdminDto adminDto = null;
		
		adminDto = new AdminDto(
								getAllUserFromRepo(),
								getAllPlaceFromRepo(),
								adminId,
								0
								);
		
		return adminDto;
	}



	public MatchWrapperDto loginPlayer(String name, String password) {
		
		MatchWrapperDto matchWrapperDto = null;
		
		Integer userId = userRepository.userLoggedIn(1, name, password);
		Optional<User> userOpt = this.userRepository.findById(userId);
		
		User user = userOpt.get();

		List<MatchDto> matchDtoList = new ArrayList<>();
				
		Iterable<Match> matchList = matchRepository.findAll();
		
		for (Match match : matchList) {
			
			// PlaceDto	
			Optional<Place> placeOpt = placeRepository.findById(match.getPlaceId());
			Place place = placeOpt.get();

			PlaceDto placeDto = new PlaceDto(
								place.getId(),
								place.getName(),
								place.getAddress(),
								place.getRentFee()
								);
				
			// User1
			Optional<User> user1Opt = userRepository.findById(match.getUser1Id());
			User user1 = user1Opt.get();
				
			UserDto user1Dto = new UserDto(
								user1.getId(),
								user1.getName()
								);
			
			// User2
			Optional<User> user2Opt = userRepository.findById(match.getUser2Id());
			User user2 = user2Opt.get();
			
			UserDto user2Dto = new UserDto(
								user2.getId(),
								user2.getName()
								);
			
			//MatchDto
			MatchDto matchDto = new MatchDto(
											match.getDate(),
											placeDto,
											user1Dto,
											match.getUser1Points(),
											user2Dto,
											match.getUser2Points());
			
			matchDtoList.add(matchDto);
			
		}
		
		matchWrapperDto = new MatchWrapperDto(
					matchDtoList,
					getAllUserFromRepo(),
					getAllPlaceFromRepo(),
					user.getId()
					);
		
				
		return matchWrapperDto;

	}
	
	
}
