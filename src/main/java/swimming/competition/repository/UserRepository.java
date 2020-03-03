package swimming.competition.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import swimming.competition.config.DatabaseConfig;
import swimming.competition.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
	
	@Autowired
	private DatabaseConfig databaseConfig;
	
	public User findUser(String userName) throws SQLException {
		Connection con = databaseConfig.getDataSource().getConnection();
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
