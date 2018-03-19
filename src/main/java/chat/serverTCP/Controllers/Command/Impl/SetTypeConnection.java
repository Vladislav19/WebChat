package chat.serverTCP.Controllers.Command.Impl;

import chat.DB.interfaces.UserDAO;
import chat.serverTCP.Controllers.Command.Interface.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Created by Владислав on 18.03.2018.
 */
public class SetTypeConnection implements Command{
    Object object;
    UserDAO userDAO;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    public SetTypeConnection(Object object, UserDAO userDAO, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.object = object;
        this.userDAO = userDAO;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }
    @Override
    public void execute() throws IOException, SQLException {
        String mas[] = ((String)object).split(" ");
        String type = mas[1];
        String login = mas[0];
        userDAO.setTypeConn(login,type);
    }
}
