package com.xlauncher.web;

import com.xlauncher.entity.Role;
import com.xlauncher.service.RoleService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    CheckToken checkToken;
    private static final String DESC = "角色管理";
    private static Logger logger = Logger.getLogger(OrganizationController.class);
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map<String,Object> insertRole(@RequestBody Map<String,Object> role , @RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response){
        int status;
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("添加角色: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",role:" + role);
        status=roleService.insertRole(role,token);
        Map<String,Object> map = new HashMap<>(1);
        map.put("status",status);
        return map;
    }

    /**
     * 角色名称查重
     * @param roleName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/roleName",method = RequestMethod.POST)
    public Boolean roleNameCheck(@RequestBody String roleName){
        String[] sb = roleName.split("=");
        int status ;
        try {
            status = roleService.countRoleName(URLDecoder.decode(sb[1],"utf-8"));
        } catch (UnsupportedEncodingException e) {
            status=-1;
        }
        switch (status){
            case 1:return false;
            case 0:return true;
            default:return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "{roleId}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteRole(@PathVariable("roleId")int roleId ,@RequestHeader("token") String token,  HttpServletRequest request, HttpServletResponse response){
        int status;
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("删除角色: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        status=roleService.deleteRole(roleId,token);
        Map<String,Object> map = new HashMap<>(1);
        map.put("status",status);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Map<String,Object> updateRole(@RequestBody Map<String,Object> role  ,@RequestHeader("token") String token,  HttpServletRequest request, HttpServletResponse response){
        int status;
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("修改角色: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",role:" + role);
        status=roleService.updateRole(role,token);
        Map<String,Object> map = new HashMap<>(1);
        map.put("status",status);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Role> listRole(@RequestHeader("token") String token,  HttpServletRequest request, HttpServletResponse response){
        activeUtil.check(request,response);
        return roleService.listRole(token);
    }

    @ResponseBody
    @RequestMapping(value = "/authorization/{roleId}",method = RequestMethod.GET)
    public List<Integer> authorizationList(@PathVariable("roleId")int roleId,@RequestHeader("token") String token,  HttpServletRequest request, HttpServletResponse response){
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            List<Integer> list = new ArrayList<>(1);
            response.setStatus(409);
            return list;
        }
        return roleService.listAuthorization(roleId,token);
    }

    @ResponseBody
    @RequestMapping(value = "/{roleId}",method = RequestMethod.GET)
    public Role getROle(@PathVariable("roleId")int roleId,@RequestHeader("token") String token,  HttpServletRequest request, HttpServletResponse response){
        activeUtil.check(request,response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Role role = new Role();
            response.setStatus(409);
            return role;
        }
        return roleService.getRoleById(roleId,token);
    }

}
