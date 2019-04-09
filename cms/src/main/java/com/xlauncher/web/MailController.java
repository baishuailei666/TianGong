package com.xlauncher.web;

import com.xlauncher.entity.MailR;
import com.xlauncher.entity.MailS;
import com.xlauncher.service.MailService;
import com.xlauncher.util.CheckToken;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mail")
public class MailController {

    @Autowired
    MailService mailService;
    @Autowired
    CheckToken checkToken;
    private static Logger logger = Logger.getLogger(MailController.class);
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public int insertMail(@RequestBody Map<String,Object> mailInfo, @RequestHeader ("token") String token){
        logger.info("发送邮箱: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        logger.info("邮箱信息mailInfo:" + mailInfo);
        return mailService.insertMail(mailInfo,token);
    }

    @ResponseBody
    @RequestMapping(value = "/receiver/{mailId}",method = RequestMethod.DELETE)
    public int deleteFromReceiver(@PathVariable("mailId")int mailId, @RequestHeader ("token") String token){
        return mailService.deleteMailR(mailId,token);
    }

    @ResponseBody
    @RequestMapping(value = "/sender/{mailId}",method = RequestMethod.DELETE)
    public int deleteFromSender(@PathVariable("mailId")int mailId, @RequestHeader ("token") String token){
        return mailService.deleteMailS(mailId,token);
    }

    @ResponseBody
    @RequestMapping(value = "/receiver/{mailId}",method = RequestMethod.GET)
    public MailR queryDetailReceiver(@PathVariable("mailId")int mailId, @RequestHeader ("token") String token){
        return mailService.queryMailR(mailId,token);
    }

    @ResponseBody
    @RequestMapping(value = "/sender/{mailId}",method = RequestMethod.GET)
    public MailS queryDetailSender(@PathVariable("mailId")int mailId, @RequestHeader ("token") String token){
        return mailService.queryMailS(mailId,token);
    }

    @ResponseBody
    @RequestMapping(value = "/receiver/page/{page}",method = RequestMethod.GET)
    public List<MailR> queryReceiverList(@PathVariable("page")int page,@RequestHeader ("token") String token){
        return mailService.queryMailR(token,page);
    }

    @ResponseBody
    @RequestMapping(value = "/sender/page/{page}",method = RequestMethod.GET)
    public List<MailS> querySenderList(@PathVariable("page")int page,@RequestHeader ("token") String token){
        return mailService.queryMailS(token,page);
    }

    @ResponseBody
    @RequestMapping(value = "/receiver/count",method = RequestMethod.GET)
    public int countReceiverList(@RequestHeader ("token") String token){
        return mailService.countMailR(token);
    }

    @ResponseBody
    @RequestMapping(value = "/sender/count",method = RequestMethod.GET)
    public int countSenderList(@RequestHeader ("token") String token){
        return mailService.countMailS(token);
    }

    @ResponseBody
    @RequestMapping(value = "/receiver/unread",method = RequestMethod.GET)
    public int countUnread(@RequestHeader("token") String token){
        return mailService.unread(token);
    }

    @ResponseBody
    @RequestMapping(value = "/receiver/overview",method = RequestMethod.GET)
    public List<MailR> mailOverView(@RequestHeader("token")String token){
        return mailService.overview(token);
    }

}
