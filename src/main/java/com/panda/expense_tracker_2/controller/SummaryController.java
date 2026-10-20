package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.dto.SummaryResponse;
import com.panda.expense_tracker_2.service.SummaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SummaryController {
    private final SummaryService summaryService;

    @GetMapping("/logbooks/{logbookId}/summary")
    public ResponseEntity<SummaryResponse> getSummary(
            @PathVariable
            Long logbookId
    ){
        return new ResponseEntity<SummaryResponse>(summaryService.summary(logbookId),
                HttpStatus.OK);
    }
}
