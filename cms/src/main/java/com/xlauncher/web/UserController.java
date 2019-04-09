package com.xlauncher.web;

import com.xlauncher.entity.Question;
import com.xlauncher.entity.User;
import com.xlauncher.service.UserService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "用户管理";
    private static Logger logger = Logger.getLogger(UserController.class);
    /**
     * 用户登录验证信息正确则返回Token,Permission等，错误则返回status 415
     * @param userLogin
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> Login(@RequestBody Map<String,Object> userLogin, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> responseMap = new HashMap<>(1);
        responseMap.put("userLoginName",userLogin.get("userLoginName"));
        return userService.login(userLogin,request,response,responseMap);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String,Object> register(@RequestBody User user){
        Map<String,Object> map = new HashMap<>(1);
        int status = userService.register(user);
        map.put("status",status);
        return map;
    }

    /**
     * 个人信息查看
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Map<String,Object> userInfo(@RequestHeader("token")String token){
        return userService.userInfo(token);
    }

    /**
     * 提供发送邮件的用户信息
     * @param token
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mailUser",method = RequestMethod.GET)
    public Map<String,Object> mailUser(@RequestHeader("token")String token, @RequestParam("username")String userName){
        return userService.mailUser(token, userName);
    }


    /**
     * 用户名查重
     * @param roleName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/namecheck",method = RequestMethod.POST)
    public Boolean roleNameCheck(@RequestBody String roleName){
        String[] sb = roleName.split("=");
        int status = userService.countUserByLoginName(sb[1]);
        switch (status){
            case 1:return false;
            case 0:return true;
            default:break;
        }
        return false;
    }

    /**
     * 用户证件信息查重
     * @param userCardId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userCardId",method = RequestMethod.POST)
    public Boolean userCardIdCheck(@RequestBody String userCardId){
        String[] sb = userCardId.split("=");
        int status = userService.countUserCardId(sb[1]);
        switch (status){
            case 1:return false;
            case 0:return true;
            default:break;
        }
        return false;
    }

    /**
     * 获取用户编号用于前端校验
     * @param userCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userCode",method = RequestMethod.POST)
    public Boolean userCodeCheck(@RequestBody @Param("userCode") String userCode){
        String[] sb = userCode.split("&");
        String[] code = sb[0].split("=");
        String[] id = sb[1].split("=");
        int status = userService.countUserCode(code[1],id[1]);
        switch (status){
            case 1:return false;
            case 0:return true;
            default:break;
        }
        return false;
    }

    /**
     * 添加用户
     * @param request
     * @param response
     * @param userInfo
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map<String,Object> insertUser(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String,Object> userInfo,@RequestHeader("token")String token){
        Map<String,Object> map = new HashMap<>(1);
        activeUtil.check(request, response);
        int status = userService.insertUser(userInfo,token);
        map.put("status",status);
        return map;
    }

    /**
     * 删除用户
     * @param request
     * @param response
     * @param userId
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "{userId}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteUser(HttpServletRequest request, HttpServletResponse response,@PathVariable("userId")int userId ,@RequestHeader("token") String token){
        int status;
        activeUtil.check(request, response);
        status=userService.deleteUser(userId,token);
        Map<String,Object> map = new HashMap<>(1);
        map.put("status",status);
        return map;
    }

    /**
     * 修改用户信息
     * @param request
     * @param response
     * @param user
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Map<String,Object> updateUser(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String,Object> user  ,@RequestHeader("token") String token){
        int status;
        activeUtil.check(request, response);
        Map<String,Object> map = new HashMap<>(1);
        status=userService.updateUser(user,token);
        map.put("status",status);
        return map;
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/password",method = RequestMethod.PUT)
    public Map<String,Object> updatePassword(@RequestBody Map<String,Object> user){
        int status;
        Map<String, Object> map = new HashMap<>(1);
        status=userService.updatePassword(user);
        map.put("status",status);
        return map;
    }

    /**
     * 用户注册确认
     * @param userInfo
     * @param response
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.PUT)
    public int confirmRegister(@RequestBody Map<String,Object> userInfo ,HttpServletResponse response,@RequestHeader("token") String token){
        int status = userService.updateUser(userInfo,token);
        if (status!=1){
            response.setStatus(415);
        }
        return status;
    }

    /**
     * 用户信息列表
     * @param request
     * @param response
     * @param userName
     * @param roleName
     * @param userCode
     * @param number
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<User> listUser(HttpServletRequest request, HttpServletResponse response
            ,@RequestParam("username")String userName,@RequestParam("rolename")String roleName,@RequestParam("usercode")String userCode,@RequestParam("page")int number,@RequestHeader("token")String token){
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            List<User> list = new ArrayList<>(1);
            response.setStatus(409);
            return list;
        }
        return userService.listUser(userName,roleName,userCode,number,token);
    }


    /**
     * 用户信息列表分页
     * @param request
     * @param response
     * @param userName
     * @param roleName
     * @param userCode
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public int countUser(HttpServletRequest request, HttpServletResponse response
            ,@RequestParam("username")String userName,@RequestParam("rolename")String roleName,@RequestParam("usercode")String userCode,@RequestHeader("token")String token){
        activeUtil.check(request, response);
        return userService.countPage(userName,roleName,userCode,token);
    }

    /**
     * @param userId
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assign/{userId}",method = RequestMethod.GET)
    public List<Integer> authorizationList(@PathVariable("userId")int userId,@RequestHeader("token") String token){
        return userService.assignList(userId,token);
    }

    /**
     * 查看用户详情
     * @param request
     * @param response
     * @param userId
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public User getUser(HttpServletRequest request, HttpServletResponse response
            ,@PathVariable("userId")int userId,@RequestHeader("token") String token){
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            logger.info("用户没有此权限!" + DESC);
            User user = new User();
            response.setStatus(409);
            return user;
        }
        return userService.getUserById(userId,token);
    }

    @ResponseBody
    @RequestMapping(value = "/checkinfo",method = RequestMethod.POST)
    public Map<String, Object> checkInfo(@RequestBody Map<String,Object> userInfo, HttpServletResponse response){
        int status = userService.checkInfo(userInfo);
        Map<String,Object> map = new HashMap<>();
        if(status==-1){
            response.setStatus(415);
        }
        map.put("id",status);
        return map;
    }

    /**
     * 查看用户密保问题
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/question/{userId}",method = RequestMethod.GET)
    public List<Question> getQuestion(@PathVariable("userId")int userId){
        return userService.listQuestion(userId);
    }

    /**
     * 添加密保问题
     * @param questionList
     * @param request
     * @param response
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/question",method = RequestMethod.POST)
    public int insertQuestion(@RequestBody List<Question> questionList,HttpServletRequest request,HttpServletResponse response,@RequestHeader("token") String token){
        activeUtil.check(request, response);
        return userService.insertQuestion(questionList,token);

    }

    /**
     * 修改用户密保问题
     * @param questionList
     * @param request
     * @param response
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/question",method = RequestMethod.PUT)
    public int updateQuestion(@RequestBody List<Question> questionList,HttpServletRequest request,HttpServletResponse response,@RequestHeader("token") String token){
        activeUtil.check(request, response);
        return userService.updateQuestion(questionList,token);

    }

    /**
     * 密保验证用于重置密码
     * @param question
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/questioncheck",method = RequestMethod.POST)
    public int questionCheck(@RequestBody List<Map<String,Object>> question, HttpServletResponse response){
        int status = userService.questionCheck(question);
        if(status!=1){
            response.setStatus(415);
        }
        return status;
    }

    /**
     * 密保问题验证重置密码
     * @param userInfo
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/infocheck",method = RequestMethod.POST)
    public int InfoCheck(@RequestBody Map<String,Object> userInfo, HttpServletResponse response){
        int status = userService.infoCheck(userInfo);
        if(status!=1){
            response.setStatus(415);
        }
        return status;
    }

    /**
     * 用户退出登录session失效
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> logout(@RequestBody Map<String,String> userForLogout , HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(userForLogout.get("userLoginName") + " is exit!");
        Map<String, String> map = new HashMap<>(1);
        String userLoginName = userForLogout.get("userLoginName");
        if (userLoginName != null) {
            this.userService.getUserByLoginName(userLoginName);
            this.userService.deleteToken(request.getHeader("token"));
            request.getSession().invalidate();
            map.put("OK", "用户退出成功！");
        } else {
            response.sendError(0,"请求错误！userLoginName为空！");
            response.setStatus(415);
            map.put("ERR", "请求错误！userLoginName为空！");
        }
        return map;
    }

    /**
     * 同一浏览器用户再次打开登录页面,验证token session是否有效并默认用户进入系统
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/check",method = RequestMethod.POST)
    @ResponseBody
    public void check(@RequestBody Map<String,String> userForCheck, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        this.userService.getToken(userForCheck,request,response);
    }
}
