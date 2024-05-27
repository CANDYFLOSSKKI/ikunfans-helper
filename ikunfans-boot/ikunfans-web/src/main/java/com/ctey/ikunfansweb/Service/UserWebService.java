package com.ctey.ikunfansweb.Service;

import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import com.ctey.ikunfanscommon.Entity.UserInfoData;
import com.ctey.ikunfanscommon.Req.UserLoginReq;
import com.ctey.ikunfanscommon.Util.AccountTokenUtil;
import com.ctey.ikunfansdb.Model.User;
import com.ctey.ikunfansdb.Service.UserService;
import com.ctey.ikunfansdb.Util.DbModelTransformUtil;
import com.ctey.ikunfansweb.Config.DockingInterfaceConfig.Docking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserWebService {
    private final TokenWebService tokenWebService;
    private final UserService userService;

    @Autowired
    public UserWebService(TokenWebService tokenWebService, UserService userService) {
        this.tokenWebService = tokenWebService;
        this.userService = userService;
    }

    @Docking
    public String handleUserLogin(UserLoginReq req) {
        User user = userService.login(req);
        if (user == null) { return null; }
        return AccountTokenUtil.createAccountToken(DbModelTransformUtil.userToTokenClaim(user));
    }

    @Docking
    public String handleUserSign(UserLoginReq req) {
        if (userService.hasOverlap(req)) { return null; }
        User user = userService.sign(DbModelTransformUtil.userLoginReqToUser(req));
        return AccountTokenUtil.createAccountToken(DbModelTransformUtil.userToTokenClaim(user));
    }

    @Docking
    public UserInfoData collectUserInfo(String auth) {
        TokenClaimEntity claim = tokenWebService.handleAccountAuth(auth);
        if (claim == null || !userService.hasOverlapByAuth(claim)) { return null; }
        return DbModelTransformUtil.userToUserInfo(userService.getInfo(claim.getId()));
    }

}
