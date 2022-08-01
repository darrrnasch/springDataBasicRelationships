package com.derniweb.springdatabeginner.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/*
https://hyperskill.org/learn/step/18031
in the examples they don't use the @Id but in realty they use smth like that. They just try to generalize.

"
Here in the topic, we are using the property String code as an id.
But you can use any type you want. In real-world applications, the Long type is commonly used
"
 */


@Entity
public class Treadmill {
    @Id
    private String code;
    private String model;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Treadmill(String code, String model) {
        this.code = code;
        this.model = model;
    }

    public Treadmill() {
    }

}
