package com.aicode.template.request;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.aicode.template.context.domain.Method;
import java.util.Date;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/request/MethodRequestResult.class */
public class MethodRequestResult {
    private String requestId;
    private String methodId;
    private Method method;
    private Date beginTime;
    private Date endTime;
    private Integer requestCount = 1;
    private boolean isReturn = false;

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isReturn() {
        return this.isReturn;
    }

    public String getMethodId() {
        return this.methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public void setReturn(boolean aReturn) {
        this.isReturn = aReturn;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Integer getRequestCount() {
        return this.requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public Date getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDiff() {
        if (this.beginTime == null || this.endTime == null) {
            return 0L;
        }
        return Long.valueOf(DateUtil.between(this.beginTime, this.endTime, DateUnit.SECOND));
    }
}
