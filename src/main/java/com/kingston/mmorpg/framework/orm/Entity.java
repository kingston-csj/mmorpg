package com.kingston.mmorpg.framework.orm;

import java.io.Serializable;

public interface Entity<PK extends Serializable & Comparable<PK>> {

	PK getId();
}
