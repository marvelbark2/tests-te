package Roles;

import java.util.List;

public class App {
    public static void main(String[] args) {
        List<RoleType> roleTypes = RolesAllowedUtil.getRoleTypesAllowedFromAnnotations(RoleTypeTest.DeaneryDemo.class.getAnnotations());

        System.out.println(roleTypes);

        System.out.println(URole.ADMIN.findRole(2));
    }
}
