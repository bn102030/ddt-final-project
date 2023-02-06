package com.ddt.finalproject.frontEndService.impl;

import com.ddt.finalproject.dto.frontEnd.DateFormat;
import com.ddt.finalproject.frontEndService.TimeService;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class TimeServiceImpl implements TimeService {


    @Override
    public DateFormat GetDateFormat() {
        DateFormat dateFormat = new DateFormat();
        List<DateFormat> dateFormatList = new ArrayList<>();
        dateFormat.setDate(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dateFormat.setYearAndMonth(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM")));
        dateFormat.setMonth(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("MM")));
        dateFormat.setDay(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd")));

        return dateFormat;
    }

    @Override
    public String GetLastMonth() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }


}
