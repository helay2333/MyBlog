package com.example.green.controller;

import com.example.green.common.*;
import com.example.green.model.UserInfo;
import com.example.green.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Green写代码
 * @date 2023-01-11 16:15
 * 用户控制器
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/reg")
    public Object reg(String username, String password){
        //1.非空校验
        //尽管前端做了非空校验, 但是我们依旧需要非空校验, 因为不一定所有请求都是从浏览器来的
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return AjaxResult.fail(-1, "非法的参数请求");//比如postman中就不是浏览器来的 , 可能会走这里
        }
        UserInfo user = userService.getUserByName(username);
        if(user != null && user.getId() >= 0){
            return AjaxResult.fail(-1,"用户名重复, 请重新注册吧!");
        }
        //2. 进行添加操作
        String firstPhoto = "img/first.jpg";
        int result = userService.add(username, SecurityUtil.encrypt(password),firstPhoto);
        if(result == 1){
            return AjaxResult.success("添加成功",1);
        }else{
            return AjaxResult.fail(-1,"数据库添加出错");
        }
    }

    /**
     * 登录功能
     * @param username
     * @param password
     * @return 如果用户和密码都正确, 返回1; 如果用户名或者密码为空 或者不正确, 那么返回非1
     */
    @ResponseBody
    @RequestMapping("/login")
    public int login(HttpServletRequest request, String username, String  password){
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)){
            return 0;
        }
        //进行添加操作
        UserInfo userInfo = userService.getUserByName(username);
        if(userInfo == null || userInfo.getId() <= 0){
            //用户名或者密码错误   userinfo无效
            return -1;
        }else{
            System.out.println("登录的时候用到的密码:  "+password);
            boolean result = SecurityUtil.decrypt(password, userInfo.getPassword());
            if(result){
                //用户名和密码正确
                // 将userinfo保存到session中

                HttpSession session = request.getSession();

                session.setAttribute(ConstantVariable.session_userinfo_key, userInfo);
                return 1;
            }
        }
        return -1;
    }

    /**
     * 退出功能  需要清除session
     * @return
     */
    @ResponseBody
    @RequestMapping("/logout")
    public boolean logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);//如果有就操作, 没有说明没有登录
        if(session != null && session.getAttribute(ConstantVariable.session_userinfo_key) != null){
             session.removeAttribute(ConstantVariable.session_userinfo_key);
        }
        return true;
    }
    @ResponseBody
    @RequestMapping("/myinfo")
    public UserInfo myInfo(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(ConstantVariable.session_userinfo_key) != null) {
            UserInfo userInfo = SessionUtil.getLoginUser(request);
            return userInfo;
//            return  (UserInfo) session.getAttribute(ConstantVariable.session_userinfo_key);

        }
        return null;
    }
    @ResponseBody
    @RequestMapping("hi")
    public String sayHi(){
        return "Hi, Blog";
    }
    @ResponseBody
    @RequestMapping("/isLogin")
    public Object isLogin(HttpServletRequest request){
        HttpSession session = request.getSession(false);//如果有就操作, 没有说明没有登录
        if(session != null && session.getAttribute(ConstantVariable.session_userinfo_key) != null){
            return AjaxResult.success("true");
        }
        return AjaxResult.fail(404,"false");
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

//    private String uploadPath = "E:/niuke/work/upload";
    private String uploadPath = "/root/Spring/img";
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model, HttpServletRequest request){
        if(headerImage == null) {
            model.addAttribute("error", "您还没有选择图片");
            return "/setting";
        }
        String fileName = headerImage.getOriginalFilename();
        //后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if(!StringUtils.hasLength(suffix)){
            model.addAttribute("error","文件格式不正确");
            return "/setting";
        }
        fileName = ProjectUtil.generateUUID() + suffix;
        //确定文件存放的路径
        File dest = new File(uploadPath + "/" + fileName);
        try{
            headerImage.transferTo(dest);
        }catch (IOException e){
            logger.error("上传文件失败"+ e.getMessage());
            throw new RuntimeException("上传 文件失败, 服务器发生异常", e);
        }
        String domain = "http://8.130.30.189:8080";
        String contextPath = "";
        //更新当前用户的头像的路径
        //http://localhost:8080/user/header/xxx.png
        UserInfo user = SessionUtil.getLoginUser(request);
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updatePhoto(user.getId(), headerUrl);

        UserInfo sessionUser = SessionUtil.getLoginUser(request);
        sessionUser.setPhoto(headerUrl);
        return "redirect:/myblog_list.html";

    }
    @ResponseBody
    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/changePassword")
    public Object changePassword(String password, HttpServletRequest httpServletRequest,String oldpasssword) {
        UserInfo userInfo = SessionUtil.getLoginUser(httpServletRequest);
        if(userInfo != null && userInfo.getId() >= 0){
            System.out.println("更改密码时候的原密码:  "+oldpasssword);
            boolean confirmOldPas = SecurityUtil.decrypt(oldpasssword, userInfo.getPassword());
            System.out.println(confirmOldPas);
            if(!confirmOldPas){
                return AjaxResult.fail(-1, "原密码不正确, 请重试");
            }
            String newPassword = SecurityUtil.encrypt(password);
            System.out.println(newPassword);
            userService.updatePassword(userInfo.getId(), newPassword);
            return AjaxResult.success("修改成功",1);
        }
        return -1;
    }

    @ResponseBody
    @RequestMapping("/myartCount")
    public Integer getMyCount(HttpServletRequest request) {
        UserInfo userInfo = SessionUtil.getLoginUser(request);
        return userService.getMyArtCount(userInfo.getId());
    }
}
