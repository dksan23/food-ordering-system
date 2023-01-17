package com.food.ordering.system.order.service.application.exceptionhandler;


import com.food.ordering.system.common.application.handler.ErrorDTO;
import com.food.ordering.system.common.application.handler.GlobalExceptionHandler;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderGlobalExceptionHandler extends GlobalExceptionHandler {



    @ResponseBody
    @ExceptionHandler(value = {OrderDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(OrderDomainException orderDomainException)
    {
        return ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(orderDomainException.getMessage()).build()
    }

}
