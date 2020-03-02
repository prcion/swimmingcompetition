package swimming.competition.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
	private int Id;
	private String username;
	private String password;
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
}
