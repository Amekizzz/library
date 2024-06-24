package com.yx.codeutil;


public class VerifyCode {

    private String code;//验证码字符串

    private byte[] imgBytes;//验证码图片字节数据

    private long expireTime;//存储验证码的过期时间

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}

