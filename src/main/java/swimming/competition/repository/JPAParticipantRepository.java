package swimming.competition.repository;

import swimming.competition.domain.Participant;
import swimming.competition.domain.Proba;

import java.sql.SQLException;
import java.util.List;

public interface JPAParticipantRepository {
    int size();
    Integer save(Participant participant) throws SQLException;
    int findOne(String name, int age) throws SQLException;
    List<Participant> find(List<Proba> lst) throws SQLException;
}
