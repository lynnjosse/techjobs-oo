package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        Job thisJob = jobData.findById(id);
        model.addAttribute("job", thisJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute Job newJob, Model model, @Valid JobForm jobForm, Errors errors) {
        if (errors.hasErrors()) {
            return "new-job";
        }

        String thisName = jobForm.getName();

        Location thisLocation = jobData.getLocations().findById(jobForm.getLocationId());
        Employer thisEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        CoreCompetency thisCoreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        PositionType thisPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());

        Job thisJob = new Job (thisName, thisEmployer, thisLocation, thisPositionType, thisCoreCompetency);

        jobData.add(thisJob);

        return "redirect:/job/?id=" + thisJob.getId();
    }
}
