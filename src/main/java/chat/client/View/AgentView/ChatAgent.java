package chat.client.View.AgentView;


import chat.entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class ChatAgent {


    User user;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    Scanner in = new Scanner(System.in);
    public ChatAgent(User user, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.user = user;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public void startChat() throws IOException {
        System.out.println("Agent chat");
        String ask;
        String response;

        objectOutputStream.writeObject(user.getLogin()+" TCP");
        objectOutputStream.flush();
        objectOutputStream.writeUTF("setTypeConnection");
        objectOutputStream.flush();

        Thread threadRead = new Thread(() -> {
            while(true){
                try {
                  String ask1 = objectInputStream.readUTF();
                  System.out.println(ask1);
                } catch (IOException e) {
                 e.printStackTrace();
                }
            }
        });

        Thread threadWrite = new Thread(() -> {
            while (true){
                if(in.hasNext()) {
                    String respon = in.nextLine();
                    try {
                        objectOutputStream.writeObject(respon);
                        objectOutputStream.flush();
                        objectOutputStream.writeUTF("SendMessage " + user.getLogin());
                        objectOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        threadRead.start();
        threadWrite.start();

    }

}
