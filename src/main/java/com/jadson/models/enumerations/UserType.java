package com.jadson.models.enumerations;

public enum UserType {
    ADMINISTRADOR("admin"),     // 0
    USUARIO_PADRAO("padrao"),    // 1
    CONVIDADO("convidado");

    private String role;

    UserType (String role){
        this.role= role;
    }

    public String getRole() {
        return role;
    }
}
