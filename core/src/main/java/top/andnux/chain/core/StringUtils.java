package top.andnux.chain.core;

import android.text.TextUtils;

public class StringUtils {
    public static boolean isEmpty(String s) {
        if (TextUtils.isEmpty(s)) return true;
        return "null".equalsIgnoreCase(s);
    }
}
