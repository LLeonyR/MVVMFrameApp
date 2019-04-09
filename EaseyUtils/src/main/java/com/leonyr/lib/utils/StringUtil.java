package com.leonyr.lib.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * ==============================================================
 *
 * Description: 字符串工具类
 * <p>
 * Created by 01385127 on 2019.04.09
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 空字符
     *
     * @param text
     * @return
     */
    public static boolean isBlank(String text) {
        return !isNotBlank(text);
    }

    /**
     * 非空字符
     *
     * @param text
     * @return
     */
    public static boolean isNotBlank(String text) {
        return text != null && !"".equalsIgnoreCase(text.trim()) && !"null".equalsIgnoreCase(text.trim());
    }

    public static String convertByteArrayToHexString(byte[] b, int size) {
        String ret = " ";
        for (int i = 0; i < size; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 过滤英文字符
     *
     * @param str
     * @return
     */
    public static String filterLetter(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.replaceAll("[a-zA-Z]", "");
    }

    /**
     * 过滤第一个英文字符之后所有字符
     *
     * @param str
     * @return
     */
    public static String filterBeforeFirstLetter(String str) {
        if (isNotBlank(str) && !isDigital(str)) {
            int index = 0;
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] >= 'a' && chars[i] <= 'z' || chars[i] >= 'A' && chars[i] <= 'Z') {
                    index = i;
                    break;
                }
            }
            return str.substring(0, index);
        }
        return str;
    }

    /**
     * 过滤数字
     *
     * @param str
     * @return
     */
    public static String filterDigital(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.replaceAll("[1-9]", "");
    }

    /**
     * 是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isDigital(String str) {
        if (isBlank(str)) {
            return false;
        }
        return TextUtils.isDigitsOnly(str);
    }

    /**
     * 是否为纯英文字母
     *
     * @param str
     * @return
     */
    public static boolean isLetters(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        return pattern.matcher(str).matches();
    }

    public static String replaceWaybill(String waybill) {
        if (isNotBlank(waybill) && waybill.contains("\'")) {
            waybill = waybill.replace("\'", "\"");
        }
        return waybill;
    }

    public static long dateToStamp(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 补全Json的逗号
     *
     * @param qrCode 这种格式
     *               {"K1":"595XA","K2":"K2Value""K3":K3Value,"K4":k4Value"K5":608007236401}"
     *               1.肯定有冒号,一个冒号对应一对
     *               2.key肯定有双引号,value不一定有双引号
     * @return
     */
    public static String getCommaJSon(String qrCode) {
        String[] colonArray = qrCode.substring(1, qrCode.length() - 1).split(":");
        StringBuilder resultBuffer = new StringBuilder();
        resultBuffer.append("{");
        for (int i = 0; i < colonArray.length; i++) {
            // 1.第一个不处理
            if (i == 0) {
                resultBuffer.append(colonArray[i]).append(":");
            } else if (i > 0 && i < colonArray.length - 1) {
                resultBuffer.append(getCommaString(colonArray[i])).append(":");
            } else if (i == colonArray.length - 1) {
                //最后一个不处理
                resultBuffer.append(colonArray[i]).append("}");
            }
        }
        return resultBuffer.toString();
    }

    /**
     * 补全逗号
     */
    private static String getCommaString(String src) {
        // "value","key" 4个双引号，有逗号,正常
        // value,"key"   2个双引号，有逗号,正常
        //"","key"       4个双引号,value值为空

        //-------------------3种异常情况-------------------
        // "value""key"  4个双引号，没有逗号,异常-----"value","key"
        // value"key"    2个双引号，没有逗号,异常------"value","key"
        //"""key"        4个双引号,value为空----------"","key"

        //有逗号，补全双引号
        if (src.contains(",")) {
            String[] arr = src.split(",");
            if (arr[0].contains("\"")) {
                return src;
            }
            return "\"" + arr[0] + "\"" + "," + arr[1];
        }

        String[] quotationArray = src.split("\"");
        String left = "";
        String right = "";
        int count = 0;
        for (int i = 0; i < quotationArray.length; i++) {
            if (!quotationArray[i].isEmpty()) {
                if (count == 0) {
                    //不管之前有没有双引号,都自动加上
                    left = "\"" + quotationArray[i] + "\"";
                } else {
                    right = "\"" + quotationArray[i] + "\"";
                }
                count++;
            }
        }
        //value为空
        String result;
        if (count == 1) {
            result = "\"\"" + "," + left;
        } else {
            result = left + "," + right;
        }
        return result;
    }

    public static byte[] string2Bytes(String content, String charsetName) {
        if (TextUtils.isEmpty(content)) {
            return new byte[]{};
        } else {
            try {
                return content.getBytes(charsetName);
            } catch (Exception var3) {
                Log.e("StringUtil", "string2Bytes" + var3);
                return new byte[]{};
            }
        }
    }

    public static Map<String, String> string2Map(String mapString) {
        Map map = new HashMap();
        StringTokenizer entrys = new StringTokenizer(mapString, "^");

        while (entrys.hasMoreTokens()) {
            StringTokenizer items = new StringTokenizer(entrys.nextToken(), "'");
            map.put(items.nextToken(), items.hasMoreTokens() ? items.nextToken() : null);
        }
        return map;
    }

    /**
     * 判断是否是RFID标签
     */
    public static boolean isRfidTag(String content) {
        try {
            if (TextUtils.isEmpty(content) || content.length() != 32) {
                return false;
            }
            byte[] resource = hexStringToByte(content);
            if (resource.length == 16) {
                byte lastByte = resource[15];
                byte[] tempData = new byte[15];
                System.arraycopy(resource, 0, tempData, 0, 15);
                byte checkResult = getXOR(tempData);
                return lastByte == checkResult;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Log.e("StringUtil", "isRfidTag", ex);
            return false;
        }
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] aChar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(aChar[pos]) << 4 | toByte(aChar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static byte getXOR(byte[] bytes) {
        byte xor = 0;
        for (int i = 0; i < bytes.length; i++) {
            xor ^= bytes[i];
        }
        return xor;
    }

    /**
     * byte数组转int
     *
     * @param bytes
     * @return
     */
    public static int byteArrayToInt(byte[] bytes) {
        if (bytes == null) {
            return 0;
        }
        if (bytes.length > 4) {
            return 0;
        }
        byte[] temp = new byte[4];
        System.arraycopy(bytes, 0, temp, 4 - bytes.length, bytes.length);
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (temp[i] & 0x000000FF) << shift;
        }
        return value;
    }
}
