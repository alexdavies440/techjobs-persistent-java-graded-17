package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {

    @NotBlank(message = "Location required")
    @Size(min = 2, max = 100)
    private String location;

    @OneToMany
    @JoinColumn(name = "employer_id")

    // Consider whether this field should be final or not, if so get rid of setter
    private List<Job> jobs = new ArrayList<>();

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

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
