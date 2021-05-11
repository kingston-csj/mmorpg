package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.service.CrossService;
import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.net.socket.annotation.MessageMeta;

@Getter
@Setter
@MessageMeta(cmd = CrossService.GATE_TO_SERVER_TRANSFER)
public class AccountTransferMessage implements Message {

    private long account;
    /**
     * 真正的消息
     */
    private Message message;
}
