package com.mintifi.ordermanagement;

import com.mintifi.ordermanagement.util.PasswordUtil;

public class TestHash {

    public static void main(String[] args) {

        System.out.println(
                PasswordUtil.hashPassword("admin123")
        );
    }
}