package com.example.B20DCDT008.library.Model;


import java.util.Date;

public class Book {
    private String name, author, category, cover, description;
    private int id, pages_number, sold;
    private Date public_day;

    public Book() {

    }

    public Book(String name, String author, String category, String cover, String description, int id, int pages_number, int sold, Date public_day) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.cover = cover;
        this.description = description;
        this.id = id;
        this.pages_number = pages_number;
        this.sold = sold;
        this.public_day = public_day;
    }

    public String getDescription() {
        return description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPages_number() {
        return pages_number;
    }

    public void setPages_number(int pages_number) {
        this.pages_number = pages_number;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public Date getPublic_day() {
        return public_day;
    }

    public void setPublic_day(Date public_day) {
        this.public_day = public_day;
    }
}
