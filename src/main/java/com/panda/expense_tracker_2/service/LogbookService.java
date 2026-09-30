package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.LogbookRequest;
import com.panda.expense_tracker_2.dto.LogbookResponse;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogbookService {
    private final LogbookRepository logbookRepository;

    public LogbookResponse createLogbook(LogbookRequest request){
        Logbook saved = logbookRepository.save(new Logbook(request.getName()));
        return new LogbookResponse(saved.getId(), saved.getName());
    }

    public LogbookResponse deleteLogbook(Long id){
        Logbook found = logbookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Logbook not found "));
        logbookRepository.delete(found);
        return new LogbookResponse(found.getId(), found.getName());
    }

    public List<LogbookResponse> listLogbook(){
        List<Logbook> logbooks = logbookRepository.findAll();
        return logbooks.stream()
                .map(logbook -> new LogbookResponse(logbook.getId(),logbook.getName()))
                .collect(Collectors.toList());
    }

}
