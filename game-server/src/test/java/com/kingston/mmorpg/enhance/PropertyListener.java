package com.kingston.mmorpg.enhance;

import java.beans.PropertyChangeSupport;

public class PropertyListener {

    public static void main(String[] args) {
        Player player = new Player();
        PropertyChangeSupport support = new PropertyChangeSupport(player);
        // 为player添加属性变更监听器
        support.addPropertyChangeListener(listener ->
                System.out.println(String.format("对象 [%s]属性发生变更，从%s变为%s",
                        listener.getPropertyName(), listener.getOldValue(), listener.getNewValue()))
        );

        player.setName("Tom");
        // 手动抛出事件
        support.firePropertyChange("name", null, player.getName());
        // 程序输出
        // 对象 [name]属性发生变更，从null变为Tom
    }
}
