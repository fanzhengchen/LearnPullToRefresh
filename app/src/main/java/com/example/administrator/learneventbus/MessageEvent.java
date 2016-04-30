package com.example.administrator.learneventbus;

/**
 * Created by Administrator on 2016/4/16.
 */
public class MessageEvent {
    private String ip;
    private int port;

    public MessageEvent(String ip,int port){
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return String.format("%s %d", ip, port);
    }
}
