package org.forfun.mmorpg.csv.reader;

import java.io.InputStream;
import java.util.List;

public interface DataReader {

    <E> List<E> read(InputStream is, Class<E> clazz);
}
