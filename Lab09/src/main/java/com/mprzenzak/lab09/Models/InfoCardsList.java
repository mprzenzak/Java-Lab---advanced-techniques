package com.mprzenzak.lab09.Models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "karty_informacyjne")
public class InfoCardsList {

    private Items items;
    private String size;
    private String total_size;

    @XmlElement(name = "items")
    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    @XmlElement(name = "size")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @XmlElement(name = "total_size")
    public String getTotal_size() {
        return total_size;
    }

    public void setTotal_size(String total_size) {
        this.total_size = total_size;
    }
}
