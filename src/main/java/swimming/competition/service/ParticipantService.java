package swimming.competition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swimming.competition.domain.Participant;
import swimming.competition.domain.Proba;
import swimming.competition.repository.ParticipantRepository;
import swimming.competition.repository.ProbaRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class ParticipantService {
	
	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private ProbaRepository probaRepository;
	
	public int save(Participant participant) throws SQLException {
		int index = participantRepository.save(participant);
		int size = 0;
		for(Proba p : participant.getProbe()){
			size = probaRepository.save(p, index);
			p.setId(size);
			p.setIdParticipant(index);
		}
		return size;
	}
	
	public List<Participant> find(String style, String distance) throws SQLException {
		List<Proba> lst = probaRepository.find(style, distance);
		List<Participant> participants = participantRepository.find(lst);
		return probaRepository.findAll(participants);
	}
}
