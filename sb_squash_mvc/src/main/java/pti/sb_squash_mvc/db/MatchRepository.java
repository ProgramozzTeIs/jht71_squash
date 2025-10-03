package pti.sb_squash_mvc.db;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pti.sb_squash_mvc.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Integer>{

	@Query("SELECT * FROM matches WHERE place_id = :placeId")
	List<Match> getMatchesListByPlace(@Param("placeId") int placeId);
	
}
