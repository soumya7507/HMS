package com.hms;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class A {
    public static void main(String[] args) {
    TreeSet<String> a=new TreeSet<>();
    a.add("bn");
    a.add("njji");
    a.add("bb");
    a.add("dd");
        List<String> b = a.stream().filter(x -> x.startsWith("b")).collect(Collectors.toList());
        System.out.println(b);
    }


}
