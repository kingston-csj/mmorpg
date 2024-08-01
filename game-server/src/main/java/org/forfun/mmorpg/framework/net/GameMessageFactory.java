package org.forfun.mmorpg.framework.net;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.annotation.MessageRoute;
import jforgame.socket.share.message.Message;
import jforgame.socket.share.message.MessageFactory;
import jforgame.socket.support.DefaultMessageFactory;
import org.forfun.mmorpg.game.util.ClassScanner;

import java.util.Collection;
import java.util.Set;

public class GameMessageFactory implements MessageFactory {

    private static volatile DefaultMessageFactory self = new DefaultMessageFactory();

    public GameMessageFactory(String path) {
        Set<Class<?>> messages = ClassScanner.listClassesWithAnnotation(path, MessageRoute.class);
        for (Class<?> clazz : messages) {
            MessageRoute meta = clazz.getAnnotation(MessageRoute.class);
            if (meta == null) {
                throw new RuntimeException("messages[" + clazz.getSimpleName() + "] missed MessageMeta annotation");
            }
            short module = meta.module();
            if (Math.abs(module) > 326) {
                throw new RuntimeException("abs(module) must less than 326, target " + module);
            }

            // facade层所在包名的上一层
            int prevPacketNameIndex = clazz.getPackage().getName().lastIndexOf(".");
            String packetName = clazz.getPackage().getName().substring(0, prevPacketNameIndex);
            Set<Class<?>> msgClazzs = ClassScanner.listAllSubclasses(packetName, Message.class);
            msgClazzs.parallelStream().filter(msgClz -> msgClz.getAnnotation(MessageMeta.class) != null).forEach(msgClz -> {
                MessageMeta mapperAnnotation = msgClz.getAnnotation(MessageMeta.class);
                int cmdMeta = mapperAnnotation.cmd();
                if (Math.abs(cmdMeta) > 99) {
                    throw new RuntimeException("abs(cmd) must less than 100, target " + msgClz.getSimpleName());
                }
                short key = (short) (Math.abs(module) * 100 + cmdMeta);
                if (module < 0) {
                    key = (short) (-key);
                }
                self.registerMessage(key, msgClz);
            });
        }
    }

    @Override
    public void registerMessage(int cmd, Class<?> clazz) {
        self.registerMessage(cmd, clazz);
    }

    @Override
    public Class<?> getMessage(int cmd) {
        return self.getMessage(cmd);
    }

    @Override
    public int getMessageId(Class<?> clazz) {
        return self.getMessageId(clazz);
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return self.contains(clazz);
    }

    @Override
    public Collection<Class<?>> registeredClassTypes() {
        return self.registeredClassTypes();
    }
}