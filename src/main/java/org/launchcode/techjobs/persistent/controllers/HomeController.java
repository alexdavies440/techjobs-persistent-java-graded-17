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
//        model.addAttribute("jobs", jobRepository.findAll());



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
                                    Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
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

        // Takes employerId input from user and uses it to search employerRepository
        // and then sets employer for newJob to the corresponding Employer
        Optional<Employer> optEmployer = employerRepository.findById(employerId);

        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            newJob.setEmployer(employer);
            model.addAttribute("employer", employer);
        }

        // Captures List of checked skills from user as skill ids and interates
        // through the list and assigns corresponding skills to newJob
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);

        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        return "view";
    }


}
