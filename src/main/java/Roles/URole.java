package Roles;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum URole {
    ADMIN(1), User(2), Guest(3);
    private int value;

    private URole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public URole findRole(int v) {
       return Arrays.stream(this.values())
                .filter(role -> role.getValue() == v)
                .findFirst()
                .get();
    }
}
