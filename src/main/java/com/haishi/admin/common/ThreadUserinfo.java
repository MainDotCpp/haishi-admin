package com.haishi.admin.common;

import com.haishi.admin.system.dto.Userinfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadUserinfo {

    private static final ThreadLocal<Userinfo> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Userinfo userinfo) {
        THREAD_LOCAL.set(userinfo);
    }

    public static Userinfo get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
