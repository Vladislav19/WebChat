package chat.serverTCP.Logic;


import chat.DB.implementations.UserDAOImpls;
import chat.DB.interfaces.UserDAO;
import chat.entity.Chat;
import chat.entity.Repository;
import chat.entity.SocketContain;
import chat.entity.User;
import chat.serverTCP.ServerConnetion;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainLogic {

    UserDAO userDAO = new UserDAOImpls();


    public MainLogic() {

    }

    public String searchFreeAgent(User user){
        User freeAgent = userDAO.findFreeAgent();
        if(freeAgent!=null){
            userDAO.markNotFree(freeAgent.getLogin(),freeAgent.getPass());
            Chat chatRoom = new Chat();
            chatRoom.setLoginClient(user.getLogin());
            chatRoom.setLoginAgent(freeAgent.getLogin());
            chatRoom.setTypeClient(user.getTypeConnection());
            chatRoom.setTypeAgent(freeAgent.getTypeConnection());
            Repository.getInstance().chats.add(chatRoom);
            return "success";
        }else{
            return "not found free agent";
        }
    }

    public  int findChaterByLogin(String login){
        User user;
        int port = 0;
        for (Chat chatRoom:Repository.getInstance().chats) {
            if(chatRoom.getLoginClient().equals(login)){
                String loginChater = chatRoom.getLoginAgent();
                user = userDAO.findByLogin(loginChater);
                port = user.getPort();
                break;
            }
            if(chatRoom.getLoginAgent().equals(login)){
                String loginChater = chatRoom.getLoginClient();
                user = userDAO.findByLogin(loginChater);
                port = user.getPort();
                break;
            }
            else {
                port = -1;
            }
        }
        return port;
    }

    public  ObjectOutputStream findSocketByPort(int port){
        ObjectOutputStream ous = null;
        for (SocketContain s : Repository.getInstance().sockets) {
            if(s.getSocket().getPort()==port){
                ous = s.getOus();break;
            }
        }
        return ous;
    }



}
