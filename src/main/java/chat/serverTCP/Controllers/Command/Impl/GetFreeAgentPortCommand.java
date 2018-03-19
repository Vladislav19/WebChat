package chat.serverTCP.Controllers.Command.Impl;

import chat.DB.interfaces.UserDAO;
import chat.entity.User;
import chat.serverTCP.Controllers.Command.Interface.Command;
import chat.serverTCP.Logic.MainLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Created by Владислав on 24.02.2018.
 */
public class GetFreeAgentPortCommand implements Command {
    Object object;
    UserDAO userDAO;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    public GetFreeAgentPortCommand(Object object, UserDAO userDAO, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.object = object;
        this.userDAO = userDAO;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void execute() throws IOException, SQLException {
        MainLogic logic = new MainLogic();
        String result = logic.searchFreeAgent((User)object);
        if(result.equals("success")){
            objectOutputStream.writeUTF("connection is established");
            objectOutputStream.flush();
        }
        else {
            objectOutputStream.writeUTF("not found free agent");
            objectOutputStream.flush();
        }

    }
}
