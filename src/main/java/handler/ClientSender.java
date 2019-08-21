package handler;

import io.netty.channel.Channel;

/**
 * Created by wangyang on 2019-08-21
 */
public class ClientSender implements Runnable {
    private Channel ch;
    private String msg;
    public ClientSender(Channel channel, String message) {
        this.ch = channel;
        this.msg = message;
    }
    public void run() {
        ch.writeAndFlush(msg);
    }
}
