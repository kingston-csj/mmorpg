package org.forfun.mmorpg.game.util.dirtywords;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
class DirtyWordUnit {

    private final String source;
    private final String keyWord;
    private final List<Integer> indexList = new ArrayList<>();

    public DirtyWordUnit(String source,String word) {
        this.source = source;
        this.keyWord = word;
    }

    public void checkWordIndex() {
        int index = 0;
        if (source.length() < keyWord.length()) {
            return ;
        }
        for (int i = 0,n=source.length(); i < n; i++) {
            if (keyWord.length() > index && source.charAt(i) == keyWord.charAt(index)) {
                indexList.add(index);
                index++;
                if (foundDirtyWord()) {
                    break;
                }
            }
        }
    }

    public boolean foundDirtyWord() {
        return this.indexList.size() == keyWord.length();
    }



}
