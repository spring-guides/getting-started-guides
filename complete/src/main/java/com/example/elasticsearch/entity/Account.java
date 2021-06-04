package com.example.elasticsearch.entity;

import org.elasticsearch.index.VersionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "accounts", versionType = VersionType.INTERNAL, createIndex = true)
public class Account{

    private @Id Integer id;
    private @Field(type = FieldType.Double) Double balance;
    private @Field(type = FieldType.Text) String firstName;
    private @Field(type = FieldType.Text)String lastName;
    private @Field(type = FieldType.Integer) Integer age;
    private @Field(type = FieldType.Text) String gender;
    private @Field(type = FieldType.Text) String address;
    private @Field(type = FieldType.Text) String employer;
    private @Field(type = FieldType.Text) String email;
    private @Field(type = FieldType.Text) String city;
    private @Field(type = FieldType.Text) String state;

    public Account(Integer id, Double balance, String firstName, String lastName, Integer age, String gender, String address, String employer, String email, String city, String state) {
        this.id = id;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.employer = employer;
        this.email = email;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", employer='" + employer + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}