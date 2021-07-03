package org.forfun.mmorpg.test.codec;


import org.forfun.mmorpg.protocol.Message;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

@MessageMeta(cmd = 1)
public class BaseVo implements Message {

    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
