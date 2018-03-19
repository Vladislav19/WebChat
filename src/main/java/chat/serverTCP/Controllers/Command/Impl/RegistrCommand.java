package chat.serverTCP.Controllers.Command.Impl;


import chat.DB.interfaces.UserDAO;
import chat.entity.User;
import chat.serverTCP.Controllers.Command.Interface.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Created by Владислав on 24.02.2018.
 */
public class RegistrCommand implements Command {

    Object object;
    UserDAO userDAO;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    public RegistrCommand(Object object, UserDAO userDAO, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.object = object;
        this.userDAO = userDAO;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void execute() throws SQLException, IOException {
        userDAO.save((User)object);
        objectOutputStream.writeUTF("Registration success");
        objectOutputStream.flush();
    }
}
