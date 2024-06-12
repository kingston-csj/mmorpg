package org.forfun.mmorpg.game.database.config.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;



@Getter
@Entity(name = "configcommonvalue")
public class ConfigCommonValue {

    @Id
    private String id;

    @Column
    private String value;
}
