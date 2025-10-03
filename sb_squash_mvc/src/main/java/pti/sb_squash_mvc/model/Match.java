package pti.sb_squash_mvc.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="matches")
public class Match {
	

	public Match() {
		super();
	}

	public Match(int id, int placeId, int user1Id, int user1Points, int user2Id, int user2Points, LocalDate date) {
		super();
		this.id = id;
		this.placeId = placeId;
		this.user1Id = user1Id;
		this.user1Points = user1Points;
		this.user2Id = user2Id;
		this.user2Points = user2Points;
		this.date = date;
	}

	@Id
	@Column(value="id")
	private int id;
	
	@Column(value="place_id")
	private int placeId;
	
	@Column(value="user1_id")
	private int user1Id;
	
	@Column(value="user1_points")
	private int user1Points;
	
	@Column(value="user2_id")
	private int user2Id;
	
	@Column(value="user2_points")
	private int user2Points;
	
	@Column(value="match_date")
	private LocalDate date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public int getUser1Id() {
		return user1Id;
	}

	public void setUser1Id(int user1Id) {
		this.user1Id = user1Id;
	}

	public int getUser1Points() {
		return user1Points;
	}

	public void setUser1Points(int user1Points) {
		this.user1Points = user1Points;
	}

	public int getUser2Id() {
		return user2Id;
	}

	public void setUser2Id(int user2Id) {
		this.user2Id = user2Id;
	}
	
	public int getUser2Points() {
		return user2Points;
	}

	public void setUser2Points(int user2Points) {
		this.user2Points = user2Points;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "match [id=" + id + ", placeId=" + placeId + ", user1Id=" + user1Id + ", user1Points=" + user1Points
				+ ", user2Id=" + user2Id + ", date=" + date + "]";
	}
	
	
	
}
