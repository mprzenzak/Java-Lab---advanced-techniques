package com.mprzenzak.lab09.Models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class Data {

    private InfoCardsList informationCardList;
    private String testowy_tag;

    @XmlElement(name = "karty_informacyjne")
    public InfoCardsList getInformationCardList() {
        return informationCardList;
    }

    public void setInformationCardList(InfoCardsList informationCardList) {
        this.informationCardList = informationCardList;
    }

    @XmlElement(name = "testowy_tag")
    public String getTestowy_tag() {
        return testowy_tag;
    }

    public void setTestowy_tag(String testowy_tag) {
        this.testowy_tag = testowy_tag;
    }
}