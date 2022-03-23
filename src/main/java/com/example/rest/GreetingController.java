package com.example.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;


@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();



    @GetMapping("/greeting")
    Greeting greeting(@RequestParam(value= "name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/deferred")
    public DeferredResult<ResponseEntity<?>> handleReqDefResult(Model model) {
        Logger LOG = Logger.getLogger(GreetingController.class.getName());

        LOG.info("Received async-deferredresult request");
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();

        ForkJoinPool.commonPool().submit(() -> {
            LOG.info("Processing in separate thread");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                LOG.warning("Error while casting Thread.sleep: " + e);
            }
            output.setResult(ResponseEntity.ok("ok"));
        });

        LOG.info("servlet thread freed");
        return output;
    }
}
