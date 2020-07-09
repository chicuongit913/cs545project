package cs545_project.online_market.helper;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN(1, "ROLE_ADMIN"),
    SELLER(2, "ROLE_SELLER"),
    BUYER(3, "ROLE_BUYER"),;
    private int id;
    private String name;
    UserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
