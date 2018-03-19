package chat.serverWEB.services;


import chat.entity.User;

/**
 * Created by Владислав on 07.03.2018.
 */
public interface UserService {
    User getUser(String login);
}
