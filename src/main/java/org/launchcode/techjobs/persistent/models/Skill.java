package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;

@Entity
public class Skill extends AbstractEntity {

    @Size(max = 800, message = "Character limit of 800 reached. Consider a more concise job description")
    private String description;

    public Skill() {}

    public Skill(String description) {
        this.description = description;
    }

    public @Size(max = 800, message = "Character limit of 800 reached. Consider a more concise job description") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 800, message = "Character limit of 800 reached. Consider a more concise job description") String description) {
        this.description = description;
    }
}
