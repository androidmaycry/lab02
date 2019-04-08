package com.mad.lab02;

public class Reservation_item {
    private String name;
    private String addr;
    private String cell;
    private Integer img;

    public Reservation_item() {
        //empty contructor
    }

    public Reservation_item(String name, String addr, String cell, Integer img) {
        this.name = name;
        this.addr = addr;
        this.cell = cell;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
