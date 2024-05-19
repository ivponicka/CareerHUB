package com.example.careerhub.model;

public enum Category {

    AGRICULTURE ("agriculture.jpg"),
    ART ("art.svg"),
    BUSINESS  ("business.svg"),
    CONSTRUCTION  ("construction.svg"),
    EDUCATION  ("education.svg"),
    ENTERTAINMENT  ("entertainment.svg"),
    FINANCE  ("finance.svg"),
    HEALTHCARE  ("healthcare.svg"),
    HR  ("hr.svg"),
    IT ("it.svg"),
    SPORT  ("sport.svg"),
    SERVICE  ("service.svg");
    private String iconPath;

    Category(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }
}
