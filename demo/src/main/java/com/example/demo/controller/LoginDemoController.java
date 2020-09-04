package com.example.demo.controller;

import com.example.demo.dao.RequestLoginBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/index")
public class LoginDemoController {

    /**
     * index home
     *
     * @return
     */
    @RequestMapping(value = "/home")
    public String home() {
        return "index home";
    }

    /**
     * 得到1个参数
     *
     * @param name
     *            用户名
     * @return 返回结果
     */
    @GetMapping(value = "/{name}")
    public String index(@PathVariable String name) {
        return "oh you are " + name + "<br> nice to meet you";// \n不起作用了,那就直接用html中的标签吧
    }

    /**
     * 简单post请求
     *
     * @return
     */
    @RequestMapping(value = "/testPost", method = RequestMethod.POST)
    public String testPost() {
        System.out.println("hello  test post");
        return "ok";
    }

    /**
     * 同时得到两个参数
     *
     * @param name
     *            用户名
     * @param pwd
     *            密码
     * @return 返回结果
     */
    @GetMapping(value = "/login/{name}&{pwd}")
    public String login(@PathVariable String name, @PathVariable String pwd) {
        if (name.equals("admin") && pwd.equals("admin")) {
            return "hello welcome admin";
        } else {
            return "oh sorry user name or password is wrong";
        }
    }

    /**
     * 通过get请求去登陆
     *
     * @param name
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/loginByGet", method = RequestMethod.GET)
    public String loginByGet(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "pwd", required = false) String pwd) {

        return login4Return(name, pwd);
    }

    /**
     * 通过post请求去登陆
     *
     * @param name
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/loginByPost", method = RequestMethod.POST)
    public String loginByPost(@RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "pwd", required = true) String pwd) {
        System.out.println("hello post");
        return login4Return(name, pwd);
    }

    /**
     * 参数为一个bean对象.spring会自动为我们关联映射
     * @param loginBean
     * @return
     */
    @RequestMapping(value = "/loginByPost1", method = { RequestMethod.POST, RequestMethod.GET })
    public String loginByPost1(RequestLoginBean loginBean) {
        if (null != loginBean) {
            return login4Return(loginBean.getName(), loginBean.getPwd());
        } else {
            return "error";
        }
    }

    /**
     * 请求内容是一个json串,spring会自动把他和我们的参数bean对应起来,不过要加@RequestBody注解
     *
     * @return
     */
    @RequestMapping(value = "/loginByPost2", method = { RequestMethod.POST, RequestMethod.GET })
    public String loginByPost2(@RequestBody RequestLoginBean loginBean) {
        if (null != loginBean) {
            return login4Return(loginBean.getName(), loginBean.getPwd());
        } else {
            return "error";
        }
    }




    /**
     * 对登录做出响应处理的方法
     *
     * @param name
     *            用户名
     * @param pwd
     *            密码
     * @return 返回处理结果
     */
    private String login4Return(String name, String pwd) {
        String result;
       // BaseResponse response = new BaseResponse();
        if (name == null){
            result = "name不能为空";
            return result;
        }
        if (pwd == null){
            result = "pwd不能为空";
            return result;
        }
        if (name.equals("admin") && pwd.equals("admin")) {
            result = "hello welcome admin";

            //   response.setState(true);
        } else {
            result = "oh sorry user name or password is wrong";
            //  response.setState(false);
        }

        System.out.println("收到请求,请求结果:" + result);
      //  return new Gson().toJson(response);
        return result;
    }
}
