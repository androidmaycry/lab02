package com.mad.lab02;

public class ReservationItem {
    private String name;
    private String addr;
    private String cell;
    private Integer img;

    public ReservationItem() {
        //empty contructor
    }

    public ReservationItem(String name, String addr, String cell, Integer img) {
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
