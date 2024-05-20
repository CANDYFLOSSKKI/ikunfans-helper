package com.ctey.ikunfansdb.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import com.ctey.ikunfanscommon.Req.UserLoginReq;
import com.ctey.ikunfansdb.Model.User;

public interface UserService extends IService<User> {
    public User login(UserLoginReq req);
    public User sign(User user);
    public User getInfo(String id);
    public boolean hasOverlap(UserLoginReq req);
    public boolean hasOverlapByAuth(TokenClaimEntity claim);

}
