package chat.serverWEB.services;


import chat.DB.implementations.UserDAOImpls;
import chat.DB.interfaces.UserDAO;
import chat.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by Владислав on 07.03.2018.
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String login) {
        UserDAO userDAO = new UserDAOImpls();
        User user = userDAO.findByLogin(login);
        return user;
    }
}
