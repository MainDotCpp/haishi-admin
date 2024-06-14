package com.haishi.admin.common.utils;

import java.io.IOException;

public class WebDownloadUtils {
    public static void download(String url, String path) throws IOException {
        Runtime.getRuntime().exec("wget -c -r -npH -k -nv https://b-mc.xyz/");
    }
}
