package itu.nicolas.ticketing.handler;

public enum RolesType {
    admin,
    moderator,
    tester;

//    String value;
//    RolesType(String value) {
//        this.value = value;
//    }

    public String getValue() {
        return this.name();
    }
}
