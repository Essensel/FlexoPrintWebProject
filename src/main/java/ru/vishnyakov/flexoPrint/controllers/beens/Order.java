package ru.vishnyakov.flexoPrint.controllers.beens;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Entity(name = "orders")
public class Order {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
protected Long id;
    protected Long orderSize;
protected String colors;
    protected int width;
    private int length;
    protected String materialType;
    protected String varnish;
    protected String processing;
    protected String imgName;
    protected String design;
    protected Date orderDate;
    @ManyToOne
    protected Customer orderСreator;

public Order(){
    Calendar calendar = new GregorianCalendar();
    this.orderDate = calendar.getTime();
}

    public Order(String design,Long orderSize, String materialType, String varnish,String processing,
                 int width, int length, String colors){
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

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
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

    public Customer getOrderСreator() {
        return orderСreator;
    }
}
