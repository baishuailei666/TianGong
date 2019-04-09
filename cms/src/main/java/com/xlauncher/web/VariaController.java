package com.xlauncher.web;

import com.xlauncher.util.Init;
import com.xlauncher.util.LinuxSystemTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class VariaController {

    @Autowired
    Init init;

    @ResponseBody
    @RequestMapping(value = "/systeminfo",method = RequestMethod.GET)
    public Map<String,Object> systemInfo(){
        Map<String,Object> map = new HashMap<>();
        DecimalFormat df = new DecimalFormat("0.0000");
        int[] memInfo = new int[0];
        try {
            memInfo = LinuxSystemTool.getMemInfo();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put("memRate", df.format((double)(memInfo[0]-memInfo[1])/memInfo[0]));
        try {
            map.put("cpuRate" ,LinuxSystemTool.getCpuInfo());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            List<String> list = LinuxSystemTool.execCmd("df -h /", null);
            map.put("driTotal",list.get(1));
            map.put("driUsed",list.get(2));
            map.put("driAva",list.get(3));
            map.put("driRate",list.get(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public List initTime(){
        Init init = new Init();
        try {
            return init.runTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
