package com.haishi.admin.system.enums;

public enum DataPermission {
    ALL(0, "全部"),
    SELF(1, "本人"),
    DEPT(2, "本部门"),
    DEPT_AND_CHILD(3, "本部门及子部门");

    private final int code;
    private final String name;

    DataPermission(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
