package com.xlauncher.web;

import com.xlauncher.entity.Division;
import com.xlauncher.service.DivisionService;
import com.xlauncher.util.AuthUtil;
import com.xlauncher.util.CheckToken;
import com.xlauncher.util.userlogin.ActiveUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;


@Controller
@RequestMapping(value = "/division")
public class DivisionController {

    @Autowired
    DivisionService divisionService;
    @Autowired
    ActiveUtil activeUtil;
    @Autowired
    CheckToken checkToken;
    @Autowired
    AuthUtil authUtil;
    private static final String DESC = "行政区划";
    private static Logger logger = Logger.getLogger(DivisionController.class);
    @ResponseBody
    @RequestMapping(value = "/province", method = RequestMethod.GET)
    public List<Division> listDivisionP(){
        return divisionService.listDivisionP();
    }

    @ResponseBody
    @RequestMapping(value = "/city", method = RequestMethod.GET)
    public List<Division> listDivisionCi(){
        return divisionService.listDivisionCi();
    }


    @ResponseBody
    @RequestMapping(value = "/county", method = RequestMethod.GET)
    public List<Division> listDivisionC(){
        return divisionService.listDivisionC();
    }


    @ResponseBody
    @RequestMapping(value = "/town", method = RequestMethod.GET)
    public List<Division> listDivisionT(){
        return divisionService.listDivisionT();
    }


    @ResponseBody
    @RequestMapping(value = "/village", method = RequestMethod.GET)
    public List<Division> listDivisionV(){
        return divisionService.listDivisionV();
    }




    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Division> listDivision(@RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response){
        activeUtil.check(request, response);
        return divisionService.listDivisionB();
    }

    @ResponseBody
    @RequestMapping(value = "",method =RequestMethod.POST)
    public int insertDivision(@RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response, @RequestBody Division division){
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return 0;
        }
        logger.info("添加区划deleteDivision：用户id:" + checkToken.checkToken(token).getUserId() + ", 用户姓名:" + checkToken.checkToken(token).getUserName() + ",division:" + division);
        return divisionService.insertDivisionB(division);
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public int updateDivision(@RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response, @RequestBody Division division){
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return 0;
        }
        logger.info("修改区划deleteDivision：用户id:" + checkToken.checkToken(token).getUserId() + ", 用户姓名:" + checkToken.checkToken(token).getUserName() + ",division:" + division);
        return divisionService.updateDivisionB(division);
    }

    @ResponseBody
    @RequestMapping(value = "/{divisionId}",method = RequestMethod.DELETE)
    public int deleteDivision(@RequestHeader("token") String token, HttpServletRequest request, HttpServletResponse response, @PathVariable("divisionId") BigInteger divisionId){
        activeUtil.check(request, response);
        if (!authUtil.isAuth(DESC, token, request)) {
            response.setStatus(409);
            return 0;
        }
        logger.info("删除区划deleteDivision：用户id:" + checkToken.checkToken(token).getUserId() + ", 用户姓名:" + checkToken.checkToken(token).getUserName() + ",divisionId:" + divisionId);
        return divisionService.deleteDivisionB(divisionId);
    }

    /**
     * 行政区划编号查重
     * @param divisionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/divisionId",method = RequestMethod.POST)
    public Boolean countDivisionId(@RequestBody String divisionId){
        String[] sb = divisionId.split("=");
        BigInteger bi = new BigInteger(sb[1]);
        int status = divisionService.countDivisionId(bi);
        switch (status){
            case 1:return false;
            case 0:return true;
            default:break;
        }
        return false;
    }


}
