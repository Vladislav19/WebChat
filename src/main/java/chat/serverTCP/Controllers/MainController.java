package chat.serverTCP.Controllers;



import chat.DB.implementations.UserDAOImpls;
import chat.DB.implementations.UserDAOImplsH2;
import chat.DB.interfaces.UserDAO;
import chat.serverTCP.Controllers.Command.Impl.*;
import chat.serverTCP.Controllers.Command.Interface.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

/**
 * Created by Владислав on 14.02.2018.
 */
public class MainController {

    UserDAO userDAO = null;
    Command command;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    int db;

    public MainController(int db,ObjectOutputStream objectOutputStream,ObjectInputStream objectInputStream) {
        this.db=db;
        this.objectInputStream=objectInputStream;
        this.objectOutputStream=objectOutputStream;
    }

    public void useCommand(Object object,String string) throws IOException, SQLException {
        int i=0;
        if(i==0) {
            if (db == 1) {
                userDAO = new UserDAOImplsH2();
            } else {
                userDAO = new UserDAOImpls();
            }
        }i++;

        if(string.equals("Registration")){
            command = new RegistrCommand(object,userDAO,objectInputStream,objectOutputStream);
            command.execute();
        }

        else if(string.equals("Authorization")){
            command = new AutorisationCommand(object,userDAO,objectInputStream,objectOutputStream);
            command.execute();
        }

        else if(string.equals("GetFreeAgentPort")){
            command = new GetFreeAgentPortCommand(object,userDAO,objectInputStream,objectOutputStream);
            command.execute();
        }

        else if(string.equals("AgentIsNotActive")){
            command = new MarkNotFreeCommand(object,userDAO,objectInputStream,objectOutputStream);
            command.execute();
        }

        else if(string.equals("MarkFree")){
            command = new MarkFreeCommand(object,userDAO,objectInputStream,objectOutputStream);
            command.execute();
        }

        else if(string.contains("SendMessage")){
            command = new SendMessageCommand(object,userDAO,string,objectInputStream,objectOutputStream );
            command.execute();
        }

        else if(string.contains("setTypeConnection")){
            command = new SetTypeConnection(object,userDAO,objectInputStream,objectOutputStream );
            command.execute();
        }
    }
}
