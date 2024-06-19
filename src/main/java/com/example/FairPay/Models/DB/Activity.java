package com.example.FairPay.Models.DB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "activity")
public class Activity {
    @Id
    private String _id;

    @Field
    private String name;

    @Field
    private String description;

    @Field
    private String userId;

    @Field
    private Date date;

    @Field
    private String type;

    public Activity(String _id, String name, String userId, String description, Date date, String type) {
        this._id = _id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public Activity() {
    }

    public String getUserId() {return userId; }

    public void setUserId(String userId) {this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) {this.type = type; }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
