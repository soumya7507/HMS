package com.hms.controller;

import com.hms.payload.StateDto;
import com.hms.service.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/state")
public class StateController {

    private StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addState(
            @RequestBody StateDto stateDto
    ){
        String s=stateService.addState(stateDto);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

}
