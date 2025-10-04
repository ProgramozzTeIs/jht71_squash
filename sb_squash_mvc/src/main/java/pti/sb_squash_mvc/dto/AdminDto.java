package pti.sb_squash_mvc.dto;

import java.util.Set;

public class AdminDto {

	private Set<UserDto> users;
	private Set<PlaceDto> places;
	private int loggedInAdminId;
	private int code;
	
	
	public AdminDto(Set<UserDto> users, Set<PlaceDto> places, int loggedInAdminId, int code) {
		super();
		this.users = users;
		this.places = places;
		this.loggedInAdminId = loggedInAdminId;
		this.code = code;
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
	
	public int getLoggedInAdminId() {
		return loggedInAdminId;
	}
	
	public void setLoggedInAdminId(int loggedInAdminId) {
		this.loggedInAdminId = loggedInAdminId;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
}
