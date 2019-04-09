package com.xlauncher.web;

import com.xlauncher.entity.Notice;
import com.xlauncher.service.NoticeService;
import com.xlauncher.util.CheckToken;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;
    @Autowired
    CheckToken checkToken;
    private static Logger logger = Logger.getLogger(NoticeController.class);
    @ResponseBody
    @RequestMapping(value = "/page/{page}",method = RequestMethod.GET)
    public List<Notice> noticeList(@PathVariable("page")int page,@RequestHeader("token")String token){
        return noticeService.queryNotice(token,page);
    }

    @ResponseBody
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public int noticeCount(@RequestHeader("token")String token){
        return noticeService.countNotice(token);
    }

    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public int insertNotice(@RequestBody Notice notice, @RequestHeader("token")String token){
        logger.info("发布公告: 用户id：" + checkToken.checkToken(token).getUserId() + ", 用户姓名：" + checkToken.checkToken(token).getUserName());
        logger.info("公告信息notice:" + notice);
        return noticeService.insertNotice(notice,token);
    }

    @ResponseBody
    @RequestMapping(value = "/{noticeId}",method = RequestMethod.DELETE)
    public int deleteNotice(@PathVariable("noticeId")int noticeId, @RequestHeader("token")String token){
        return noticeService.deleteNotice(noticeId,token);
    }

    @ResponseBody
    @RequestMapping(value = "/{noticeId}",method = RequestMethod.GET)
    public Notice noticeDetail(@PathVariable("noticeId")int noticeId, @RequestHeader("token")String token){
        return noticeService.queryNoticeDetail(noticeId,token);
    }

    @ResponseBody
    @RequestMapping(value = "/overview",method = RequestMethod.GET)
    public List<Notice> overview(@RequestHeader("token")String token){
        return noticeService.overview(token);
    }


}
