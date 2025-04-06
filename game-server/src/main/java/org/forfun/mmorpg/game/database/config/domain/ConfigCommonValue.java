package org.forfun.mmorpg.game.database.config.domain;

import jforgame.data.annotation.DataTable;
import jforgame.data.annotation.Id;
import lombok.Getter;



@Getter
@DataTable(name = "configcommonvalue")
public class ConfigCommonValue {

    @Id
    private int id;

    private String key;

    private String value;

    private String desc;
}
