package pti.sb_squash_mvc.db;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pti.sb_squash_mvc.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Integer>{

	@Query("SELECT * FROM matches ORDER BY match_date desc")
	Iterable<Match> getMatchesListInDescOrder();
	
	@Query("SELECT * FROM matches WHERE place_id = :placeId ORDER BY match_date desc")
	List<Match> getMatchesListByPlace(@Param("placeId") Integer placeId);
	
	@Query("SELECT * FROM matches WHERE user1_id = :userId OR user2_id = :userId ORDER BY match_date desc")
	public List<Match> getMatchesListByUserId(@Param("userId")Integer userId);


	
	
}
