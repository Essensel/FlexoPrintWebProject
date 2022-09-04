package ru.vishnyakov.flexoPrint.controllers.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.GsonBuilderUtils;
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
                           String lak, String addProcessing, String calculatedCost, String orderSizeInMeters,
                           ModelMap model) throws IOException {

        System.out.println(orderSizeInMeters);
        Customer newCustomer = new Customer(firstName, lastName, company, email, address, phone, comment);
        Long newCustomerId = customerService.checkNewCustomer(newCustomer);
        String orderStatus = "новый заказ";
        Order newOrder = new Order(design, lCount, material, lak, addProcessing, width, length, colors, calculatedCost, orderStatus, orderSizeInMeters);

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
        Long id = customerService.checkNewCustomer(newCustomer);
        Order newOrder = new Order();
        if (id == null) {
            customerService.saveCustomer(newCustomer);
            newOrder.setOrderСreator(newCustomer);
        } else {
            newOrder.setOrderСreator(customerService.getCustomerById(id));
        }
        newOrder.setType("заявка на расчет");
        orderService.addOrder(newOrder);

        return "afterChecking";
    }

    @GetMapping("/addUser")
    public String addUser() {
        return "register";
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

    @GetMapping(value = "/list")
    public String newsList(ModelMap model, @RequestParam(name = "page", defaultValue = "1") Integer pageNum) {
        //новостей на странице
        int pageSize = 20;
        List<Order> ordersList = orderService.getOrdersList(pageNum, pageSize);


        HashMap<Long, Long> map = new HashMap<>();
        for (int i = 0; i < ordersList.size(); i++) {
            map.put(ordersList.get(i).getId(), ordersList.get(i).getOrderСreator().getId());
        }

        model.addAttribute("ordersList", ordersList);
        model.addAttribute("map", map);
        model.addAttribute("customers", customerService);
        model.addAttribute("img", uploadDirPath);


        Integer prevPage, nextPage;
        //посчитать страницы для кнопок "вперед" и "назад"

        if (pageNum < 2) {
            prevPage = null;
        } else {
            prevPage = pageNum - 1;
        }
        //всего страниц
        int pageCount = orderService.getCount() / pageSize + 1;
        //если последняя, то null
        if (pageNum >= pageCount) {
            nextPage = null;
        } else {
            nextPage = pageNum + 1;
        }
        System.out.println(prevPage + " prev");
        System.out.println(nextPage + " next");
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);

        return "orderslist";
    }

    @GetMapping(value = "/list/{id}")
    public String detailOrder(ModelMap model, @PathVariable Long id) {

        model.addAttribute("curentOrder", orderService.getOrderById(id));
        model.addAttribute("curentCustomer", orderService.getOrderById(id).getOrderСreator());
        System.out.println(orderService.getOrderById(id).getProcessing());
        System.out.println(orderService.getOrderById(id).getProcessing().contains("hotFoil"));
        return "detail";
    }

    @PostMapping(value = "/list/{id}")
    public String uodateCustomersOrder(@RequestParam MultipartFile imgFile, String firstName,
                                       String lastName, String company,
                                       String email, String address,
                                       String phone, String comment, String design, Long lCount, String material, String colors,
                                       Integer length, Integer width,
                                       String lak, String coldFoil, String hotFoil, String lamination,
                                       String relamDelam, String congrev, String traf, String calc, String meters, @PathVariable String id) {
        String addProcessing = "";
        if (coldFoil != null) {
            addProcessing += "coldFoil ";
        }
        if (hotFoil != null) {
            addProcessing += "hotFoil ";
        }
        if (lamination != null) {
            addProcessing += "lamination ";
        }
        if (relamDelam != null) {
            addProcessing += "relamDelam ";
        }
        if (congrev != null) {
            addProcessing += "congrev ";
        }
        if (traf != null) {
            addProcessing += "traf ";
        }

        return "redirect:/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteOrder(ModelMap model, @PathVariable Long id) {
        boolean isDeleted = false;
        if (orderService.deleteById(id)) {
            isDeleted = true;


            return "redirect:/list";
        }

        return "redirect:/culc/" + id;
    }

}