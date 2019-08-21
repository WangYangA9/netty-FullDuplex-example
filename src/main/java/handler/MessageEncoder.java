package handler;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;

/**
 * Created by wangyang on 2019-08-21
 */
public class MessageEncoder extends MessageToByteEncoder<MessageVO> {
    private Kryo kryo = new Kryo();

    protected void encode(ChannelHandlerContext channelHandlerContext, MessageVO messageVO, ByteBuf byteBuf) throws Exception {
        byte[] body = convertToBytes(messageVO);  //将对象转换为byte
        int dataLength = body.length;  //读取消息的长度
        byteBuf.writeInt(dataLength);  //先将消息长度写入，也就是消息头
        byteBuf.writeBytes(body);  //消息体中包含我们要发送的数据
    }

    private byte[] convertToBytes(MessageVO message) {

        ByteArrayOutputStream bos = null;
        Output output = null;
        try {
            bos = new ByteArrayOutputStream();
            output = new Output(bos);
            kryo.writeObject(output, message);
            output.flush();

            return bos.toByteArray();
        } catch (KryoException e) {
            e.printStackTrace();
        }finally{
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(bos);
        }
        return null;
    }
}
