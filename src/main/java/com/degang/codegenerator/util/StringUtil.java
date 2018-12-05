package com.degang.codegenerator.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by degang on 2018/11/29
 */
@Slf4j
public class StringUtil {

    public static void main(String[] args) {
        String s = "我是测试1a";
        System.out.println(genLengthStr(s,5));
    }

    /**
     * 根据指定长度生成字符串，过长截取，不够用空格补足。
     * @param instr 一个英文字母、汉字、标点，都算是一个长度。汉字转成getBytes占4个长度
     * @param len 长度
     * @return 指定长度的字符串
     */
    public static String genLengthStr(String instr,int len){
        if (instr == null) {
            return getBlank(len);
        }
        if(instr.length() > len){
            return instr.substring(0,len);
        }

        return instr + getBlank(len-instr.getBytes().length);
    }

    public static String genLengthStr(Integer i,int len){
        return genLengthStr(getObjString(i), len);
    }


    private static String getObjString(Object obj){
        return obj == null ? null : obj.toString();
    }

    private static String getBlank(int len){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<len; i++){
            sb.append(" ");
        }
        return sb.toString();
    }
}
