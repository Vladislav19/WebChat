package chat.serverTCP;




import chat.entity.SocketContain;
import chat.serverTCP.Controllers.MainController;
import chat.serverTCP.Logic.MainLogic;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server extends Thread
{
    private  Socket socket;
    private ObjectOutputStream dos;
    private ObjectInputStream dis;
    int db;
    public Server(int db) {this.db=db;}

    public void setSocket(Socket socket,ObjectOutputStream objectOutputStream,ObjectInputStream objectInputStream)
    {
        dis=objectInputStream;
        dos=objectOutputStream;
        this.socket = socket;
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void run()
    {
        try {

            Object object;
            MainController mainController = new MainController(db,dos,dis);

            while(true) {
                object = dis.readObject();
                String line= dis.readUTF();
                mainController.useCommand(object,line);
            }
        }
        catch (EOFException ex){

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}