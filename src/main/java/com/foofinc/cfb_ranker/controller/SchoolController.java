package com.foofinc.cfb_ranker.controller;


import com.foofinc.cfb_ranker.service.ResponseDto;
import com.foofinc.cfb_ranker.service.SchoolService;
import com.foofinc.cfb_ranker.service.entity.StatWeight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cfbr")
@CrossOrigin(origins = "http://localhost:3000")
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/teams")
    public ResponseEntity<ResponseDto> getTeams() {
        return new ResponseEntity<>(schoolService.getTeams(), HttpStatus.OK);
    }


    @PutMapping(value = "/teams", consumes = "application/json")
    public ResponseEntity<ResponseDto> postTeams(@RequestBody StatWeight statWeight) {
        return new ResponseEntity<>(schoolService.getTeams(statWeight), HttpStatus.OK);
    }
}
