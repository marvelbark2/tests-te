package Roles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@RolesAllowed()
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD})
public @interface AcademicRolesAllowed {
    public AcademicRoleType[] value();
}
