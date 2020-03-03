package swimming.competition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swimming.competition.domain.User;
import swimming.competition.repository.UserRepository;

import java.sql.SQLException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean verify(String userName, String password) throws SQLException {
		User user = userRepository.findUser(userName);
		if(user.getUsername() != null){
			if(user.getPassword().equals(password))
				return true;
		}
		return  false;
	}
}
