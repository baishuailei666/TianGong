package com.xlauncher.web;

import com.xlauncher.entity.AlertLog;
import com.xlauncher.entity.EventAlert;
import com.xlauncher.entity.OperationLog;
import com.xlauncher.service.AlertLogService;
import com.xlauncher.service.EventAlertService;
import com.xlauncher.service.OperationLogService;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;


/**
 * 导出Excel接口
 * @date 2018-05-16
 * @author 白帅雷
 */
@Controller
@RequestMapping(value = "/excel")
public class ExportExcelController {
    @Autowired
    OperationLogService operationLogService;
    @Autowired
    EventAlertService eventAlertService;
    @Autowired
    private AlertLogService alertLogService;

    /**
     * 组件告警日志导出
     *
     * @param request request
     * @param response response
     * @param token 用户令牌
     * @param alertPriority 告警日志级别
     * @param alertFileName 告警日志发生文件名
     * @param lowStartTime 告警日志开始时间
     * @param upStartTime 告警日志结束时间
     */
    @ResponseBody
    @RequestMapping(value = "/alertLog", method = RequestMethod.GET, produces = {"application/octet-stream;charset=UTF-8"})
    public void alertLogExportExcel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("alertPriority") String alertPriority
            , @RequestParam("alertFileName") String alertFileName) throws UnsupportedEncodingException {
        //文件名
        String fileName = "/var/log/tiangong/" + DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + " tiangong_componentAlertLog.xls";
        //sheet名
        String sheetName = "组件告警日志详情";
        //标题
        String[] title = new String[]{"ID", "告警级别", "告警产生的时间", "告警产生的时间戳", "告警产生的线程", "告警产生的代码行数", "告警产生的文件名", "告警产生的类名", "告警产生的方法名", "告警信息", "告警类型"};
        //获取数据
        List<AlertLog> alertLogList = this.alertLogService.getAlertLogForExcel(upStartTime,lowStartTime,alertPriority, alertFileName,token);
        String[][] values = new String[alertLogList.size()][];
        for (int i = 0; i < alertLogList.size(); i++) {
            values[i] = new String[title.length];
            // 将对象内容转换成string
            AlertLog alertLog = alertLogList.get(i);
            values[i][0] = alertLog.getId() + "";
            values[i][1] = alertLog.getAlertPriority();
            values[i][2] = alertLog.getAlertTime();
            values[i][3] = String.valueOf(alertLog.getAlertTimeSpan());
            values[i][4] = alertLog.getAlertThread();
            values[i][5] = alertLog.getAlertLineNum();
            values[i][6] = alertLog.getAlertFileName();
            values[i][7] = alertLog.getAlertClassName();
            values[i][8] = alertLog.getAlertMethodName();
            values[i][9] = alertLog.getAlertMessage();
            values[i][10] = alertLog.getAlertType();
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);
        downSetResponse(wb, response, fileName);
    }

    /**
     * 未复核告警事件导出
     *
     * @param request request
     * @param response response
     * @param token 用户令牌
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param typeDescription 事件类型
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     */
    @ResponseBody
    @RequestMapping(value = "/notCheck", method = RequestMethod.GET, produces = {"application/octet-stream;charset=UTF-8"})
    public void notCheckEventAlertExportExcel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("typeDescription") String typeDescription
            , @RequestParam("channelLocation") String channelLocation, @RequestParam("channelHandler") String channelHandler
            , @RequestParam("channelOrg") String channelOrg) throws Exception {
        //文件名
        String fileName = "/var/log/tiangong/" + DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + " tiangong_notCheckEventAlert.xls";
        //sheet名
        String sheetName = "未复核告警事件详情";
        //标题
        String[] title = new String[]{"事件编号", "通道编号", "产生时间", "事件类型", "事件类型的状态", "错误类型的纠正类型", "事件存储资源", "通道管理员", "通道管理员电话", "通道所属位置", "通道所属经度", "通道所属维度", "通道所属组织", "事件复核时间", "事件复核人"};
        //获取数据
        List<EventAlert> eventAlertList = this.eventAlertService.listNotCheckAlertForExcel(upStartTime, lowStartTime, typeDescription, channelLocation, channelHandler, channelOrg);
        String[][] values = new String[eventAlertList.size()][];
        for (int i = 0; i < eventAlertList.size(); i++) {
            values[i] = new String[title.length];
            // 将对象内容转换成string
            EventAlert alert = eventAlertList.get(i);
            values[i][0] = alert.getEventId() + "";
            values[i][1] = alert.getChannelId() + "";
            values[i][2] = alert.getEventStartTime();
            values[i][3] = alert.getTypeDescription();
            values[i][4] = alert.getEventStatus();
            values[i][5] = alert.getTypeRectify();
            values[i][6] = alert.getEventSource();
            values[i][7] = alert.getChannelHandler();
            values[i][8] = alert.getChannelHandlerPhone();
            values[i][9] = alert.getChannelLocation();
            values[i][10] = alert.getChannelLongitude();
            values[i][11] = alert.getChannelLatitude();
            values[i][12] = alert.getChannelOrg();
            values[i][13] = alert.getEventReviewer();
            values[i][14] = alert.getEventReviewer();
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);
        downSetResponse(wb, response, fileName);
    }

    /**
     * 已复核告警事件导出
     *
     * @param request request
     * @param response response
     * @param token 用户令牌
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param upCheckStartTime 复核的起始时间
     * @param lowCheckStartTime 复核的结束时间
     * @param typeDescription 事件类型
     * @param typeStatus 告警状态
     * @param channelLocation 通道位置
     * @param channelHandler  事件负责人
     * @param channelOrg   通道所属组织
     * @param eventReviewer 事件复核人
     * @param typeRectify 事件复核类型
     */
    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = {"application/octet-stream;charset=UTF-8"})
    public void checkEventAlertExportExcel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("upCheckStartTime")String upCheckStartTime
            , @RequestParam("lowCheckStartTime")String lowCheckStartTime, @RequestParam("typeDescription") String typeDescription
            , @RequestParam("channelLocation") String channelLocation, @RequestParam("channelHandler") String channelHandler
            , @RequestParam("channelOrg") String channelOrg, @RequestParam("eventReviewer") String eventReviewer
            , @RequestParam("typeRectify") String typeRectify, @RequestParam("typeStatus") String typeStatus) throws UnsupportedEncodingException {
        //文件名
        String fileName = "/var/log/tiangong/" + DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + " tiangong_checkEventAlert.xls";
        //sheet名
        String sheetName = "已复核告警事件详情";
        //标题
        String[] title = new String[]{"事件编号", "通道编号", "产生时间", "事件类型", "事件类型的状态", "错误类型的纠正类型", "事件存储资源", "通道管理员", "通道管理员电话", "通道所属位置", "通道所属经度", "通道所属维度", "通道所属组织", "事件复核时间", "事件复核人"};
        //获取数据
        List<EventAlert> eventAlertList = this.eventAlertService.listCheckAlertForExcel(upStartTime, lowStartTime, upCheckStartTime, lowCheckStartTime, typeDescription, channelLocation, channelHandler, channelOrg, eventReviewer, typeRectify, typeStatus);
        String[][] values = new String[eventAlertList.size()][];
        for (int i = 0; i < eventAlertList.size(); i++) {
            values[i] = new String[title.length];
            // 将对象内容转换成string
            EventAlert alert = eventAlertList.get(i);
            values[i][0] = alert.getEventId() + "";
            values[i][1] = alert.getChannelId();
            values[i][2] = alert.getEventStartTime();
            values[i][3] = alert.getTypeDescription();
            values[i][4] = alert.getEventStatus();
            values[i][5] = alert.getTypeRectify();
            values[i][6] = alert.getEventSource();
            values[i][7] = alert.getChannelHandler();
            values[i][8] = alert.getChannelHandlerPhone();
            values[i][9] = alert.getChannelLocation();
            values[i][10] = alert.getChannelLongitude();
            values[i][11] = alert.getChannelLatitude();
            values[i][12] = alert.getChannelOrg();
            values[i][13] = alert.getEventEndTime();
            values[i][14] = alert.getEventReviewer();
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);
        downSetResponse(wb, response, fileName);
    }

    /**
     * 运维操作日志导出
     *
     * @param request request
     * @param response response
     * @param token 用户令牌
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作子模块
     * @param opCategory 操作类别
     * @param opSystemModule 操作子系统模块
     */
    @ResponseBody
    @RequestMapping(value = "/operation", method = RequestMethod.GET, produces = {"application/octet-stream;charset=UTF-8"})
    public void operationExportExcel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("opPerson")String opPerson
            , @RequestParam("opType")String opType, @RequestParam("opModule")String opModule
            , @RequestParam("opCategory")String opCategory, @RequestParam("opSystemModule")String opSystemModule) throws UnsupportedEncodingException {
        //文件名
        String fileName = "/var/log/tiangong/" + DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + " tiangong_operationLog.xls";
        //sheet名
        String sheetName = "操作日志详情";
        //标题
        String[] title = new String[]{"ID", "操作人员", "操作时间", "操作类型", "操作描述", "操作模块", "操作类别"};
        //获取数据
        List<OperationLog> logList = this.operationLogService.listLogForExcel(upStartTime, lowStartTime, opPerson, opType, opModule, token, opCategory, opSystemModule);
        String[][] values = new String[logList.size()][];
        for (int i = 0; i < logList.size(); i++) {
            values[i] = new String[title.length];
            // 将对象内容转换成string
            OperationLog log = logList.get(i);
            values[i][0] = log.getId() + "";
            values[i][1] = log.getOperationPerson();
            values[i][2] = log.getOperationTime();
            values[i][3] = log.getOperationType();
            values[i][4] = log.getOperationDescription();
            values[i][5] = log.getOperationModule();
            values[i][6] = log.getOperationCategory();
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);
        downSetResponse(wb, response, fileName);
    }

    /**
     * 运营操作日志导出
     *
     * @param request request
     * @param response response
     * @param token 用户令牌
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作子模块
     * @param opCategory 操作类别
     * @param opSystemModule 操作子系统模块
     */
    @ResponseBody
    @RequestMapping(value = "/operating", method = RequestMethod.GET, produces = {"application/octet-stream;charset=UTF-8"})
    public void operatingExportExcel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("opPerson")String opPerson
            , @RequestParam("opType")String opType, @RequestParam("opModule")String opModule
            , @RequestParam("opCategory")String opCategory, @RequestParam("opSystemModule")String opSystemModule) throws UnsupportedEncodingException {
        //文件名
        String fileName = "/var/log/tiangong/" + DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + " tiangong_operatingLog.xls";
        //sheet名
        String sheetName = "操作日志详情";
        //标题
        String[] title = new String[]{"ID", "操作人员", "操作时间", "操作类型", "操作描述", "操作模块", "操作类别"};
        //获取数据
        List<OperationLog> operatingLogList = this.operationLogService.listLogForExcel(upStartTime, lowStartTime, opPerson, opType, opModule, token, opCategory, opSystemModule);
        String[][] operatingValues = new String[operatingLogList.size()][];
        for (int j = 0; j < operatingLogList.size(); j++) {
            operatingValues[j] = new String[title.length];
            // 将对象内容转换成string
            OperationLog operatingLog = operatingLogList.get(j);
            operatingValues[j][0] = operatingLog.getId() + "";
            operatingValues[j][1] = operatingLog.getOperationPerson();
            operatingValues[j][2] = operatingLog.getOperationTime();
            operatingValues[j][3] = operatingLog.getOperationType();
            operatingValues[j][4] = operatingLog.getOperationDescription();
            operatingValues[j][5] = operatingLog.getOperationModule();
            operatingValues[j][6] = operatingLog.getOperationCategory() + "";
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, operatingValues, null);
        downSetResponse(wb, response, fileName);
    }

    /**
     * 事件操作日志导出
     *
     * @param request request
     * @param response response
     * @param token 用户令牌
     * @param upStartTime 查询的起始时间
     * @param lowStartTime 查询的结束时间
     * @param opPerson 操作用户
     * @param opType 操作类型
     * @param opModule 操作子模块
     * @param opCategory 操作类别
     * @param opSystemModule 操作子系统模块
     */
    @ResponseBody
    @RequestMapping(value = "/event", method = RequestMethod.GET, produces = {"application/octet-stream;charset=UTF-8"})
    public void eventExportExcel(HttpServletRequest request, HttpServletResponse response
            , @RequestHeader("token") String token, @RequestParam("upStartTime")String upStartTime
            , @RequestParam("lowStartTime")String lowStartTime, @RequestParam("opPerson")String opPerson
            , @RequestParam("opType")String opType, @RequestParam("opModule")String opModule
            , @RequestParam("opCategory")String opCategory, @RequestParam("opSystemModule")String opSystemModule) throws UnsupportedEncodingException {
        //文件名
        String fileName = "/var/log/tiangong/" + DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + " tiangong_eventLog.xls";
        //sheet名
        String sheetName = "操作日志详情";
        //标题
        String[] title = new String[]{"ID", "操作人员", "操作时间", "操作类型", "操作描述", "操作模块", "操作类别"};
        //获取数据
        List<OperationLog> excelLogList = this.operationLogService.listLogForExcel(upStartTime, lowStartTime, opPerson, opType, opModule, token, opCategory, opSystemModule);
        String[][] eventValues = new String[excelLogList.size()][];
        for (int k = 0; k < excelLogList.size(); k++) {
            eventValues[k] = new String[title.length];
            // 将对象内容转换成string
            OperationLog operatingLog = excelLogList.get(k);
            eventValues[k][0] = operatingLog.getId() + "";
            eventValues[k][1] = operatingLog.getOperationPerson();
            eventValues[k][2] = operatingLog.getOperationTime();
            eventValues[k][3] = operatingLog.getOperationType()+ "";
            eventValues[k][4] = operatingLog.getOperationDescription();
            eventValues[k][5] = operatingLog.getOperationModule();
            eventValues[k][6] = operatingLog.getOperationCategory();
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, eventValues, null);
        downSetResponse(wb, response, fileName);
    }


    /**
     * 提供下载的接口
     *
     * @param wb wb
     * @param response 设置返回的类型
     * @param fileName 文件名
     * @throws UnsupportedEncodingException
     */
    private void downSetResponse(HSSFWorkbook wb, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                .concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            if (out != null) {
                bos = new BufferedOutputStream(out);
            }
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                if (bos != null) {
                    bos.write(buff, 0, bytesRead);
                }
            }
        } catch (final IOException e) {
            try {
                throw e;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送响应流方法
     *
     * @param response response
     * @param fileName 文件名
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "ISO8859-1"))));
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            System.out.println("输出流错误");
            ex.printStackTrace();
        }
    }

}
