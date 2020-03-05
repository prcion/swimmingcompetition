package swimming.competition.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import swimming.competition.config.DatabaseConfig;
import swimming.competition.domain.Participant;
import swimming.competition.domain.Proba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ParticipantRepository {
	
	@Autowired
	private DatabaseConfig databaseConfig;
	
	private static final Logger logger= LogManager.getLogger();
	
	public int size(){
		Connection con = null;
		try {
			con = databaseConfig.getDataSource().getConnection();
		} catch (SQLException e) {
			logger.error("error in connection " + e.getMessage());
		}
		try(PreparedStatement preStmt=con.prepareStatement("select count(*) from participant")) {
			try(ResultSet result = preStmt.executeQuery()) {
				if (result.next()) {
					logger.trace(result.getInt(1));
					return result.getInt(1) + 1;
				}
			}
		} catch (SQLException e) {
			logger.error("error in prepared statement " + e.getMessage());
		}
		return 1;
	}
	
	public Integer save(Participant participant) throws SQLException {
		Connection con = databaseConfig.getDataSource().getConnection();
		PreparedStatement prep = con.prepareStatement("INSERT INTO participant value (?,?,?)");
		int size = findOne(participant.getName(), participant.getAge()) ;
		if(size != 0)
			return size;
		size = size();
		prep.setInt(1, size);
		prep.setString(2, participant.getName());
		prep.setInt(3, participant.getAge());
		prep.executeUpdate();
		return size;
	}
	
	public int findOne(String name, int age) throws SQLException {
		Connection con = databaseConfig.getDataSource().getConnection();
		
		PreparedStatement pr = con.prepareStatement("SELECT * FROM participant where name=? and age=?");
		pr.setString(1, name);
		pr.setInt(2, age);
		ResultSet rs = pr.executeQuery();
		while(rs.next()){
			return rs.getInt(1);
		}
		return 0;
	}
	
	public List<Participant> find(List<Proba> lst) throws SQLException {
		List<Participant> participants = new ArrayList<>();
		Connection con = databaseConfig.getDataSource().getConnection();
		for(Proba p : lst){
			PreparedStatement pr = con.prepareStatement("SELECT * FROM participant where id=?");
			pr.setInt(1, p.getIdParticipant());
			ResultSet rs = pr.executeQuery();
			while(rs.next()){
				Participant participant = new Participant();
				participant.setId(rs.getInt("id"));
				participant.setName(rs.getString("name"));
				participant.setAge(rs.getInt("age"));
				participants.add(participant);
			}
		}
		return participants;
	}
}
