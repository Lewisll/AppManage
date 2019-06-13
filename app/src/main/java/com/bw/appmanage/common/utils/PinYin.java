package com.bw.appmanage.common.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/6/11
 * <br/>desc  :
 * </pre>
 */
public class PinYin {

    /**
     * 讲汉字转换为拼音
     *
     * @param src
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getPinYin(String src) {
        char[] charArr = null;
        charArr = src.toCharArray();
        String[] strArr = new String[charArr.length];
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat hpOpf = new HanyuPinyinOutputFormat();
        hpOpf.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hpOpf.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        hpOpf.setVCharType(HanyuPinyinVCharType.WITH_V);
        String str = "";
        int t0 = charArr.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(charArr[i])
                        .matches("[\\u4E00-\\u9FA5]+")) {
                    strArr = PinyinHelper.toHanyuPinyinStringArray(charArr[i],
                            hpOpf);// 将汉字的几种全拼都存到t2数组中
                    str += strArr[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                    str += Character.toString(charArr[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            Log.e("getPinYin error.", e.toString());
        }
        String s = str.toLowerCase();
        return s;
    }

    /**
     * 将汉字转换为拼音--首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 讲汉字转换为字节序列
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        // 将字符串转换成字节序列
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

}
