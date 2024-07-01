package com.haishi.admin.common.utils;

import com.haishi.admin.common.ThreadUserinfo;
import com.haishi.admin.common.entity.QBaseEntity;
import com.haishi.admin.system.constants.PermissionCode;
import com.querydsl.core.types.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueryUtils {
    public static ArrayList<Predicate> dataPermissionFilter(QBaseEntity _super, ArrayList<Predicate> predicates) {
        var userinfo = ThreadUserinfo.get();
        List<String> permissions = userinfo.getPermissions();
        if (predicates == null) {
            predicates = new ArrayList<>();
        }
        if (permissions.contains(PermissionCode.DATA__ALL)) {
            return predicates;
        }

        if (permissions.contains(PermissionCode.DATA__DEPT_AND_CHILD)) {
            predicates.add(_super.deptId.startsWith(userinfo.getDeptId()));
        } else if (permissions.contains(PermissionCode.DATA__DEPT)) {
            predicates.add(_super.deptId.eq(userinfo.getDeptId()));
        } else if (permissions.contains(PermissionCode.DATA__SELF)) {
            predicates.add(_super.owner.id.eq(userinfo.getId()));
        }
        return predicates;
    }

    public static ArrayList<Predicate> dataPermissionFilter(QBaseEntity _super) {
        return dataPermissionFilter(_super, null);
    }


}
