package com.xlauncher.web;

import com.xlauncher.entity.Todo;
import com.xlauncher.entity.User;
import com.xlauncher.service.TodoService;
import com.xlauncher.service.UserService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    UserService userService;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    CheckToken checkToken;
    private static final String DESC = "我的待办";
    private static Logger logger = Logger.getLogger(TodoController.class);

    /**
     * 用户注册申请列表
     * @param page
     * @param token
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public List<Todo> registerList(@RequestParam("page")int page, @RequestHeader("token")String token, HttpServletRequest request, HttpServletResponse response){
        activeUtil.check(request,response);
        return todoService.listRegisterTodo(page,token);
    }

    /**
     * 用户注册确认信息
     * @param todoId
     * @param token
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public User registerInfo(@RequestParam("id")int todoId, @RequestHeader("token")String token, HttpServletRequest request, HttpServletResponse response){
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            User user = new User();
            response.setStatus(409);
            return user;
        }
        return userService.registerInfo(todoId,token);
    }

    /**
     * 密码重置申请列表
     * @param page
     * @param token
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetcode",method = RequestMethod.GET)
    public List<Todo> resetCodeList(@RequestParam("page")int page, @RequestHeader("token")String token, HttpServletRequest request, HttpServletResponse response){
        activeUtil.check(request,response);
        return todoService.listResetCodeTode(page,token);
    }

    @ResponseBody
    @RequestMapping(value = "/resetcode/{todoId}",method = RequestMethod.PUT)
    public Map<String,Object> resetCode(@PathVariable("todoId")int todoId,@RequestHeader("token")String token, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>(1);
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return map;
        }
        logger.info("密码重置:: 用户id：" + checkToken.checkToken(token).getUserId() + "用户姓名：" + checkToken.checkToken(token) + ",todoId:" + todoId);
        int status = todoService.resetCode(todoId,token);
        map.put("status",status);
        return map;
    }

    /**
     * 注册拒绝
     * @param todoId
     * @param token
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deny/{todoId}",method = RequestMethod.PUT)
    public Map<String,Object> denyTodo(@PathVariable("todoId")int todoId,@RequestHeader("token")String token, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>(1);
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return map;
        }
        logger.info("拒绝注册申请:: 用户id：" + checkToken.checkToken(token).getUserId() + "用户姓名：" + checkToken.checkToken(token) + ",todoId:" + todoId);
        int status = todoService.denyTodo(todoId,token);
        map.put("status",status);
        return map;
    }

    /**
     * 注册通过
     * @param user
     * @param token
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/confirm",method = RequestMethod.PUT)
    public Map<String,Object> registerConfirm(@RequestBody Map<String,Object> user  ,@RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response){
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String,Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("通过注册申请:: 用户id：" + checkToken.checkToken(token).getUserId() + "用户姓名：" + checkToken.checkToken(token) + ",user:" + user);
        int status = userService.registerConfirm(user,token);
        Map<String,Object> map = new HashMap<>(1);
        map.put("status",status);
        return map;
    }

    /**
     * 用户注册申请列表count
     * @param token
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register/count",method = RequestMethod.GET)
    public Map<String,Object> registerCount(@RequestHeader("token")String token, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>(1);
        activeUtil.check(request,response);
        int count = todoService.countRegisterTodo(token);
        map.put("count",count);
        return map;
    }

    /**
     * 密码重置申请列表count
     * @param token
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetcode/count",method = RequestMethod.GET)
    public Map<String,Object> resetcodeCount(@RequestHeader("token")String token, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>(1);
        activeUtil.check(request,response);
        int count = todoService.countResetCodetode(token);
        map.put("count",count);
        return map;
    }
}
