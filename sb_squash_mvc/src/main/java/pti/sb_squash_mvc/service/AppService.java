package pti.sb_squash_mvc.service;

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
import pti.sb_squash_mvc.dto.MatchDto;
import pti.sb_squash_mvc.dto.MatchWrapperDto;
import pti.sb_squash_mvc.dto.PlaceDto;
import pti.sb_squash_mvc.dto.UserDto;
import pti.sb_squash_mvc.model.Match;
import pti.sb_squash_mvc.model.Place;
import pti.sb_squash_mvc.model.User;

@Service
public class AppService {

	private MatchRepository matchRepository;
	private PlaceRepository placeRepository;
	private UserRepository userRepository;
	
	
	@Autowired
	public AppService(MatchRepository matchRepository, PlaceRepository placeRepository, UserRepository userRepository) {
		super();
		this.matchRepository = matchRepository;
		this.placeRepository = placeRepository;
		this.userRepository = userRepository;
	}


	public MatchWrapperDto matchWrapperDtoMaker(int loggedInUserId, int selectedPlaceId) {
		
		MatchWrapperDto matchWrapperDto = null;
		List<MatchDto> matchDtoList = new ArrayList<>();
		
		if (selectedPlaceId != 0)
		{
			List<Match> matchList = matchRepository.getMatchesListByPlace(selectedPlaceId);
			
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
			
			
		}
		
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
	
	
}
