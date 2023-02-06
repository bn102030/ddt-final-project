package com.ddt.finalproject;

import com.ddt.finalproject.dto.CategoryPriceDto;
import com.ddt.finalproject.dto.UserDto;
import com.ddt.finalproject.dto.frontEnd.CategoryAndPrice;
import com.ddt.finalproject.dto.frontEnd.DateFormat;
import com.ddt.finalproject.frontEndService.SpendService;
import com.ddt.finalproject.frontEndService.TimeService;
import com.ddt.finalproject.service.OrderService;
import com.ddt.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/html")
public class ThymeleafController {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    TimeService timeService;
    @Autowired
    SpendService spendService;

    static String globelDate="current";

    @GetMapping("/")
    public String main(
            Model model,
            @RequestParam String userID) {
        UserDto userDto = userService.getUserData(Long.parseLong(userID));
        DateFormat dateFormat = timeService.GetDateFormat();
        List<CategoryPriceDto> categoryPriceList = orderService.getCatrgoryByUserIDAndDate(userDto.getUserID(),globelDate);
        Integer sumPrice = spendService.GetCurrentSumPrice(userDto.getUserID(),globelDate);
        CategoryAndPrice maxCategory = spendService.GetCurrentMaxCategory(userDto.getUserID(),globelDate);
        Integer comparePrice = spendService.GetComparePriceByCategory(userDto.getUserID(), timeService.GetLastMonth());

        model.addAttribute("categoryList",categoryPriceList);
        model.addAttribute("userInfo", userDto);
        model.addAttribute("dateFormat", dateFormat);
        model.addAttribute("maxCategory", maxCategory);
        model.addAttribute("sumPrice", sumPrice);
        model.addAttribute("comparePrice", comparePrice);

        return "index";
    }

    @GetMapping("/Clist")
    public String cAccordion(Model model,
                             @RequestParam String userID){
        UserDto userDto = userService.getUserData(Long.parseLong(userID));
        DateFormat dateFormat = timeService.GetDateFormat();
        List<CategoryPriceDto> categoryPriceList = orderService.getCatrgoryByUserIDAndDate(userDto.getUserID(),globelDate);
        Integer sumPrice = spendService.GetCurrentSumPrice(userDto.getUserID(),globelDate);
        CategoryAndPrice maxCategory = spendService.GetCurrentMaxCategory(userDto.getUserID(),globelDate);
        Integer comparePrice = spendService.GetComparePriceByCategory(userDto.getUserID(), timeService.GetLastMonth());

        model.addAttribute("categoryPie", categoryPriceList);
        model.addAttribute("userInfo", userDto);
        model.addAttribute("dateFormat", dateFormat);
        model.addAttribute("sumPrice", sumPrice);
        model.addAttribute("maxCategory", maxCategory);
        model.addAttribute("comparePrice", comparePrice);


        return "index";
    }



}