package whizware.whizware.constant;

import lombok.Getter;

public enum Role {
    STORE("STORE"),
    WAREHOUSE("WAREHOUSE"),
    ADMIN("ADMIN");

    @Getter
    private String name;

    Role(String name) {
        this.name = name;
    }

}
