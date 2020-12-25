//package com.kingston.mmorpg.framework.net.socket;
//
////import sun.net.util.IPAddressUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class IpUtil {
//
//    /**
//     * 是否内部ip
//     * @param ip
//     * @return
//     */
//    public static boolean isInternalIp(String ip) {
//        var list = new ArrayList<>();
//
//        list.add("first");
//        list.add("second");
//        list.add("third");
//
//        var result = List.copyOf(list);
//        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
//        return internalIp(addr);
//    }
//
//
//    private static boolean internalIp(byte[] addr) {
//        final byte part0 = addr[0];
//        final byte part1 = addr[1];
//        //10.x.x.x/8
//        final byte SECTION_1 = 0x0A;
//        //172.16.x.x/12
//        final byte SECTION_2 = (byte) 0xAC;
//        final byte SECTION_3 = (byte) 0x10;
//        final byte SECTION_4 = (byte) 0x1F;
//        //192.168.x.x/16
//        final byte SECTION_5 = (byte) 0xC0;
//        final byte SECTION_6 = (byte) 0xA8;
//        switch (part0) {
//            case SECTION_1:
//                return true;
//            case SECTION_2:
//                if (part1 >= SECTION_3 && part1 <= SECTION_4) {
//                    return true;
//                }
//            case SECTION_5:
//                switch (part1) {
//                    case SECTION_6:
//                        return true;
//                }
//            default:
//                return false;
//        }
//    }
//
//}
