package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.hibernate.sql.results.graph.embeddable.EmbeddableLoadingLogger;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {

        // May be redundant, can refactor later
        model.addAttribute("title", "Add Job");

        model.addAttribute(new Job());

        // Passes all employers into the add.html template to be looped over in a Select element
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());

        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob, Errors errors,
                                    Model model, @RequestParam int employerId,@RequestParam(required = false) List<Integer> skills) {
        // @ModelAttribute here tells Spring to link attributes like name
        // to the corresponding fields in the Job model so we don't have to explicitly link each one
        // RequestParam is collected from a user input form

        // Does same as above but in case of error in PostMapping so options don't disappear on reload
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("employerId", employerId);

        if (errors.hasErrors()) {
            return "add";
        }

        // Checks if user selected skills, if not, skills won't be present
        // which would otherwise crash the page. If present,
        // captures List of checked skills from user as skill ids and interates
        // through the list and assigns corresponding skills to newJob
        Optional<List<Integer>> optSkills = Optional.ofNullable(skills);
        if (!optSkills.isPresent()) {
            return "add";
        } else {
            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillObjs);
        }

        // Takes employerId input from user and uses it to search employerRepository
        // and then sets employer for newJob to the corresponding Employer
        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        // .orElse() method

        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            newJob.setEmployer(employer);
            model.addAttribute("employer", employer);
        } else {
            optEmployer.orElse(new Employer());
        }

        jobRepository.save(newJob);

        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional<Job> optJob = jobRepository.findById(jobId);

        if(optJob.isPresent()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job);
            return "view";
        } else {
            return "redirect:..";
        }
    }
}
