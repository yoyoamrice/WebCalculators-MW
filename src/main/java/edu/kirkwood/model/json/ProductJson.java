package edu.kirkwood.model.json;

import java.util.List;

public class ProductJson {

        private int id;
        private String title;
        private double price;
        private String description;
        private CategoryJson category;
        private List<String> images;

        // Getters and setters


    public ProductJson() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryJson getCategory() {
        return category;
    }

    public void setCategory(CategoryJson category) {
        this.category = category;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
