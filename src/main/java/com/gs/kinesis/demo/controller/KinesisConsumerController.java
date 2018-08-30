package com.gs.kinesis.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs.kinesis.demo.service.IKinesisConsumerService;

@RequestMapping("/consumer")
@RestController
public class KinesisConsumerController {

    @Autowired
    private IKinesisConsumerService kinesisConsumerService;

    @GetMapping("/start")
    public void startConsumer() {
        kinesisConsumerService.start();
    }
}
