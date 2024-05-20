package com.example.careerhub.model;

public enum Category {

    AGRICULTURE ("agriculture.png"),
    ART ("arts.png"),
    BUSINESS  ("business.png"),
    CONSTRUCTION  ("construction.png"),
    EDUCATION  ("education.png"),
    FINANCE  ("finance.png"),
    HEALTHCARE  ("healthcare.png"),
    HR  ("hr.png"),
    IT ("it.png"),
    MEDIA ("media.png"),
    SPORT  ("sport.png"),
    SERVICE  ("service.png");
    private String iconPath;
    Category(String iconPath) {
        this.iconPath = iconPath;
    }
    public String getIconPath() {
        return iconPath;
    }
}
