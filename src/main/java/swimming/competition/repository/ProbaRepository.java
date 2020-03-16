package swimming.competition.repository;

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
public class ProbaRepository implements JPAProbaRepository {
	
	@Autowired
	private DatabaseConfig databaseConfig;
	
	public int size() throws SQLException {
		Connection con = databaseConfig.getDataSource().getConnection();
		try(PreparedStatement preStmt=con.prepareStatement("select count(*) from proba")) {
			try(ResultSet result = preStmt.executeQuery()) {
				if (result.next()) {
					return result.getInt(1) + 1;
				}
			}
		}
		return 1;
	}
	
	public Integer save(Proba proba, int index) throws SQLException {
		Proba pr = findOne(proba.getStyle(), proba.getDistance(), index);
		if(proba.getId() == 0 && pr.getId() == 0){
			Connection con = databaseConfig.getDataSource().getConnection();
			PreparedStatement prep = con.prepareStatement("INSERT INTO proba value (?,?,?,?)");
			int size = size();
			prep.setInt(1, size);
			prep.setString(2, proba.getStyle());
			prep.setString(3, proba.getDistance());
			prep.setInt(4, index);
			prep.executeUpdate();
			return size;
		}
		return 0;
	}
	
	public Proba findOne(String style, String distance, int idParticipant) throws SQLException {
		Connection con = databaseConfig.getDataSource().getConnection();
		Proba proba = new Proba();
		PreparedStatement pr = con.prepareStatement("SELECT * FROM proba where style=? and distance=? and id_participant=?");
		pr.setString(1, style);
		pr.setString(2, distance);
		pr.setInt(3, idParticipant);
		ResultSet rs = pr.executeQuery();
		while(rs.next()){
			proba.setId(rs.getInt(1));
			proba.setStyle(rs.getString(2));
			proba.setDistance(rs.getString(3));
			proba.setIdParticipant(rs.getInt(4));
			return proba;
		}
		return proba;
	}
	
	public List<Proba> find(String style, String distance) throws SQLException {
		List<Proba> lst = new ArrayList<>();
		Connection con = databaseConfig.getDataSource().getConnection();
		PreparedStatement pr = con.prepareStatement("SELECT * FROM proba where style=? and distance=?");
		pr.setString(1, style);
		pr.setString(2, distance);
		ResultSet rs = pr.executeQuery();
		while(rs.next()){
			Proba proba = new Proba();
			proba.setId(rs.getInt(1));
			proba.setStyle(rs.getString(2));
			proba.setDistance(rs.getString(3));
			proba.setIdParticipant(rs.getInt(4));
			lst.add(proba);
		}
		return lst;
	}
	
	public List<Participant> findAll(List<Participant> participants) throws SQLException {
		for(Participant participant :participants){
			Connection con = databaseConfig.getDataSource().getConnection();
			PreparedStatement pr = con.prepareStatement("SELECT * FROM proba where id_participant=?");
			pr.setInt(1, participant.getId());
			ResultSet rs = pr.executeQuery();
			while(rs.next()){
				Proba proba = new Proba();
				proba.setId(rs.getInt(1));
				proba.setStyle(rs.getString(2));
				proba.setDistance(rs.getString(3));
				proba.setIdParticipant(rs.getInt(4));
				participant.addProba(proba);
			}
		}
		return  participants;
	}
}
