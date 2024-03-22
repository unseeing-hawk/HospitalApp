package main.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;


@ControllerAdvice
class GlobalDefaultExceptionHandler {
    @ExceptionHandler(HttpStatusCodeException.class)
    public ModelAndView handleError(HttpStatusCodeException ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<Object, Object> map = objectMapper.readValue(ex.getResponseBodyAsString(), HashMap.class);

            mav.addObject("status", map.get("status") + ": ");
            mav.addObject("message", map.get("message"));
            mav.addObject("error", map.get("error"));
        } catch (JsonProcessingException exception) {
            mav.addObject("status", "");
            mav.addObject("message", "");
            mav.addObject("error", "Json processing error");
        }

        return mav;
    }
}
