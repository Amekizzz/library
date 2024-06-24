package com.yx.codeutil;

import java.io.IOException;
import java.io.OutputStream;
//生成验证码
public interface IVerifyCodeGen {
    //生成验证码
    String generate(int width, int height, OutputStream os) throws IOException;
    //生成验证码对象，包含验证码字符串和验证码图片
    VerifyCode generate(int width, int height) throws IOException;
}
