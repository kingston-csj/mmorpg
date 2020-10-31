package com.kingston.mmorpg.game.database.config.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class ConfigCommonValue {

    @Id
    private String id;

    @Column
    private String value;
}
