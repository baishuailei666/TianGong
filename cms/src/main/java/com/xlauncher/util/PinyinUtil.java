package com.xlauncher.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * author:zhangxiaolong
 */
public class PinyinUtil {

    /**
     * 将汉字转换成不带声调的拼音形式
     * @param name
     * @return
     */
    public static String chineseToPinyin(String name) {
        char[] charArray = name.toCharArray();
        StringBuilder pinyin = new StringBuilder();
        for(int i = 0; i < charArray.length; i++) {
            if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]")) {
                pinyin.append(PinyinHelper.toHanyuPinyinStringArray(charArray[i])[0]);
            } else {
                pinyin.append(charArray[i]);
            }
        }
        return pinyin.toString();
    }

    public static String convert(String text, HanyuPinyinCaseType caseType, boolean isCapitalized) {
        if (text.length() == 0) {
            return "";
        }
        /**
         * 确定汉字转换为拼音的格式
         */
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        if (caseType != null) {
            format.setCaseType(caseType);
            isCapitalized = false;
        }
        /**
         * 是否有声调
         * WITHOUT_TONE没有声调
         * WITH_TONE_MARK会报异常
         * WITH_TONE_NUMBER会在拼音后面加数字表示是几声
         */
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = text.trim().toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : input) {
            /**
             * 判断要处理的字符是否是汉字，常用汉字范围在u4E00-u9FA5之间
             */
            if (Character.toString(c).matches("[\\u4E00-\\u9FA5]")) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (isCapitalized) {
                        /**
                         * 因为工具支持识别多音字，多音字的所有拼音以String数组形式返回，一般取第一个元素即可，即temp[0]
                         */
                        builder.append(capitalize(temp[0]));
                    } else {
                        builder.append(temp[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                if (isCapitalized) {
                    builder.append(capitalize(Character.toString(c)));
                } else {
                    builder.append(Character.toString(c));
                }
            }
        }
        return builder.toString();
    }

    /**
     * 将指定的小写字符串转换为大写
     * @param lowerStr
     * @return
     */
    public static String capitalize(String lowerStr) {
        char[] charArray = lowerStr.toCharArray();
        char[] capitalizedArray = new char[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            capitalizedArray[i] = Character.toUpperCase(charArray[i]);
        }
        String capitalizedStr = new String(capitalizedArray);
        return capitalizedStr;
    }

}