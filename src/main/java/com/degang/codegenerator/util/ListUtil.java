package com.degang.codegenerator.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ListUtil {
	/**
	 * 从list指定属性prop收集新集合
	 * @param list
	 * @param prop
	 * @param clazz
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	public static <T, P> List<P> collect(List<T> list, String prop, Class<P> clazz){
		Method getMethod = null;
		String firstLetter = prop.substring(0, 1).toUpperCase();
		String get = "get" + firstLetter + prop.substring(1);
		ArrayList<P> ret = new ArrayList<P>();
		for(T item : list){
			try{
				if(getMethod == null){
					getMethod = item.getClass().getMethod(get, new Class[]{});
				}
				ret.add((P)getMethod.invoke(item, new Object[]{}));
			}catch(Exception e){
				log.error("listUtil", e);
			}
		}
		return ret;
	}

    /**
     * list<String>转换为string字符串用逗号分隔,用逗号分隔
     * @return
     */
    public static String list2String(List list) {
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < list.size(); i++) {
            sb = sb.append(list.get(i) + ",");
        }
        return sb.substring(0, sb.length()-1);
    }

    /**
     * 数组转换成字符串,用逗号分隔
     * @param list
     * @return
     */
    public static String array2String(String[] list) {
        if(list == null || list.length == 0) return null;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < list.length; i++) {
            sb = sb.append(list[i] + ",");
        }
        return sb.substring(0, sb.length()-1);
    }

    /**
     * 字符串转换为list数组
     * @param str 以逗号分隔的字符串
     * @return
     */
    public static List<String> string2List(String str) {
        if(StringUtils.isEmpty(str)) {
            return null;
        }

        String[] array = str.split(",");
        List<String> list = new ArrayList<>();
        for(String item : array) {
            list.add(item);
        }
        return list;
    }

    /**
     * 字符串转换为list数组
     * @param str 以逗号分隔的字符串
     * @return str 为空返回 new ArrayList
     */
    public static List<String> string2ListReturnNotNull(String str) {
        if(StringUtils.isEmpty(str)) {
            return new ArrayList<>();
        }

        String[] array = str.split(",");
        List<String> list = new ArrayList<>();
        for(String item : array) {
            list.add(item);
        }
        return list;
    }

    /**
     * 字符串列表转换成整形列表
     * @param list
     * @return
     */
    public static List<Integer> strList2IntList(List<String> list) {
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Integer> returnList = new ArrayList<>();

        for(String str : list) {
            if(StringUtils.isEmpty(str)) continue;
            returnList.add(Integer.parseInt(str));
        }
        return returnList;
    }


    public static String list2StringInBlank(List<String> list) {
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(String str : list) {
            if(StringUtils.isNotBlank(str)) {
                sb = sb.append(str.trim() + " ");
            }
        }
        return sb.toString().trim();
    }
}
