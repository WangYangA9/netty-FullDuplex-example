package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;

import java.net.InetAddress;

/**
 * Created by wangyang on 2019-08-21
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final MessageVO message1;

    public NettyServerHandler(MessageVO message1) {
        this.message1 = message1;
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
        MessageVO messageVO = (MessageVO)msg;
        if (messageVO.getUserName().equals("李1")) {
//            System.out.println("收到"+ (messageVO).getMessage());
            ctx.writeAndFlush(new MessageVO("李1", "嗨！没事溜溜弯"));
        } else if (messageVO.getUserName().equals("李2")) {
//            System.out.println("收到"+ messageVO.getMessage());
            ctx.writeAndFlush(new MessageVO("李2", "回头给老太太请安"));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
//        System.out.println("连接的客户端ID:" + ctx.channel().id());
//        ctx.writeAndFlush("server"+ InetAddress.getLocalHost().getHostName() + "success connected！ \n");
//        System.out.println("connection");
//        long start = System.currentTimeMillis();
//        System.out.println("start:" + start);

        super.channelActive(ctx);
        ctx.writeAndFlush(message1);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
