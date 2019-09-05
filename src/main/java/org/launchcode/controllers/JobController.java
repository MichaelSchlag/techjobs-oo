package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job theJob = new Job();
        theJob = jobData.findById(id);
        model.addAttribute("theJob", theJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@Valid JobForm jobForm, Errors errors, Model model) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if(errors.hasErrors()){
            model.addAttribute(jobForm);
            return "new-job";
        }
//        Employer.findById(1);
        JobFieldData<Employer> employers = jobData.getEmployers();
        Employer e = employers.findById(jobForm.getEmployerId());
        JobFieldData<Location> locations = jobData.getLocations();
        Location l = locations.findById(jobForm.getLocationId());
        JobFieldData<PositionType> positionTypes = jobData.getPositionTypes();
        PositionType pt = positionTypes.findById(jobForm.getPositionTypeId());
        JobFieldData<CoreCompetency> coreCompetencies = jobData.getCoreCompetencies();
        CoreCompetency cc = coreCompetencies.findById(jobForm.getCoreCompetencyId());

        Job newJob = new Job(jobForm.getName(), e, l, pt, cc);
        jobData.add(newJob);
        model.addAttribute("theJob", newJob);
        return "job-detail";

//        return "";

    }
}
