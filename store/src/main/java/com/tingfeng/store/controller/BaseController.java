package com.tingfeng.store.controller;

import com.tingfeng.store.controller.ex.*;
import com.tingfeng.store.service.ex.*;
import com.tingfeng.store.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;


/**
 * 处理控制层异常
 */
public class BaseController {
    public static final int OK = 200;

    //请求处理方法，方法的返回值就是需要传给前端的数据
    //自动将异常对象传递给此方法的参数列表上

    //统一处理抛出的异常
    //项目发生了异常，被统一拦截到这儿，这个方法此时就冲当的是请求处理方法，方法的返回值直接给到前端
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);

        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占用!");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户数据不存在!");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户名密码错误!");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户收货地址超出上限!");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("用户收货地址数据不存在!");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("收货地址数据非法访问!");
        } else if (e instanceof ProductNotFindException) {
            result.setState(4006);
            result.setMessage("商品数据不存在!");
        } else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车数据不存在!");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("在用户注册过程中发生了未知异常!");
        } else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("在删除数据时发生了未知异常!");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("在更新数据过程中发生了未知异常!");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }

    protected final Integer getuidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
