package ru.vishnyakov.flexoPrint.controllers.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vishnyakov.flexoPrint.controllers.beens.Customer;
import ru.vishnyakov.flexoPrint.controllers.services.CustomerService;


@Controller
public class FlexoController {


@Autowired
    private CustomerService customerService;

    @RequestMapping("/main")
    public String showMain() {
        return "mainPaige";
    }

    @GetMapping("/check")
    public String showCheckPoint(ModelMap model) {
        return "info";
    }
    @GetMapping("/helpMe")
    public String showmMeHelp(ModelMap model) {
        return "checkpoint";
    }

    @PostMapping ("helpMe")
    public String addNewCustomer(@RequestParam String firstName,
                                 String lastName,  String company,
                                 String email, String address,
                                 String phone, String comment,
                                 ModelMap model) {

        Customer newCustomer = new Customer(firstName, lastName, company, email, address,phone, comment );
        customerService.saveCustomer(newCustomer);
        return "afterChecking";
    }

    @GetMapping("/label")
    public String showLabel() {
        return "label";
    }

    @GetMapping("/about")
    public String showInfAbout() {
        return "about";
    }

    @GetMapping("/advant")
    public String showAdvantages() {
        return "advantages";
    }

    @GetMapping("/geo")
    public String showGeography() {
        return "geography";
    }
    @GetMapping("/partners")
    public String showPartners() {
        return "partners";
    }
    @GetMapping("/jobseeker")
    public String showJobseekers() {
        return "jobseeker";
    }
    @GetMapping("/contacts")
    public String showContacts() {
        return "contacts";
    }
    @GetMapping("/types")
    public String showToF() {
        return "types-of-finishes";
    }
    @GetMapping("/schema")
    public String showSchema() {
        return "schema";
    }
    @GetMapping("/pack")
    public String showPack() {
        return "pack";
    }
}