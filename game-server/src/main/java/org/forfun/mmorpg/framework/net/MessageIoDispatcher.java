package org.forfun.mmorpg.framework.net;


import jforgame.socket.share.ChainedMessageDispatcher;
import jforgame.socket.share.IdSession;
import jforgame.socket.share.MessageHandler;
import jforgame.socket.share.MessageHandlerRegister;
import jforgame.socket.share.MessageParameterConverter;
import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.annotation.MessageRoute;
import jforgame.socket.share.message.MessageExecutor;
import jforgame.socket.share.message.MessageFactory;
import jforgame.socket.share.task.MessageTask;
import jforgame.socket.support.DefaultMessageHandlerRegister;
import jforgame.socket.support.DefaultMessageParameterConverter;
import jforgame.socket.support.MessageExecuteUnit;
import org.forfun.mmorpg.game.ServerType;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.battle.model.BattleMessage;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;

import java.lang.reflect.Method;
import java.util.Map;

public class MessageIoDispatcher extends ChainedMessageDispatcher {

    private MessageHandlerRegister handlerRegister;

    private MessageParameterConverter msgParameterConverter = new DefaultMessageParameterConverter(GameContext.getMessageFactory());

    public MessageIoDispatcher() {
        initialize();

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
            GameExecutor.getInstance().acceptTask(task);
            return true;
        };

        addMessageHandler(messageHandler);
    }

    private void initialize() {
        Map<String, Object> messageRoutes = GameContext.getBeansWithAnnotation(MessageRoute.class);
        this.handlerRegister = new DefaultMessageHandlerRegister();
        MessageFactory messageFactory = GameContext.getMessageFactory();
        messageRoutes.values().forEach(controller->{
            try {
                Method[] methods = controller.getClass().getDeclaredMethods();
                for (Method method : methods) {
//                    RequestHandler mapperAnnotation = method.getAnnotation(RequestHandler.class);
                        Class<?> paramClazz = getMessageMeta(method);
                        if (paramClazz == null) {
//                            throw new RuntimeException(
//                                    String.format("controller[%s] method[%s] lack of RequestMapping annotation",
//                                            controller.getClass().getName(), method.getName()));
                            continue;
                        }
                        int key = messageFactory.getMessageId(paramClazz);
                        MessageExecutor cmdExecutor = handlerRegister.getMessageExecutor(key);
                        if (cmdExecutor != null) {
                            throw new RuntimeException(String.format("cmd[%d] duplicated", key));
                        }

                        cmdExecutor = MessageExecuteUnit.valueOf(method, method.getParameterTypes(), controller);
                        handlerRegister.register(key, cmdExecutor);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        });
    }

    private Class<?> getMessageMeta(Method method) {
        for (Class<?> paramClazz : method.getParameterTypes()) {
            if (paramClazz.isAnnotationPresent(MessageMeta.class)) {
                return paramClazz;
            }
        }
        return null;
    }

    private int buildKey(int module, int cmd) {
        int result = Math.abs(module) * 1000 + Math.abs(cmd);
        return cmd < 0 ? -result : result;
    }

    @Override
    public void onSessionCreated(IdSession session) {
    }

    @Override
    public void onSessionClosed(IdSession session) {
//        long playerId = SessionManager.INSTANCE.getPlayerIdBy(session);
//        if (playerId > 0) {
//            logger.info("角色[{}]close session", playerId);
//        }
    }

}

