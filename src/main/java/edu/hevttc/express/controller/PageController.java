package edu.hevttc.express.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面跳转Controller
 */
@Controller
public class PageController {
    @GetMapping("/")
    public String showIndex() {
        return "login";
    }

    @GetMapping("/index")
    public String goIndex() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @GetMapping("/registerSuccess")
    public String showRegisterSuccess() {
        return "registerSuccess";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/staff/feedback")
    public String showFeedBack() {
        return "feedback";
    }

    @GetMapping("/staff/myFeedback")
    public String showMyFeedBack() {
        return "staff/myfeedback";
    }

    @GetMapping("/search")
    public String showSearch() {
        return "search";
    }

    @GetMapping("/payment")
    public String showPayment() {
        return "payment";
    }

    @GetMapping("/payment/result")
    public String showPaymentResult() {
        return "payment_result";
    }

    @GetMapping("/admin/express")
    public String showAExpress() {
        return "admin/express";
    }

    @GetMapping("/admin/expressRecycle")
    public String showExpressRecycle() {
        return "admin/expressRecycle";
    }

    @GetMapping("/admin/staff")
    public String showStaff() {
        return "admin/staff";
    }

    @GetMapping("/admin/checkingstaff")
    public String showCheckingStaff() {
        return "admin/checkingstaff";
    }

    @GetMapping("/admin/feedback")
    public String showFeedback() {
        return "admin/feedback";
    }

    @GetMapping("/admin/dataStatistics")
    public String showAdminData() {
        return "admin/userdata";
    }

    @GetMapping("/admin/password")
    public String showPassword() {
        return "admin/password";
    }

    @GetMapping("/staff/home")
    public String showStaffHome() {
        return "staff/home";
    }

    @GetMapping("/staff/express")
    public String showStaffExpress() {
        return "staff/express";
    }

    @GetMapping("/staff/myexpress")
    public String showStaffMyExpress() {
        return "staff/myexpress";
    }

    @GetMapping("/staff/userInfo")
    public String showUserInfo() {
        return "staff/userInfo";
    }

    @GetMapping("/staff/password")
    public String showStaffPassword() {
        return "staff/password";
    }

    @GetMapping("/staff/dataStatistics")
    public String showStaffData() {
        return "staff/userdata";
    }

}