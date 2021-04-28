package cn.miteng.controller;

import cn.miteng.entity.SummaryRepository;
import cn.miteng.entity.UserRepository;
import cn.miteng.service.http.run;
import cn.miteng.util.Public;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class HelloController {
    @Autowired
    private SummaryRepository summaryRepository;
    @Autowired
    private UserRepository userRepository;

    //    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
//    public JSONObject upload(@RequestParam("file") MultipartFile file,@RequestBody JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
//        return new run().upload(file,requestBody,session,response,request);
//    }
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public JSONObject login(@RequestBody JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        try {
            return new run().login(requestBody, session, response, request);
        }catch (Exception e){
            return new Public().Return("1","内部错误");
        }
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public JSONObject register(@RequestBody JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        try {
            return new run().register(requestBody, session, response, request);
        }catch (Exception e){
            return new Public().Return("1","内部错误");
        }
    }

    @RequestMapping(value = "/get", method = {RequestMethod.POST})
    public JSONObject get(@RequestBody JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        return new run().get(requestBody, session, response, request);
    }

    @RequestMapping(value = "/set", method = {RequestMethod.POST})
    public JSONObject set(@RequestBody JSONObject requestBody, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        try {
            return new run().set(requestBody, session, response, request);
        }catch (Exception e){
            return new Public().Return("1","内部错误");
        }
    }
}
