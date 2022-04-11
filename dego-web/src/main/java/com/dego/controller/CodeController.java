package com.dego.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码控制器
 *
 * @Description
 * @Author janus
 * @Date 2021/12/28 16:17
 * @Version 1.0
 */
@Controller
@Slf4j
@Api(tags = "验证码控制器")
public class CodeController {


    @Resource
    private Producer captchaProducer = null;

    @Resource
    private DefaultKaptcha captchaProducerMath;


    /**
     * 返回生成字母的验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("返回生成字母的验证码")
    @RequestMapping(value = "/kaptcha", method = RequestMethod.GET)
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            //设置页面不缓存
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            // 生成验证码编码
            String capText = captchaProducer.createText();
            log.info("返回生成字母的验证码:{}", capText);
            //获取session，并将验证码编码存放到session中
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
            //向客户端写出
            // 将验证码编码生成图片
            BufferedImage bi = captchaProducer.createImage(capText);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回生成数字计算的验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("返回生成数字计算的验证码")
    @RequestMapping(value = "/kaptcha1", method = RequestMethod.GET)
    public void getKaptchaImage1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            //设置页面不缓存
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            // 生成验证码编码
            String capText = captchaProducerMath.createText();

            log.info("返回生成数字计算的验证码:{}", capText);
            //获取session，并将验证码编码存放到session中
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
            //向客户端写出
            // 将验证码编码生成图片
            BufferedImage bi = captchaProducer.createImage(capText);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 线段干扰的验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("线段干扰的验证码")
    @RequestMapping(value = "/kaptcha2", method = RequestMethod.GET)
    public void getKaptchaImage2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            //设置页面不缓存
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            //定义图形验证码的长和宽
            LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);


            //图形验证码写出，可以写出到文件，也可以写出到流
//            captcha.write("d:/circle.png");
            //验证图形验证码的有效性，返回boolean值
//            captcha.verify("1234");
            String capText = captcha.getCode();

            log.info("返回生成数字计算的验证码:{}", capText);
            //获取session，并将验证码编码存放到session中
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
            // 将验证码编码生成图片
            out = response.getOutputStream();
            captcha.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 圆圈干扰验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("圆圈干扰验证码")
    @RequestMapping(value = "/kaptcha3", method = RequestMethod.GET)
    public void getKaptchaImage3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            //设置页面不缓存
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");


            //定义图形验证码的长、宽、验证码字符数、干扰元素个数
            CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
            //图形验证码写出，可以写出到文件，也可以写出到流
//            captcha.write("d:/circle.png");
            //验证图形验证码的有效性，返回boolean值
//            captcha.verify("1234");
            String capText = captcha.getCode();

            log.info("返回生成数字计算的验证码:{}", capText);
            //获取session，并将验证码编码存放到session中
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
            // 将验证码编码生成图片
            out = response.getOutputStream();
            captcha.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 扭曲干扰验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("扭曲干扰验证码")
    @RequestMapping(value = "/kaptcha4", method = RequestMethod.GET)
    public void getKaptchaImage4(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            //设置页面不缓存
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            //定义图形验证码的长、宽、验证码字符数、干扰线宽度
            ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);

            //图形验证码写出，可以写出到文件，也可以写出到流
//            captcha.write("d:/circle.png");
            //验证图形验证码的有效性，返回boolean值
//            captcha.verify("1234");
            String capText = captcha.getCode();

            log.info("返回生成数字计算的验证码:{}", capText);
            //获取session，并将验证码编码存放到session中
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
            // 将验证码编码生成图片
            out = response.getOutputStream();
            captcha.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
