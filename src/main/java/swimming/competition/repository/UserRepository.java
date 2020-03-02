package swimming.competition.repository;

import swimming.competition.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
	private DataSource dataSource;
	public UserRepository(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public User findUser(String userName) throws SQLException {
		Connection con = dataSource.getConnection();
		User user = new User();
		try(PreparedStatement prep = con.prepareStatement("Select * from user where user_name=?")){
			prep.setString(1, userName);
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
			}
		}
		return user;
	}
}
