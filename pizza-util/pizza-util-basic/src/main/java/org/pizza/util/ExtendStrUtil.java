package org.pizza.util;

import cn.hutool.core.util.StrUtil;
/**
 * @author 高巍
 * @since 2020/10/13 2:23 下午
 */
public class ExtendStrUtil {
    public ExtendStrUtil() {
    }

    public static String humpToUnderline(String para) {
        para = StrUtil.lowerFirst(para);
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;

        for(int i = 0; i < para.length(); ++i) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                ++temp;
            }
        }

        return sb.toString().toLowerCase();
    }

    public static StringBuilder appendBuilder(StringBuilder sb, CharSequence... strs) {
        int var3 = strs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            CharSequence str = strs[var4];
            sb.append(str);
        }

        return sb;
    }
}
