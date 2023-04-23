package com.tingfeng.store.controller;

import com.mysql.cj.x.protobuf.Mysqlx;
import com.tingfeng.store.controller.ex.*;
import com.tingfeng.store.entity.User;
import com.tingfeng.store.service.IUserService;
import com.tingfeng.store.service.ex.InsertException;
import com.tingfeng.store.service.ex.UsernameDuplicatedException;
import com.tingfeng.store.utils.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    /*设置文件上传的最大值*/
    private static final int AVATAR_MAX_SIZE=10*1024*1024;

    //限制文件上传的类型
    private static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    @Resource
    private IUserService userService;

    /*@PostMapping("/reg")
    public JsonResult<Void> result(User user){

        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("注册成功！");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("在用户注册过程中发生了未知异常!");
        }
        return result;
    }*/

    @PostMapping("/reg")
    public JsonResult<Void> result(User user){

        //创建响应结果对象
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @PostMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username, password);
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getByUid(getuidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }

    /**
     * MultipartFile接口是SpringMVC提供一个接口，这个接口为我们包装了获取文件类型的数据(任何类型的file 都可以接收)，
     * SpringBoot 它有整合了SpringMVC，只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，
     * 然后SpringBoot自动将传递给服务的文件数据赋值赋值给这个参数
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file){
        if (file.isEmpty()) {
            throw  new FileEmptyException("文件为空！");
        }
        if (file.getSize()>AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        //获取文件的类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持！");
        }
        //上传文件
        String parent = session.getServletContext().getRealPath("upload");

        File dir = new File(parent);
        if (!dir.exists()) { //检测目录是否存在
            dir.mkdirs();    //创建
        }
        //获取文件名称，UUID工具生成一个新的字符串来代替名称
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase()+suffix;
        File dest = new File(dir, filename); //空文件
        //参数file中的数据写入到空文件中
        try {
            file.transferTo(dest);  //将file中的数据写入到dest中
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        }
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像的路径/upload/test.png
        String avatar = "/upload/"+filename;
        userService.changeAvatar(uid,avatar,username);
        return new JsonResult<>(OK,avatar);
    }
}
