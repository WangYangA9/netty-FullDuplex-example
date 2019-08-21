import handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by wangyang on 2019-08-21
 */
public final class NettyClient {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "localhost");
    static final int PORT = Integer.parseInt(System.getProperty("port", "9999"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
          Bootstrap b = new Bootstrap();
          b.group(group)
           .channel(NioSocketChannel.class)
           .handler(new ChannelInitializer<SocketChannel>() {
               @Override
               protected void initChannel(SocketChannel ch) {
                   ChannelPipeline p = ch.pipeline();
                   p.addLast(new MessageEncoder(), new MessageDecoder(),
                           new NettyClientHandler(
                                   new MessageVO("李1", "您这，嘛去？"),
                                   new MessageVO("李2", "有空家里坐坐啊")
                           ));
               }
           });

          // Make the connection attempt.
            for (int i = 0; i < 10000; i++) {
                ChannelFuture f = b.connect(HOST, PORT).sync();


                // Wait until the connection is closed.
                f.channel().closeFuture().sync();
            }
        } finally {

            System.out.println("end:" + (System.currentTimeMillis() - start));
            group.shutdownGracefully();
        }
    }
}
