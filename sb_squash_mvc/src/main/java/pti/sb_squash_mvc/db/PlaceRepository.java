package pti.sb_squash_mvc.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pti.sb_squash_mvc.model.Place;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Integer>{

}
