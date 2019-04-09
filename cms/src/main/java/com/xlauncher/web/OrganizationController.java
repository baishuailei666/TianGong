package com.xlauncher.web;

import com.xlauncher.entity.Organization;
import com.xlauncher.service.OrganizationService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    CheckToken checkToken;
    private static final String DESC = "组织管理";
    private static Logger logger = Logger.getLogger(OrganizationController.class);
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Organization> listOrganization(@RequestHeader("token") String token, HttpServletResponse response, HttpServletRequest request){
        logger.info("查询组织listOrganization: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        activeUtil.check(request, response);
        return organizationService.listOrganization(token);
    }

    @ResponseBody
    @RequestMapping(value = "",method =RequestMethod.POST)
    public int insertOrganization(@RequestBody Organization organization,@RequestHeader("token") String token, HttpServletResponse response, HttpServletRequest request){
        logger.info("添加组织insertOrganization: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",organization:" + organization);
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return 0;
        }
        return organizationService.insertOrganization(organization,token);
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public int updateOrganization(@RequestBody Organization organization,@RequestHeader("token") String token, HttpServletResponse response, HttpServletRequest request){
        logger.info("修改组织updateOrganization: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",organization:" + organization);
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return 0;
        }
        return organizationService.updateOrganization(organization,token);
    }

    @ResponseBody
    @RequestMapping(value = "/{orgId}",method = RequestMethod.DELETE)
    public int deleteOrganization(@PathVariable("orgId") String orgId, @RequestHeader("token") String token, HttpServletResponse response, HttpServletRequest request){
        logger.info("删除组织deleteOrganization: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",orgId:" + orgId);
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return 0;
        }
        return organizationService.deleteOrganization(orgId,token);
    }

}
