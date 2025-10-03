package pti.sb_squash_mvc.dto;

import java.time.LocalDate;

public class MatchDto {
	
	private LocalDate matchDate;
	private PlaceDto placeDto;
	private UserDto user1Dto;
	private int user1Point;
	private UserDto user2Dto;
	private int user2Point;
	
	
	public MatchDto(LocalDate matchDate, PlaceDto placeDto, UserDto user1Dto, int user1Point, UserDto user2Dto,
			int user2Point) {
		super();
		this.matchDate = matchDate;
		this.placeDto = placeDto;
		this.user1Dto = user1Dto;
		this.user1Point = user1Point;
		this.user2Dto = user2Dto;
		this.user2Point = user2Point;
	}


	public LocalDate getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(LocalDate matchDate) {
		this.matchDate = matchDate;
	}

	public PlaceDto getPlaceDto() {
		return placeDto;
	}

	public void setPlaceDto(PlaceDto placeDto) {
		this.placeDto = placeDto;
	}


	public UserDto getUser1Dto() {
		return user1Dto;
	}

	public void setUser1Dto(UserDto user1Dto) {
		this.user1Dto = user1Dto;
	}

	public int getUser1Point() {
		return user1Point;
	}

	public void setUser1Point(int user1Point) {
		this.user1Point = user1Point;
	}

	public UserDto getUser2Dto() {
		return user2Dto;
	}

	public void setUser2Dto(UserDto user2Dto) {
		this.user2Dto = user2Dto;
	}

	public int getUser2Point() {
		return user2Point;
	}

	public void setUser2Point(int user2Point) {
		this.user2Point = user2Point;
	}

}
