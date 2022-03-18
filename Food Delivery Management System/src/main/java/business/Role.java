package business;

public enum Role {
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE"),
    CLIENT("CLIENT");

    private final String roleType;

    Role(String roleType){
        this.roleType = roleType;
    }

    public static Role setRole(String roleType){
        switch (roleType){
            case "ADMIN":
                return Role.ADMIN;
            case "EMPLOYEE":
                return Role.EMPLOYEE;
            case "CLIENT":
                return Role.CLIENT;
            default:
                return null;
        }
    }
}
