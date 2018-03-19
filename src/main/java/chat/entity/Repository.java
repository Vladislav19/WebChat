package chat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 19.03.2018.
 */
public class Repository {
    public List<SocketContain> sockets = new ArrayList<>();
    public List<Chat> chats = new ArrayList<>();

    private static Repository ourInstance = new Repository();

    public synchronized static Repository getInstance() {
        return ourInstance;
    }

    private Repository() {
    }
}
