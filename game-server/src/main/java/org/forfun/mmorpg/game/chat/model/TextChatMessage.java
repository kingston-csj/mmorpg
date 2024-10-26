package org.forfun.mmorpg.game.chat.model;

import lombok.Data;

/**
 * 文本聊天消息
 *
 */
@Data
public class TextChatMessage extends BaseChatMessage {

    private String text;

}