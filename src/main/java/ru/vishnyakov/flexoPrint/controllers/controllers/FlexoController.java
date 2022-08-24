package ru.vishnyakov.flexoPrint.controllers.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vishnyakov.flexoPrint.controllers.beens.Customer;
import ru.vishnyakov.flexoPrint.controllers.beens.Order;
import ru.vishnyakov.flexoPrint.controllers.calculations.Calculation;
import ru.vishnyakov.flexoPrint.controllers.services.CustomerService;
import ru.vishnyakov.flexoPrint.controllers.services.OrderService;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Controller
public class FlexoController {


    @Value("${upload.path}")
    private String uploadDirPath;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/main")
    public String showMain() {
        return "mainPaige";
    }

    @RequestMapping("/list/")
    public String showListOfCustomers(ModelMap modelMap) {
        List<Customer> customerList = customerService.getAll();
        modelMap.addAttribute(customerList.size());
        return "listOfCustomers";
    }

    @GetMapping("/check")
    public String showCheckPoint(ModelMap model) {
        return "info";
    }

    @GetMapping("/helpMe")
    public String showmMeHelp(ModelMap model) {
        return "checkpoint";
    }

    @GetMapping("/userChecking")
    public String userChecking(ModelMap model) {
        return "checkPointForUser";
    }

    @PostMapping("/userChecking")
    public String addOrder(@RequestParam  MultipartFile imgFile, String firstName,
                           String lastName, String company,
                           String email, String address,
                           String phone, String comment, String design, Long lCount, String material, String colors,
                           int length, int width,
                           String lak, String coldFoil, String hotFoil,
                           String lamination, String relamDelam, String congrev,
                           String traf, String special,
                           ModelMap model)  throws IOException {


        Customer newCustomer = new Customer(firstName, lastName, company, email, address, phone, comment);
        Long newCustomerId = customerService.checkNewCustomer(newCustomer);

        ArrayList<String> processingList = new ArrayList<>(); //собираем дополнительную обработку
        // из всех чекбоксов в список
        processingList.add(coldFoil);
        processingList.add(hotFoil);
        processingList.add(lamination);
        processingList.add(relamDelam);
        processingList.add(congrev);
        processingList.add(traf);
        processingList.add(special);
        String processingText = "";
        for (int i = 0; i < processingList.size(); i++) {
            if (processingList.get(i) != null) {
                processingText += processingList.get(i) + " "; // информацию из всех чекбоксов собираем в 1 строку для записи в БД
            }
        }

        Order newOrder = new Order(design,lCount,material,lak,processingText,width,length,colors);

        if (!imgFile.isEmpty()) {
            if (imgFile.getContentType().contains("image")) {
                File uploadDir = new File(this.uploadDirPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String imgName = UUID.randomUUID() + "_" +imgFile.getOriginalFilename();
                imgFile.transferTo(new File(this.uploadDirPath + imgName));
                newOrder.setImgName(imgName);
            }
        }
        if (newCustomerId == null) { // если заказчик новый сохраняем его в БД
            customerService.saveCustomer(newCustomer);
            newOrder.setOrderСreator(newCustomer);
        } else { // если заказчик с таким именем, фамилией, названием компании и телефоном уже есть в БД, то
            // находим его ID и добавляем ему новый заказ
            newOrder.setOrderСreator(customerService.getCustomerById(newCustomerId));
        }
        orderService.addOrder(newOrder);
        Calculation calculation = new Calculation();
        System.out.println( newOrder.getProcessing() +"---- "+ calculation.getMaterialWidght(newOrder.getWidth())+" "+
                calculation.getAdditionalProcessingPrice(newOrder.getProcessing(),newOrder.getWidth())+" стоимость тиража "+ calculation.getPrimeCostOfCirculation(newOrder)+"расход материала "+calculation.getMaterialConsumption(newOrder.getOrderSize(),newOrder.getWidth(),newOrder.getLength(),newOrder.getColors()));

        return  "redirect:/culc/"+newOrder.getId();
    }
    @RequestMapping("/culc/{id}")
    public String showUserPage(ModelMap model, @PathVariable Long id){
        if(id>0){
          Order curentOrder = this.orderService.getOrderById(id);
                model.addAttribute("order", curentOrder);
        }
        return "calculation";
    }

    @PostMapping("helpMe")
    public String addNewCustomer(@RequestParam String firstName,
                                 String lastName, String company,
                                 String email, String address,
                                 String phone, String comment,
                                 ModelMap model) {
        Customer newCustomer = new Customer(firstName, lastName, company, email, address, phone, comment);
        customerService.saveCustomer(newCustomer);
        Order newOrder = new Order();
        newOrder.setOrderСreator(newCustomer);
        orderService.addOrder(newOrder);
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