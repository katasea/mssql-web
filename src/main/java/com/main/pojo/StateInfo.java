package com.main.pojo;

import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * 状态信息描述
 * 用于AJAX返回
 *
 * @author chenfuqiang
 */
public class StateInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1054440207147560676L;
    /**
     * 处理结果
     */
    private boolean flag = true;
    /**
     * 处理信息
     */
    private String msg;

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(Class c,String msg) {
        Logger.getLogger(c).error(msg);
        this.msg = msg;
    }

}
