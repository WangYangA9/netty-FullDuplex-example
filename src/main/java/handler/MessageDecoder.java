package handler;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by wangyang on 2019-08-21
 */
public class MessageDecoder extends ByteToMessageDecoder {
    public static final int HEAD_LENGTH = 4;

    private Kryo kryo = new Kryo();

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < HEAD_LENGTH) {
            return;
        }
        byteBuf.markReaderIndex();                  //我们标记一下当前的readIndex的位置
        int dataLength = byteBuf.readInt();       // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
        if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
            ctx.close();
        }

        if (byteBuf.readableBytes() < dataLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] body = new byte[dataLength];  //传输正常
        byteBuf.readBytes(body);
        Object o = convertToObject(body);  //将byte数据转化为我们需要的对象
        list.add(o);
    }

    private Object convertToObject(byte[] body) {

        Input input = null;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(body);
            input = new Input(bais);

            return kryo.readObject(input, MessageVO.class);
        } catch (KryoException e) {
            e.printStackTrace();
        }finally{
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(bais);
        }

        return null;
    }
}
