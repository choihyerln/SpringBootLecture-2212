package com.mulcam.demo.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
	
	@Value("${naver.accessId}") private String accessId;
	@Value("${naver.secretKey}") private String secretKey;
	
    @GetMapping
    public ModelAndView showCalendar() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        ModelAndView mav = new ModelAndView("calendar");
        mav.addObject("month", month);
        mav.addObject("year", year);
        return mav;
    }
}