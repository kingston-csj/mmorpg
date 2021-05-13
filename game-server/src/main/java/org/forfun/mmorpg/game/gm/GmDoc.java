package org.forfun.mmorpg.game.gm;

public class GmDoc {

    public static String printDoc() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------gm命令说明-----------\n");
        for (GmCommands cmd : GmCommands.values()) {
            sb.append(cmd.getDesc() + "\t-->\t(" + cmd.getExample()).append(")\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String doc = printDoc();
        System.out.println(doc);
    }
}
