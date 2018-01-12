package com.example.dm2.xml;

public class Web{
    private String nombre;
    private String url;



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url.substring(3,url.length()).trim();
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
