package com.ctey.ikunfansweb.Controller;

import com.ctey.ikunfanscommon.Entity.UserInfoData;
import com.ctey.ikunfanscommon.Req.UserLoginReq;
import com.ctey.ikunfanscommon.Resp.DefaultDataResp;
import com.ctey.ikunfanscommon.Resp.DefaultMsgResp;
import com.ctey.ikunfanscommon.Util.ModelTransformUtil;
import com.ctey.ikunfansweb.Service.UserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserWebService userWebService;

    @Autowired
    public UserController(UserWebService userWebService) {
        this.userWebService = userWebService;
    }

    @PostMapping("/login")
    public DefaultMsgResp postUserLogin(@RequestBody UserLoginReq req) {
        String token = userWebService.handleUserLogin(req);
        return ModelTransformUtil.getDefaultMsgInstance(token != null, "用户登录", token);
    }

    @PostMapping("/sign")
    public DefaultMsgResp postUserSign(@RequestBody UserLoginReq req) {
        String token = userWebService.handleUserSign(req);
        return ModelTransformUtil.getDefaultMsgInstance(token != null, "用户注册", token);
    }

    @GetMapping("/info")
    public DefaultDataResp getUserInfo(@RequestHeader("Authorization") String auth) {
        UserInfoData data = userWebService.collectUserInfo(auth);
        return ModelTransformUtil.getDefaultDataInstance("用户信息", data);
    }

}
