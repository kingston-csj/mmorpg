package org.forfun.mmorpg.game.util.dirtywords;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirtyWordsReader {

    private String ENCODING = "UTF-8";
    @Getter
    private final Map<Character, List<String>> dirtyWords = new HashMap<>();
    @Getter
    private int wordCount = 0;

    DirtyWordsReader() throws IOException {
        Set<String> words = readSensitiveWordPool();
        this.initWordsStore(words);
    }

    private Set<String> readSensitiveWordPool() throws IOException {
        Set<String> words = new HashSet<>();
        Resource resource = new FileSystemResource("config/SensitiveWord.txt");
        File file = resource.getFile();
        try (InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING)) {
            if (file.isFile() && file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(read);
                String word;
                while ((word = bufferedReader.readLine()) != null) {
                    if (StringUtils.isEmpty(word)) {
                        continue;
                    }
                    wordCount++;
                    words.add(word.trim());
                }
            } else {
                throw new FileNotFoundException("dirty words file is empty");
            }
        } catch (Exception e) {
            throw e;
        }
        return words;
    }

    private void initWordsStore(Set<String> words) {
        for (String word : words) {
            if (StringUtils.isEmpty(word)) {
                continue;
            }
            char first = word.charAt(0);
            List<String> sameFirst = dirtyWords.computeIfAbsent(first, k -> new ArrayList<>());
            sameFirst.add(word);
        }
    }

}
