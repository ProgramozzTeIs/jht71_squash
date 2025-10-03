package pti.sb_squash_mvc.dto;

import java.util.List;
import java.util.Set;


public class MatchWrapperDto {
	
	private List<MatchDto> matches;
	private Set<UserDto> users;
	private Set<PlaceDto> places;
	
	private int loggedInUserId;

	public MatchWrapperDto(List<MatchDto> matches, Set<UserDto> users, Set<PlaceDto> places, int loggedInUserId) {
		super();
		this.matches = matches;
		this.users = users;
		this.places = places;
		this.loggedInUserId = loggedInUserId;
	}

	public List<MatchDto> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchDto> matches) {
		this.matches = matches;
	}

	public Set<UserDto> getUsers() {
		return users;
	}

	public void setUsers(Set<UserDto> users) {
		this.users = users;
	}

	public Set<PlaceDto> getPlaces() {
		return places;
	}

	public void setPlaces(Set<PlaceDto> places) {
		this.places = places;
	}

	public int getLoggedInUserId() {
		return loggedInUserId;
	}

	public void setLoggedInUserId(int loggedInUserId) {
		this.loggedInUserId = loggedInUserId;
	}
	
	
	
	
	
}
