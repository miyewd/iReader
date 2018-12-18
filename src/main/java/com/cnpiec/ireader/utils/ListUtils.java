package com.cnpiec.ireader.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	/**
	 * 将一个list集合分为n份
	 * @param list 集合
	 * @param n 份数
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> list, int n) {
		List<List<T>> result = new ArrayList<List<T>>();
		int remaider = list.size() % n; // (先计算出余数)
		int number = list.size() / n; // 然后是商
		int offset = 0;// 偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remaider > 0) {
				value = list.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = list.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}

	/**
	 * 将一个集合按每个集合len长度分割
	 * @param list 集合
	 * @param len 分割后每个集合的size
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> list, int len) {
		if (list == null || list.size() == 0 || len < 1) {
			return null;
		}

		List<List<T>> result = new ArrayList<List<T>>();

		int size = list.size();
		int count = (size + len - 1) / len;

		for (int i = 0; i < count; i++) {
			List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}
}
