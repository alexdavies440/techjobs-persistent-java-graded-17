package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Employer extends AbstractEntity {

    @NotBlank(message = "Location required")
    @Size(min = 2, max = 100)
    private String location;

    public Employer() {}

    public Employer(String location) {
        super();
        this.location = location;
    }

    public @NotEmpty(message = "Location required") @Size(min = 2, max = 100) String getLocation() {
        return location;
    }

    public void setLocation(@NotEmpty(message = "Location required") @Size(min = 2, max = 100) String location) {
        this.location = location;
    }

}
