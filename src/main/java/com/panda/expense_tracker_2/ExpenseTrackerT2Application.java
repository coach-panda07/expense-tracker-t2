package com.panda.expense_tracker_2;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseTrackerT2Application {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String secret = dotenv.get("JWT_SECRET");
        if (secret != null){
            System.setProperty("JWT_SECRET", secret);
        }
        SpringApplication.run(ExpenseTrackerT2Application.class, args);
    }

}
