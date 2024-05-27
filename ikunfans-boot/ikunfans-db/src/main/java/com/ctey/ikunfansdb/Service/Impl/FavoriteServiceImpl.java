package com.ctey.ikunfansdb.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctey.ikunfansdb.Mapper.FavoriteMapper;
import com.ctey.ikunfansdb.Model.Favorite;
import com.ctey.ikunfansdb.Service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {
    private final FavoriteMapper favoriteMapper;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public FavoriteServiceImpl(FavoriteMapper favoriteMapper, TransactionTemplate transactionTemplate) {
        this.favoriteMapper = favoriteMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public List<Favorite> getFavoriteListById(String id) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, id);
        return favoriteMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void addFavorite(String userId, long subjectId) {
        transactionTemplate.execute(status -> {
            return favoriteMapper.insert(new Favorite(userId, subjectId, 0));
        });
    }

    @Override
    @Transactional
    public void removeFavorite(String userId, long subjectId) {
        transactionTemplate.execute(status -> {
            LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Favorite::getUserId, userId)
                    .eq(Favorite::getSubjectId, subjectId);
            return favoriteMapper.delete(wrapper);
        });
    }

    @Override
    public boolean getFavoriteStatus(String userId, long subjectId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getSubjectId, subjectId)
                .eq(Favorite::getUserId, userId);
        return favoriteMapper.exists(wrapper);
    }

}
