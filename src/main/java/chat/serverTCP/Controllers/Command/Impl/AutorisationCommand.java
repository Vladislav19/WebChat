package chat.serverTCP.Controllers.Command.Impl;



import chat.DB.interfaces.UserDAO;
import chat.entity.User;
import chat.serverTCP.Controllers.Command.Interface.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Владислав on 24.02.2018.
 */
public class AutorisationCommand implements Command {

    Object object;
    UserDAO userDAO;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    public AutorisationCommand(Object object, UserDAO userDAO, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.object = object;
        this.userDAO = userDAO;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void execute() throws IOException {
        String str = (String)object;
        String[] mas = str.split(" ");
        String log = mas[0];
        String pass = mas[1];
        User user;
        int port = Integer.parseInt(mas[2]);
        String ip = mas[3];
        if((user = userDAO.find(log,pass))!=null){
            userDAO.markFree(user.getLogin(),user.getPass(),port,ip);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
        }
        else {
            objectOutputStream.writeObject(null);
            objectOutputStream.flush();
        }
    }
}
