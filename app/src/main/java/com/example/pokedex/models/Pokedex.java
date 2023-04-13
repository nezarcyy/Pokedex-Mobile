
package com.example.pokedex.models;


public class Pokedex {
    private String name;
    private String url;

    private int id;

    public int getId() {
        String[] pokeid = url.split("/");
        return Integer.parseInt(pokeid[pokeid.length-1]);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
