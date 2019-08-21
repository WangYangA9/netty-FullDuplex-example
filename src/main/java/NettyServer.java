import handler.MessageDecoder;
import handler.MessageEncoder;
import handler.MessageVO;
import handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by wangyang on 2019-08-21
 */
public class NettyServer {
    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        // 创建一组线性
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            // 初始化 Server
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress("localhost", 9999));

            // 设置收到数据后的处理的 Handler
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(new MessageEncoder(), new MessageDecoder(),
                            new NettyServerHandler(new MessageVO("张1", "吃了没，您那？")));
                }
            });
            // 绑定端口，开始提供服务
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
