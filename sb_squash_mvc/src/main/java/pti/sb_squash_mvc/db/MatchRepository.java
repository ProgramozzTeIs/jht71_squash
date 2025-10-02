package pti.sb_squash_mvc.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pti.sb_squash_mvc.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Integer>{

}
