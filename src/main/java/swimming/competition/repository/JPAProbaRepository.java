package swimming.competition.repository;

import swimming.competition.domain.Participant;
import swimming.competition.domain.Proba;

import java.sql.SQLException;
import java.util.List;

public interface JPAProbaRepository {
    int size() throws SQLException;
    Integer save(Proba proba, int index) throws SQLException;
    Proba findOne(String style, String distance, int idParticipant) throws SQLException;
    List<Proba> find(String style, String distance) throws SQLException;
    List<Participant> findAll(List<Participant> participants) throws SQLException;
}
