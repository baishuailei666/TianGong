package com.xlauncher.web;

import com.xlauncher.entity.Component;
import com.xlauncher.service.ComponentService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.HeartBeatUtil;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件Web层
 * @date 2018-05-10
 * @author 白帅雷
 */
@Controller
@RequestMapping(value = "/component")
public class ComponentController {
    @Autowired
    private ComponentService componentService;
    @Autowired
    private HeartBeatUtil heartBeatUtil;
    private static Logger logger = Logger.getLogger(ComponentController.class);
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    CheckToken checkToken;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "组件配置管理";
    /**
     * 添加组件
     *
     * @param component 添加的通道信息
     * @return 添加组件结果，成功返回1；失败返回0
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<String, Object> addComponent(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody @Param("component") Component component) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("添加组件ComponentController addComponent: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        int retAdd =this.componentService.addComponent(component, token);
        if (retAdd == 0) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retAdd);
        return map;
    }

    /**
     * 删除组件
     *
     * @param id 组件编号
     * @return 删除组件结果，成功返回1；失败返回0
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteComponent(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("id") int id) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("删除组件ComponentController deleteComponent: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",deleteId:" + id);
        int retAdd =this.componentService.deleteComponent(id, token);
        if (retAdd == 0) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retAdd);
        return map;
    }

    /**
     * 更新组件
     *
     * @param component 组件信息
     * @return 更新组件结果，成功返回1；失败返回0
     */
    @ResponseBody
    @RequestMapping( method = RequestMethod.PUT)
    public Map<String, Object> updateComponent(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestBody @Param("component") Component component) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("更新组件ComponentController updateComponent: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",component:" + component);
        int retAdd =this.componentService.updateComponent(component, token);
        if (retAdd == 0) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("status", retAdd);
        return map;
    }

    /**
     * 分页获取所有组件
     *
     * @param componentName 组件名
     * @param number 页码数
     * @return list列表所有组件
     */
    @ResponseBody
    @RequestMapping(value = "/page/{number}", method = RequestMethod.GET)
    public Map<String, Object> listAllComponentByNum(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("number") int number, @RequestParam("componentName") String componentName) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.debug("分页获取组件ComponentController listAllComponentByNum: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        List<Component> listComponentByNum = this.componentService.listAllComponentByNum((number-1)*10, componentName, token);
        if (listComponentByNum == null) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("listComponentByNum", listComponentByNum);
        return map;
    }

    /**
     * 获取行数用户分页展示
     * @param componentName 组件名
     * @return 行数
     */
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Map<String, Object> countPage(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("componentName") String componentName) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        int count = this.componentService.countPage(componentName);
        Map<String, Object> map = new HashMap<>(1);
        map.put("count", count);
        return map;
    }

    /**
     * 获取所有组件用于内部同步服务
     *
     * @return list列表所有组件
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Map<String, Object> listComponent(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Component> listComponent = this.componentService.listComponent();
        if (listComponent == null) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("listComponent", listComponent);
        return map;
    }

    /**
     * 根据组件编号查询组件
     * @param id 组件编号
     * @return 组件信息
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Map<String, Object> getComponentById(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @PathVariable("id") int id) throws SQLException {
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            Map<String, Object> map = new HashMap<>(1);
            response.setStatus(409);
            return map;
        }
        logger.info("编号查询组件ComponentController getComponentById: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName() + ",componentId:" + id);
        Component componentById = this.componentService.getComponentById(id, token);
        if (componentById == null) {
            response.setStatus(415);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("componentById", componentById);
        return map;
    }

    /**
     * 组件监控，获取各个组件的状态信息
     * @return 组件信息
     */
    @ResponseBody
    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public List<Object> getComponentStatus(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token) throws SQLException {
        logger.info("组件监控ComponentController getComponentStatus: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        activeUtil.check(request, response);
        return this.heartBeatUtil.heartBeat();
    }
}
