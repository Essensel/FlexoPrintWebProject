package ru.vishnyakov.flexoPrint.controllers.beens;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Long orderSize = Long.valueOf(0);
    protected String colors = "неопределено";
    protected int width = 0;
    private int length = 0;
    protected String materialType = "неопределено";
    protected String varnish = "неопределено";
    protected String processing = "неопределено";
    protected String imgName;
    protected String design = "неопределено";
    protected Date orderDate;
    protected String calculatedCost = "неопределено";
    protected String type = "неопределено";
    protected String status = "НЕОБРАБОТАННО";
    protected String meterssize = "неопределено";

    @ManyToOne
    protected Customer orderСreator;

    public Order() {
        Calendar calendar = new GregorianCalendar();
        this.orderDate = calendar.getTime();
    }

    public String getType() {
        return type;
    }

    public void setType(String status) {
        this.type = status;
    }

    public Order(String design, Long orderSize, String materialType, String varnish, String processing,
                 int width, int length, String colors, String calculatedCost, String status, String meterssize) {
        this.orderSize = orderSize;
        this.colors = colors;
        this.design = design;
        this.materialType = materialType;
        this.varnish = varnish;
        this.processing = processing;
        this.width = width;
        this.length = length;
        this.calculatedCost = calculatedCost;
        this.type = status;
        this.meterssize = meterssize;
        Calendar calendar = new GregorianCalendar();
        this.orderDate = calendar.getTime();
    }

    public Order(String design, Long orderSize, String materialType, String varnish, String processing,
                 int width, int length, String colors) {
        this.orderSize = orderSize;
        this.colors = colors;
        this.design = design;
        this.materialType = materialType;
        this.varnish = varnish;
        this.processing = processing;
        this.width = width;
        this.length = length;
        Calendar calendar = new GregorianCalendar();
        this.orderDate = calendar.getTime();
    }

    public String getMeterssize() {
        return meterssize;
    }

    public void setMeterssize(String orderSizeInMeters) {
        this.meterssize = orderSizeInMeters;
    }

    public String getCalculatedCost() {
        return calculatedCost;
    }

    public void setCalculatedCost(String calculatedCost) {
        this.calculatedCost = calculatedCost;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(Long orderSize) {
        this.orderSize = orderSize;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public void setVarnish(String varnish) {
        this.varnish = varnish;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public void setOrderСreator(Customer customer) {
        this.orderСreator = customer;
    }

    public Long getId() {
        return id;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getVarnish() {
        return varnish;
    }

    public String getProcessing() {
        return processing;
    }

    public String geTextOfProcessing() {
        String processingRussianTxt = "";

        if (processing.equals("") || processing.equals(" ")) {
            processingRussianTxt = "Без дополнительной обработки";
        } else {
            processing.trim();

            for (String retval : processing.split(" ")) {
                switch (retval) {
                    case "coldFoil":
                        processingRussianTxt += "Холодное тиснение. ";
                        break;
                    case "hotFoil":
                        processingRussianTxt += "Горячее тиснение. ";
                        break;
                    case "lamination":
                        processingRussianTxt += "Ламинация. ";
                        break;
                    case "relamDelam":
                        processingRussianTxt += "Печать по клеевому слою. ";
                        break;
                    case "congrev":
                        processingRussianTxt += "Конгрев. ";
                        break;
                    case "traf":
                        processingRussianTxt += "Трафаретная печать. ";
                        break;
                }
            }
        }

        return processingRussianTxt;
    }

    public Customer getOrderСreator() {
        return this.orderСreator;
    }
}
