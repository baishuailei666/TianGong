package com.xlauncher.entity;

/**
 * 正阳科技返回认证实体类，含返回信息
 * @author 张霄龙
 * @since 2018-03-22
 */
public class ValidBean {
    /**
     * 调用接口创建告警事件是否成功
     */
    private boolean valid;

    /**
     * 返回的相关的信息描述
     */
    private ReturnMessage retMsg;

    {
        ReturnMessage rm = new ReturnMessage();
        this.setRetMsg(rm);
    }

    public boolean isValid() {
        return valid;
    }

    public ReturnMessage getRetMsg() {
        return retMsg;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setRetMsg(ReturnMessage retMsg) {
        this.retMsg = retMsg;
    }

    @Override
    public String toString() {
        return "ValidBean{" +
                "valid=" + valid +
                ", retMsg=" + retMsg +
                '}';
    }
}
