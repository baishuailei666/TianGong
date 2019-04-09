package com.xlauncher.entity;

public class ValidBean {
    private boolean valid;
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
