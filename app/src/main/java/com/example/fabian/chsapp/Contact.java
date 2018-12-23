package com.example.fabian.chsapp;

import java.io.Serializable;
import java.util.Objects;

public class Contact implements Serializable {
    String name, number;
    boolean checked;

    public Contact (String name, String number) {
        this.name = name;
        this.number = number;
        this.checked = false;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public String toString() {
        return "Name: " + this.name + "\nNumber: " + this.number;
    }

    public String s() {
        return this.name + " " + this.number;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Contact)) {
            return false;
        }
        Contact c = (Contact) o;
        return name.equals(c.name) && number.equals(c.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }

}
