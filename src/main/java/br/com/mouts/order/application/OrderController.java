package br.com.mouts.order.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping(value = "/orders")
@RestController
public class OrderController {

    @PostMapping()
    public ResponseEntity<Void> createOrder(@RequestBody OrderDTO entity) {

        return ResponseEntity.created(URI.create("")).build();
    }

}
