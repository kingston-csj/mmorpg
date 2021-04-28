package org.forfun.mmorpg.data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DataManager {

    private final ConcurrentMap<String, Container> data = new ConcurrentHashMap<>();


}
