package com.raphael.extractpdfdata;

import java.util.List;

public class Dados {
    private String area, dados;

    public Dados(String area, String dados) {
        this.area = area.toString();
        this.dados = dados;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDados() {
        return dados;
    }

    public void setDados(String dados) {
        this.dados = dados;
    }
}
