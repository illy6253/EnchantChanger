package ak.enchantchanger.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by A.K. on 14/06/02.
 */
public class MessageKeyPressed implements IMessage {

    public byte keyIndex;

    public MessageKeyPressed() {
    }

    public MessageKeyPressed(byte key) {
        this.keyIndex = key;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.keyIndex = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.keyIndex);
    }
}
