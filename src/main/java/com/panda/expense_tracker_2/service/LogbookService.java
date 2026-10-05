package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.LogbookRequest;
import com.panda.expense_tracker_2.dto.LogbookResponse;
import com.panda.expense_tracker_2.model.Income;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.IncomeRepository;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LogbookService {
    private final LogbookRepository logbookRepository;
    private final IncomeRepository incomeRepository;


    @Transactional
    public LogbookResponse createLogbook(LogbookRequest request){
        Logbook saved = logbookRepository.save(new Logbook(request.getName()));
        incomeRepository.save(new Income(request.getIncomeDate(), request.getIncomeAmount(), saved));
        return new LogbookResponse(saved.getId(), saved.getName(), request.getIncomeAmount(), request.getIncomeDate());
    }


    public LogbookResponse deleteLogbook(Long id){
        Logbook found = logbookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Logbook not found "));
        Income income = incomeRepository.findByLogbookId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Income not found"));
        logbookRepository.delete(found);
        return new LogbookResponse(found.getId(), found.getName(), income.getAmount(), income.getDate());
    }

    public List<LogbookResponse> listLogbooks() {
        return logbookRepository.findAll().stream().map(l -> {
            Income i = incomeRepository.findByLogbookId(l.getId()).orElse(null);
            return new LogbookResponse(l.getId(), l.getName(),
                    i == null ? null : i.getAmount(),
                    i == null ? null : i.getDate());
        }).toList();
    }

}
