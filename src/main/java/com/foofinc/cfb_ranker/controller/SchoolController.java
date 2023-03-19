package com.foofinc.cfb_ranker.controller;

import com.foofinc.cfb_ranker.service.ResponseDto;
import com.foofinc.cfb_ranker.service.SchoolService;
import com.foofinc.cfb_ranker.service.entity.StatWeight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cfbr")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/teams")
    public ResponseEntity<ResponseDto> getTeams() {
        return new ResponseEntity<>(schoolService.getTeams(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/teams")
    public ResponseEntity<ResponseDto> postTeams(@RequestBody StatWeight statWeight){
        return new ResponseEntity<>(schoolService.getTeams(statWeight), HttpStatus.OK);
    }

}
