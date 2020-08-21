package com.kingston.mmorpg.test.codec;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.kingston.mmorpg.game.player.message.ReqAccontLogin;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JProtobuffTest {

    @Test
    public void test() throws Exception {
        a();
        a();
    }

    private void a(){
        ReqAccontLogin request = new ReqAccontLogin();
        request.setAccountId(123456L);
        request.setPassword("kingston");
        Codec<ReqAccontLogin> simpleTypeCodec = ProtobufProxy
                .create(ReqAccontLogin.class);
        try {
            // 序列化
            byte[] bb = simpleTypeCodec.encode(request);
            // 反序列化
            ReqAccontLogin request2 = simpleTypeCodec.decode(bb);
            Assert.assertTrue(request2.getAccountId() == request.getAccountId());
            Assert.assertTrue(request2.getPassword().equals(request.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
