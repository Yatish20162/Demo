package com.payroll.message.function;

import com.payroll.message.dto.LeaveMsgRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.function.Function;

@Configuration
public class LeaveFunction {

    private static final Logger logger = LoggerFactory.getLogger(LeaveFunction.class);

    @Bean
    public Function<LeaveMsgRequestDto, LeaveMsgRequestDto> email(){
        return leaveMsgRequestDto -> {
            logger.info("Sending Email with details - {}", leaveMsgRequestDto);

            return leaveMsgRequestDto;
        };
    }
}
