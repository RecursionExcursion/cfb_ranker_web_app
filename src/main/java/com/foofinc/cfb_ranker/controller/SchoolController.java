package com.foofinc.cfb_ranker.controller;

import com.foofinc.cfb_ranker.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cfbr")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/teams")
    public ResponseEntity<String> getTeams() {
        return new ResponseEntity<>(schoolService.getTeams(), HttpStatus.OK);
    }
}
