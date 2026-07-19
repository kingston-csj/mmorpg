package jforgame.behaviortree.nodes.actions;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTStatus;
import jforgame.behaviortree.nodes.ActionNode;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志动作节点
 * 用于输出调试日志
 * 
 * 
 */
@Slf4j
public class LogAction extends ActionNode {
    
    /** 日志消息 */
    private final String message;
    
    /** 日志级别 */
    public enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }
    
    private final LogLevel logLevel;
    
    public LogAction(String name, String message) {
        this(name, message, LogLevel.INFO);
    }
    
    public LogAction(String name, String message, LogLevel logLevel) {
        super(name);
        this.message = message;
        this.logLevel = logLevel;
    }
    
    @Override
    protected BTStatus performAction(AIContext context) {
        String formattedMessage = String.format("[Entity:%d] %s", context.getEntityId(), message);
        
        switch (logLevel) {
            case DEBUG:
                log.debug(formattedMessage);
                break;
            case INFO:
                log.info(formattedMessage);
                break;
            case WARN:
                log.warn(formattedMessage);
                break;
            case ERROR:
                log.error(formattedMessage);
                break;
        }
        
        return BTStatus.SUCCESS;
    }
}
