package org.forfun.mmorpg.mq;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MqG2FHello implements MqMessage {

    private int targetSid;

    @Override
    public int receiver() {
        return targetSid;
    }

}
