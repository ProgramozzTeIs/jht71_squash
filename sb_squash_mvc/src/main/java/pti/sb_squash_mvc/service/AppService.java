package pti.sb_squash_mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pti.sb_squash_mvc.db.MatchRepository;
import pti.sb_squash_mvc.db.PlaceRepository;
import pti.sb_squash_mvc.db.UserRepository;

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
	
	
	
	
}
