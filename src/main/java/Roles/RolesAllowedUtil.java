package Roles;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class RolesAllowedUtil {

    /** get the array of allowed RoleTypes for a given class **/
    public static List<RoleType> getRoleTypesAllowedFromAnnotations(
            Annotation[] annotations) {
        List<RoleType> roleTypesAllowed = new ArrayList<RoleType>();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(
                    RolesAllowed.class)) {
                RoleType[] roleTypes = getRoleTypesFromAnnotation(annotation);
                if (roleTypes != null)
                    for (RoleType roleType : roleTypes)
                        roleTypesAllowed.add(roleType);
            }
        }
        return roleTypesAllowed;
    }

    public static RoleType[] getRoleTypesFromAnnotation(Annotation annotation) {
        Method[] methods = annotation.annotationType().getMethods();
        for (Method method : methods) {
            String name = method.getName();
            Class<?> returnType = method.getReturnType();
            Class<?> componentType = returnType.getComponentType();
            if (name.equals("value") && returnType.isArray()
                    && RoleType.class.isAssignableFrom(componentType)) {
                RoleType[] features;
                try {
                    features = (RoleType[]) (method.invoke(annotation,
                            new Object[] {}));
                } catch (Exception e) {
                    throw new RuntimeException(
                            "Error executing value() method in "
                                    + annotation.getClass().getCanonicalName(),
                            e);
                }
                return features;
            }
        }
        throw new RuntimeException(
                "No value() method returning a RoleType[] type "
                        + "was found in annotation "
                        + annotation.getClass().getCanonicalName());
    }

}
