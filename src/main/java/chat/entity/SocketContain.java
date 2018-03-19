package chat.entity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Владислав on 12.03.2018.
 */
public class SocketContain {

    private Socket socket;
    private ObjectOutputStream ous;
    private ObjectInputStream ois;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOus() {
        return ous;
    }

    public void setOus(ObjectOutputStream ous) {
        this.ous = ous;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
}
