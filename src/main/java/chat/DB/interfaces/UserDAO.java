package chat.DB.interfaces;


import chat.entity.User;

import java.sql.SQLException;

/**
 * Created by Владислав on 14.02.2018.
 */
public interface UserDAO {
    void save(User user) throws SQLException;
    User find(String log, String pass);
    User findByLogin(String log);
    User findFreeAgent();
    void markFree(String log, String pass, int port, String ip);
    void markFreeByLogin(String log);
    void markNotFree(String log, String pass);
    void markNotFreeByPort(int port);
    void markFreeByPort(int port);
    User getUser(String log, String pass);
    void setTypeConn(String log,String type);
}
