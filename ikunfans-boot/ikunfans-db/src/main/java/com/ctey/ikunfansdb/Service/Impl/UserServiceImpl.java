package com.ctey.ikunfansdb.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import com.ctey.ikunfanscommon.Req.UserLoginReq;
import com.ctey.ikunfansdb.Mapper.UserMapper;
import com.ctey.ikunfansdb.Model.User;
import com.ctey.ikunfansdb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, TransactionTemplate transactionTemplate) {
        this.userMapper = userMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public User login(UserLoginReq req) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, req.getAccount())
                .eq(User::getPassword, req.getPassword());
        return userMapper.selectOne(wrapper);
    }

    @Override
    @Transactional
    public User sign(User user) {
        transactionTemplate.execute(status -> userMapper.insert(user));
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, user.getAccount());
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User getInfo(String id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean hasOverlap(UserLoginReq req) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, req.getAccount());
        return userMapper.exists(wrapper);
    }

    @Override
    public boolean hasOverlapByAuth(TokenClaimEntity claim) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, claim.getId())
                .eq(User::getAccount, claim.getAccount());
        return userMapper.exists(wrapper);
    }

}
