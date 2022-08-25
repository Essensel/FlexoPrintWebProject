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


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


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
    public String userChecking(ModelMap model, HttpServletRequest request) throws IOException, ServletException {
        boolean flagForTh = false;
        if (request.getParameter("lCount") != null) {
            flagForTh = true;
            Long lCount = Long.parseLong(request.getParameter("lCount"));
            String design = request.getParameter("design");
            int width = Integer.parseInt(request.getParameter("width"));
            int lenght = Integer.parseInt(request.getParameter("length"));
            String colors = request.getParameter("colors");
            String material = request.getParameter("material");
            String lak = request.getParameter("lak");
            String processingText = "";
            if (request.getParameter("lamination") != null) {
                processingText += request.getParameter("lamination") + " ";
            }
            if (request.getParameter("coldFoil") != null) {
                processingText += request.getParameter("coldFoil") + " ";
            }
            if (request.getParameter("hotFoil") != null) {
                processingText += request.getParameter("hotFoil") + " ";
            }
            if (request.getParameter("relamDelam") != null) {
                processingText += request.getParameter("relamDelam") + " ";
            }
            if (request.getParameter("congrev") != null) {
                processingText += request.getParameter("congrev") + " ";
            }
            if (request.getParameter("traf") != null) {
                processingText += request.getParameter("traf") + " ";
            }
            Order curentOrder = new Order(design, lCount, material, lak, processingText, width, lenght, colors);
            Calculation calculation = new Calculation();

            model.addAttribute("order", curentOrder);
            model.addAttribute("calculation", calculation);
            model.addAttribute("calc", String.format("%.0f", calculation.getPriceWithMarkup(curentOrder)));
            model.addAttribute("calcOfMaterial", String.format("%.3f", calculation.getMaterialConsumption(curentOrder.getOrderSize()

                    , curentOrder.getWidth(), curentOrder.getLength(), curentOrder.getColors())));

            System.out.println(processingText);
            System.out.println(calculation.getPrimeCostOfCirculation(curentOrder));
            System.out.println(" цена доп обр " + calculation.getAdditionalProcessingPrice(processingText, width) * calculation.getMaterialConsumption(lCount, width, lenght, colors) +
                    " pri razmere t " + calculation.getMaterialConsumption(curentOrder.getOrderSize(), curentOrder.getWidth(), curentOrder.getLength(), curentOrder.getColors()));
            return "saveOrder";
        }
        return "checkPointForUser";
    }

    @PostMapping("/userChecking")
    public String addOrder(@RequestParam MultipartFile imgFile, String firstName,
                           String lastName, String company,
                           String email, String address,
                           String phone, String comment, String design, Long lCount, String material, String colors,
                           Integer length, Integer width,
                           String lak, String addProcessing, String calculatedCost,
                           ModelMap model) throws IOException {


        Customer newCustomer = new Customer(firstName, lastName, company, email, address, phone, comment);
        Long newCustomerId = customerService.checkNewCustomer(newCustomer);

          Order newOrder = new Order(design,lCount,material,lak,addProcessing,width,length,colors,calculatedCost);

        if (!imgFile.isEmpty()) {
            if (imgFile.getContentType().contains("image")) {
                File uploadDir = new File(this.uploadDirPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String imgName = UUID.randomUUID() + "_" + imgFile.getOriginalFilename();
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

        return "redirect:/culc/" + newOrder.getId();
    }

    @RequestMapping("/culc/{id}")
    public String showUserPage(ModelMap model, @PathVariable Long id) {
        if (id > 0) {
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