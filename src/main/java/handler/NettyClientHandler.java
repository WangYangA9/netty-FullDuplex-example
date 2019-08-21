package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.net.InetAddress;

/**
 * Created by wangyang on 2019-08-21
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private final MessageVO message1;
    private final MessageVO message2;

    private volatile boolean receive1 = false;
    private volatile boolean receive2 = false;
    private volatile boolean receive3 = false;
    private volatile boolean send1 = false;
    private volatile boolean send2 = false;


    public NettyClientHandler(MessageVO message1, MessageVO message2) {
        this.message1 = message1;
        this.message2 = message2;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf in = (ByteBuf) msg;
//        try {
//            while (in.isReadable()) { // (1)
//                System.out.print((char) in.readByte());
//                System.out.flush();
//            }
//        } finally {
//            ReferenceCountUtil.release(msg); // (2)
//        }
        MessageVO message = (MessageVO) msg;
        if (message.getUserName().equals("张1")) {
//            System.out.println("收到"+ ((MessageVO) msg).getMessage());
            ctx.writeAndFlush(new MessageVO("张1", "刚吃")).sync();
            receive1 = true;
        } else if(message.getUserName().equals("李1")){
            receive2 = true;
        } else if(message.getUserName().equals("李2")){
            receive3 = true;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        if(receive1 && receive2 && receive3) {
//            System.out.println("client 结束：" + System.currentTimeMillis());
            ctx.close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
//        System.out.println("连接的客户端ID:" + ctx.channel().id());
//        ctx.writeAndFlush("client"+ InetAddress.getLocalHost().getHostName() + "success connected！ \n");
//        System.out.println("connection");
//        long start = System.currentTimeMillis();
//        System.out.println("start:" + start);

        super.channelActive(ctx);
        ctx.writeAndFlush(message1);
        ctx.writeAndFlush(message2);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
