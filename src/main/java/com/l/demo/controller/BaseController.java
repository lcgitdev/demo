package com.l.demo.controller;

import com.l.demo.model.Log;
import com.l.demo.model.User;
import com.l.demo.service.LogService;
import com.l.demo.service.UserService;
import com.l.demo.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luocheng on 2019/3/1.
 */
@Controller
public class BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static HttpSession getSession(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        HttpSession session = request.getSession();
        return session;
    }

    public static Integer getCurUserId(){
        return (Integer)getSession().getAttribute("userId");
    }

    @GetMapping("/login")
    public String login (Model model) {
        return "login";
    }

    @GetMapping("/index")
    public String index (Model model) {
        Integer userId = getCurUserId();
        if(null != userId){
            User user = userService.queryDataById(userId);
           /* if(StringUtils.isEmpty(user.getImgPath())){
                //没有上传头像  设置默认头像
                user.setImgPath("../../images/photos/1.png");
            }*/

            if(null != user){
                model.addAttribute("user",user);
                return "index";
            }
        }else{
            return "redirect:/login";
        }
        return "redirect:/login";
    }


    @ResponseBody
    @PostMapping("/login/loginIn")
    public Object loginIn (User user) {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        //查询此用户数据
        List<User> list =  userService.queryData(user);
        if(!CollectionUtils.isEmpty(list)){
            //把用户数据保存在session域对象中
            getSession().setAttribute("userId",list.get(0).getId());
            returnMap.put("status",true);

            //记录登录日志
            logService.insert(new Log(list.get(0).getRealName(),"登录系统", DateUtils.getNowDate(),"-"));
            return returnMap;
        }else{
            returnMap.put("status",false);
            returnMap.put("msg","您的登录名不存在或者密码输入错误...");
            return returnMap;
        }
    }

    @ResponseBody
    @GetMapping("/login/loginOut")
    public String loginOut (Model model) {
        Integer userId = getCurUserId();
        User user = userService.queryDataById(userId);
        //记录登出日志
        logService.insert(new Log(user.getRealName(),"退出系统", DateUtils.getNowDate(),"-"));

        getSession().removeAttribute("userId");
        return "success";
    }

}
