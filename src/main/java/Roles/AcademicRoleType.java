package Roles;

public enum AcademicRoleType implements RoleType {
    STUDENT, xf, DEANERY;
    @Override
    public String toString() {
        return name();
    }
}
