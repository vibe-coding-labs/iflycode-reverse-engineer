/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.StrUtil
 */
package com.aicode.service.response;

import cn.hutool.core.util.StrUtil;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.util.NewFileUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class BizResponse<T> {
    private T float;
    private String byte;
    public static final String RES_CODE_SUCCESS = ConditionalActionConfiguration.H("\u0016");
    private String enum;

    public void setMsg(String string) {
        String a = string;
        BizResponse a2 = this;
        a2.enum = a;
    }

    public T getObj() {
        BizResponse a;
        return a.float;
    }

    public void setObj(T t) {
        BizResponse<T> a = t;
        BizResponse a2 = this;
        a2.float = a;
    }

    public boolean isFail() {
        BizResponse a;
        if (!a.isSuccess()) {
            return true;
        }
        return false;
    }

    public void setResCode(String string) {
        String a = string;
        BizResponse a2 = this;
        a2.byte = a;
    }

    public String getResCode() {
        BizResponse a;
        return a.byte;
    }

    public String getMsg() {
        BizResponse a;
        return a.enum;
    }

    public boolean isSuccess() {
        BizResponse a;
        return StrUtil.equals((CharSequence)NewFileUtils.H("_"), (CharSequence)a.byte);
    }

    public BizResponse() {
        BizResponse a;
    }
}
