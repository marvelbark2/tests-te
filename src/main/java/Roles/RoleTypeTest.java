package Roles;

import java.util.List;

public class RoleTypeTest {

    @AcademicRolesAllowed({AcademicRoleType.DEANERY, AcademicRoleType.STUDENT})
    public class DeaneryDemo {

    }
}
