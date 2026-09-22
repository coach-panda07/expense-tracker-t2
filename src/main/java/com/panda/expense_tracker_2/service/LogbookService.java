package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.CategoryResponse;
import com.panda.expense_tracker_2.dto.LogbookRequest;
import com.panda.expense_tracker_2.dto.LogbookResponse;
import com.panda.expense_tracker_2.model.Category;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.LogbookRepositiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogbookService {
    private final LogbookRepositiory logbookRepositiory;

public LogbookResponse createLogbook(LogbookRequest request){
    Logbook saved = logbookRepositiory.save(new Logbook(request.getName()));
    return new LogbookResponse(saved.getId(), saved.getName());
}

public LogbookResponse deleteLogbook(Long id){
    Logbook found = logbookRepositiory.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    logbookRepositiory.delete(found);
    return new LogbookResponse(found.getId(), found.getName());

}

}
