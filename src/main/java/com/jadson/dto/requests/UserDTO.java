package com.jadson.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
    public String getName()     { return name(); }
    public String getEmail()    { return email(); }
    public String getPassword() { return password(); }

}
