package com.ddt.finalproject.frontEndService;


import com.ddt.finalproject.dto.frontEnd.DateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface TimeService {

    DateFormat GetDateFormat();

    String GetLastMonth();




}
