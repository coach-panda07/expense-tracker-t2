package com.panda.expense_tracker_2.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){ super(message); }
}
