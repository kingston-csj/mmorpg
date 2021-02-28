package com.kingston.mmorpg.game.gm;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.RootDoc;

public class GmDoc {

    private static RootDoc rootDoc;

    class Doclet {


    }

    public static String printDoc() {
        ClassDoc[] docs = rootDoc.classes();
        StringBuilder sb = new StringBuilder();

        for (ClassDoc doc : docs) {
            FieldDoc[] fieldDocs = doc.fields();
            for (FieldDoc fieldDoc : fieldDocs) {
                sb.append(fieldDoc.commentText());
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String sourceFile = "/projects/mmorpg/mmorpg/game-server/src/main/java/com/kingston/mmorpg/game/gm/GmCommands.java";
        com.sun.tools.javadoc.Main.main(
                "-doclet",Doclet.class.getName(),
                "-encoding","utf-8",
                "-classpath","",
                sourceFile,
                "/doc.txt"
        );

        String doc = printDoc();
        System.out.println(doc);
    }
}
