package swimming.competition.repository;

import swimming.competition.domain.User;

import java.sql.SQLException;

public interface JPAUserRepository {
    User findUser(String userName) throws SQLException;
}
