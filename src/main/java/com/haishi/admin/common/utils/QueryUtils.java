package com.haishi.admin.common.utils;

import com.haishi.admin.common.ThreadUserinfo;
import com.haishi.admin.common.entity.QBaseEntity;
import com.haishi.admin.system.constants.PermissionCode;
import com.querydsl.core.types.Predicate;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static void dataPermissionFilter(QBaseEntity _super, ArrayList<Predicate> predicates) {
        List<String> permissions = ThreadUserinfo.get().getPermissions();
        if (permissions.contains(PermissionCode.DATA__ALL)) {
            return;
        }

        if (permissions.contains(PermissionCode.DATA__DEPT_AND_CHILD)) {
            predicates.add(_super.deptId.startsWith(ThreadUserinfo.get().getDeptId()));
        } else if (permissions.contains(PermissionCode.DATA__DEPT)) {
            predicates.add(_super.deptId.eq(ThreadUserinfo.get().getDeptId()));
        } else if (permissions.contains(PermissionCode.DATA__SELF)) {
            predicates.add(_super.createdBy.eq(ThreadUserinfo.get().getId()));
        }
    }
}
