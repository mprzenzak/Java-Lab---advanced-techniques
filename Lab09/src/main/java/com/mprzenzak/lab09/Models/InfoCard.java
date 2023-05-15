package com.mprzenzak.lab09.Models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "karta_informacyjna")
public class InfoCard {

    private String id;
    private String link;
    private String data;
    private String skrotOrganizacja;
    private String komponentSrodowiska;
    private String typKarty;
    private String rodzajKarty;
    private String nrWpisu;
    private String znakSprawy;
    private String daneWnioskodawcy;

    @XmlElement(name = "id")
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    @XmlElement(name = "link")
    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    @XmlElement(name = "data")
    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    @XmlElement(name = "skrotOrganizacja")
    public String getSkrotOrganizacja() { return skrotOrganizacja; }

    public void setSkrotOrganizacja(String skrotOrganizacja) { this.skrotOrganizacja = skrotOrganizacja; }

    @XmlElement(name = "komponentSrodowiska")
    public String getKomponentSrodowiska() { return komponentSrodowiska; }

    public void setKomponentSrodowiska(String komponentSrodowiska) { this.komponentSrodowiska = komponentSrodowiska; }

    @XmlElement(name = "typKarty")
    public String getTypKarty() { return typKarty; }

    public void setTypKarty(String typKarty) { this.typKarty = typKarty; }

    @XmlElement(name = "rodzajKarty")
    public String getRodzajKarty() { return rodzajKarty; }

    public void setRodzajKarty(String rodzajKarty) { this.rodzajKarty = rodzajKarty; }

    @XmlElement(name = "nrWpisu")
    public String getNrWpisu() { return nrWpisu; }

    public void setNrWpisu(String nrWpisu) { this.nrWpisu = nrWpisu; }

    @XmlElement(name = "znakSprawy")
    public String getZnakSprawy() { return znakSprawy; }

    public void setZnakSprawy(String znakSprawy) { this.znakSprawy = znakSprawy; }

    @XmlElement(name = "daneWnioskodawcy")
    public String getDaneWnioskodawcy() { return daneWnioskodawcy; }

    public void setDaneWnioskodawcy(String daneWnioskodawcy) { this.daneWnioskodawcy = daneWnioskodawcy; }
}
