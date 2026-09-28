package com.panda.expense_tracker_2.dto;

import lombok.Data;

@Data
public class LogbookResponse {
    private Long id;
    private String name;

    public LogbookResponse(Long id, String name){
        this.name = name;
        this.id = id;
    }

}
