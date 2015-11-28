package com.myprj.crawler.client.domain;

/**
 * @author DienNM (DEE)
 **/
public class PhoneData {

    private String phone;
    private String email;
    private String address;

    @Override
    public String toString() {
        return String.format("Phone: %s, Email: %s", phone, email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
