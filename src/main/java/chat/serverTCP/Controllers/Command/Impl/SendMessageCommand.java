package chat.serverTCP.Controllers.Command.Impl;

import chat.DB.interfaces.UserDAO;

import chat.entity.Chat;
import chat.entity.Repository;
import chat.serverTCP.Controllers.Command.Interface.Command;
import chat.serverTCP.Logic.MainLogic;
import chat.serverTCP.ServerConnetion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Created by Владислав on 11.03.2018.
 */
public class SendMessageCommand implements Command {


    UserDAO userDAO;
    String string;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    String login;String message;
    MainLogic mainLogic = new MainLogic();

    public SendMessageCommand(Object object, UserDAO userDAO, String string, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.message = (String)object;
        this.userDAO = userDAO;
        this.string = string;
        String[] str = string.split(" ");
        login=str[1];
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    public SendMessageCommand(String message, String login) {
        this.message = message;
        this.login = login;
    }

    @Override
    public void execute() throws IOException, SQLException {
        System.out.println(Repository.getInstance().sockets.size());
        System.out.println(Repository.getInstance().chats.size());
        for (Chat c:Repository.getInstance().chats) {
            if(c.getLoginAgent().equals(login)){
                if(c.getTypeClient().equals("TCP")){
                    int port = mainLogic.findChaterByLogin(login);
                    ObjectOutputStream ous = mainLogic.findSocketByPort(port);
                    ous.writeUTF(message);
                    ous.flush();
                }else{
                    c.getMessageHistoryAgent().add(message);
                }
            }
            if(c.getLoginClient().equals(login)){
                if(c.getTypeAgent().equals("TCP")){
                    int port = mainLogic.findChaterByLogin(login);
                    ObjectOutputStream ous = mainLogic.findSocketByPort(port);
                    ous.writeUTF(message);
                    ous.flush();
                }else{
                    c.getMessageHistoryClient().add(message);
                }
            }
        }


    }
}
