package com.yx.codeutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

//验证码实现类
public class SimpleCharVerifyCodeGenImpl implements IVerifyCodeGen {
    //日志记录器 logger
    private static final Logger logger = LoggerFactory.getLogger(SimpleCharVerifyCodeGenImpl.class);
    //字体类型数组 FONT_TYPES
    private static final String[] FONT_TYPES = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
    //验证码长度为4
    private static final int VALICATE_CODE_LENGTH = 4;

    //设置背景颜色及大小，干扰线
    private static void fillBackground(Graphics graphics, int width, int height) {
        // 填充背景为白色
        graphics.setColor(Color.WHITE);
        //设置矩形坐标x y 为0
        graphics.fillRect(0, 0, width, height);

        // 加入8条随机颜色干扰线条
        for (int i = 0; i < 8; i++) {
            //设置随机颜色算法参数
            graphics.setColor(RandomUtils.randomColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.drawLine(x, y, x1, y1);
        }
    }

    //生成验证码图片部分
    @Override
    public String generate(int width, int height, OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        fillBackground(graphics, width, height);//设置背景和干扰线
        String randomStr = RandomUtils.randomString(VALICATE_CODE_LENGTH);//生成一个随机字符串
        createCharacter(graphics, randomStr);//字符串绘制到图像上
        graphics.dispose();
        ImageIO.write(image, "JPEG", os);//图像写入输出流并设置JPEG格式
        return randomStr;
    }

    //验证码生成
    @Override
    public VerifyCode generate(int width, int height) {
        VerifyCode verifyCode = null;
        try (
                //将流的初始化放到这里就不需要手动关闭流
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            String code = generate(width, height, baos);
            verifyCode = new VerifyCode();
            verifyCode.setCode(code);
            verifyCode.setImgBytes(baos.toByteArray());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            verifyCode = null;
        }
        return verifyCode;
    }

    //将随机字符串逐字符绘制到图像上
    private void createCharacter(Graphics g, String randomStr) {
        char[] charArray = randomStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            //设置RGB颜色算法参数
            g.setColor(new Color(50 + RandomUtils.nextInt(100),
                    50 + RandomUtils.nextInt(100), 50 + RandomUtils.nextInt(100)));
            //设置字体大小，类型
            g.setFont(new Font(FONT_TYPES[RandomUtils.nextInt(FONT_TYPES.length)], Font.BOLD, 26));
            //设置x y 坐标，调整字符位置
            g.drawString(String.valueOf(charArray[i]), 15 * i + 5, 19 + RandomUtils.nextInt(8));
        }
    }
}


