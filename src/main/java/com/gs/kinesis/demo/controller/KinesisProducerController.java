package com.gs.kinesis.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs.kinesis.demo.service.IKinesisProducerService;

@RequestMapping("/producer")
@RestController
public class KinesisProducerController {

    @Autowired
    private IKinesisProducerService kinesisProducerService;

    @PostMapping("publish")
    public void publish(@RequestBody String data){
        kinesisProducerService.publish(data);
    }
}
