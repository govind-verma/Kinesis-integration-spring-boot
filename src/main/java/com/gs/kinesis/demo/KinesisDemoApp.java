package com.gs.kinesis.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.expedia.dsp.kinesis.demo")
public class KinesisDemoApp {
    public static void main(String args[]) {
        SpringApplication.run(KinesisDemoApp.class, args);

    }
}


