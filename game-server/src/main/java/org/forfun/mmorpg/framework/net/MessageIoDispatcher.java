package org.forfun.mmorpg.framework.net;


import jforgame.socket.share.ChainedMessageDispatcher;
import jforgame.socket.share.CommonMessageHandlerRegister;
import jforgame.socket.share.DispatchThreadModel;
import jforgame.socket.share.IdSession;
import jforgame.socket.share.MessageHandler;
import jforgame.socket.share.MessageHandlerRegister;
import jforgame.socket.share.MessageParameterConverter;
import jforgame.socket.share.ThreadModel;
import jforgame.socket.share.annotation.MessageRoute;
import jforgame.socket.share.message.MessageExecutor;
import jforgame.socket.share.message.MessageFactory;
import jforgame.socket.share.task.MessageTask;
import jforgame.socket.support.DefaultMessageParameterConverter;
import org.forfun.mmorpg.game.ServerType;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.battle.model.BattleMessage;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;

import java.util.Map;

public class MessageIoDispatcher extends ChainedMessageDispatcher {

    private MessageHandlerRegister handlerRegister;

    private MessageParameterConverter msgParameterConverter = new DefaultMessageParameterConverter(GameContext.getMessageFactory());

    private ThreadModel threadModel = new DispatchThreadModel();

    public MessageIoDispatcher() {
        MessageFactory messageFactory = GameContext.getMessageFactory();
        Map<String, Object> messageRoutes = GameContext.getBeansWithAnnotation(MessageRoute.class);

        this.handlerRegister = new CommonMessageHandlerRegister(messageRoutes.values(), messageFactory);

        MessageHandler messageHandler = (session, message) -> {
            int cmd = GameContext.getMessageFactory().getMessageId(message.getClass());
            MessageExecutor cmdExecutor = handlerRegister.getMessageExecutor(cmd);
            if (cmdExecutor == null) {
                logger.error("message executor missed,  cmd={}", cmd);
                return true;
            }
            if (message instanceof BattleMessage) {
                if (GameContext.serverType == ServerType.GAME) {
                    PlayerEnt player = (PlayerEnt) session.getAttribute("PLAYER");
                    BattleMessage battleMessage = (BattleMessage) message;
                    int battleSid = battleMessage.getBattleServerId(player);
                    if (battleSid > 0) {
                        IdSession crossSession = GameContext.getRpcClientRouter().getSession(battleSid);
                        if (crossSession == null) {
                            logger.error("fight server is unreachable", cmd);
                            return false;
                        }
                        crossSession.send(battleMessage);
                        return false;
                    }
                }
            }

            Object[] params = msgParameterConverter.convertToMethodParams(session, cmdExecutor.getParams(), message);
            Object controller = cmdExecutor.getHandler();

            Object playerEnt = session.getAttribute("PLAYER");
            long dispatchKey = 0;
            if (playerEnt != null) {
                dispatchKey = ((PlayerEnt) playerEnt).dispatchKey();
            }
            MessageTask task = MessageTask.valueOf(session, dispatchKey, controller, cmdExecutor.getMethod(), params);
            task.setRequest(message);
            // 丢到任务消息队列，不在io线程进行业务处理
            threadModel.accept(task);
            return true;
        };

        addMessageHandler(messageHandler);
    }


    @Override
    public void onSessionCreated(IdSession session) {
    }

    @Override
    public void onSessionClosed(IdSession session) {
    }

}

