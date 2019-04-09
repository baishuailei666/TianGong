package com.xlauncher.web;

import com.xlauncher.entity.Permission;
import com.xlauncher.service.PermissionService;
import com.xlauncher.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "权限管理";

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map<String,Object> insertPermission(@RequestBody Permission permission, @RequestHeader("token") String token){
        int status;
        status = permissionService.insertPermission(permission,token);
        Map<String,Object> map = new HashMap<>(1);
        map.put("status",status);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/{permissionId}",method = RequestMethod.DELETE)
    public Map<String,Object> deletePermission(@PathVariable("permissionId") int permissionId, @RequestHeader("token") String token){
        int status;
        status=permissionService.deletePermission(permissionId,token);
        Map<String,Object> map = new HashMap<>(1);
        map.put("status",status);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Map<String,Object> updatePermission(@RequestBody Permission permission, @RequestHeader("token") String token){
        int status;
        status=permissionService.updatePermission(permission,token);
        Map<String,Object> map = new HashMap<>(1);
        map.put("status",status);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Permission> listPermission(@RequestHeader("token") String token){
        return permissionService.listPermission(token);
    }
}
