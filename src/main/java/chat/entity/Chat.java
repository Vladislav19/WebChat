package chat.entity;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Владислав on 11.03.2018.
 */
public class Chat {

    private long id;
    private String loginClient;
    private String loginAgent;
    private String typeClient;
    private String typeAgent;
    private LinkedList<String> messageHistoryAgent;
    private LinkedList<String> messageHistoryClient;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginClient() {
        return loginClient;
    }

    public void setLoginClient(String loginClient) {
        this.loginClient = loginClient;
    }

    public String getLoginAgent() {
        return loginAgent;
    }

    public void setLoginAgent(String loginAgent) {
        this.loginAgent = loginAgent;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public String getTypeAgent() {
        return typeAgent;
    }

    public void setTypeAgent(String typeAgent) {
        this.typeAgent = typeAgent;
    }

    public LinkedList<String> getMessageHistoryAgent() {
        return messageHistoryAgent;
    }

    public void setMessageHistoryAgent(LinkedList<String> messageHistoryAgent) {
        this.messageHistoryAgent = messageHistoryAgent;
    }

    public LinkedList<String> getMessageHistoryClient() {
        return messageHistoryClient;
    }

    public void setMessageHistoryClient(LinkedList<String> messageHistoryClient) {
        this.messageHistoryClient = messageHistoryClient;
    }
}
