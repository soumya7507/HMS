package com.hms.service;

import com.hms.entity.State;
import com.hms.payload.StateDto;
import com.hms.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StateService {
    private StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }


    public String addState(StateDto stateDto) {
        Optional<State> state = stateRepository.findStateByName(stateDto.getName());
        if(state.isPresent()){
            return "State already exists";

        }
        State state1 = new State();
        state1.setName(stateDto.getName());
        stateRepository.save(state1);
        return "State added successfully";


    }
}
