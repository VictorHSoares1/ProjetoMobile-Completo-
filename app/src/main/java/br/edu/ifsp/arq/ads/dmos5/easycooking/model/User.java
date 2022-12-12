package br.edu.ifsp.arq.ads.dmos5.easycooking.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "user")
public class User implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String email;
    private String name;
    private String surname;
    private String password;
    private String birthDate;
    private String gender;
    private String phone;
    private String image;

    public User(String email, String name, String surname, String password, String birthDate,
                String gender, String phone, String image) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.image = image;
    }

    public User() {
        this("", "", "", "", "", "", "", "");
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
