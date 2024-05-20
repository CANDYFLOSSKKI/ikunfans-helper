package com.ctey.ikunfansdb.Service.Impl;

import com.baomidou.mybatisplus.core.batch.MybatisBatch;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctey.ikunfansdb.Mapper.FavoriteMapper;
import com.ctey.ikunfansdb.Model.Favorite;
import com.ctey.ikunfansdb.Service.FavoriteService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {
    private final FavoriteMapper favoriteMapper;
    private final SqlSessionFactory sqlSessionFactory;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public FavoriteServiceImpl(FavoriteMapper favoriteMapper, SqlSessionFactory sqlSessionFactory, TransactionTemplate transactionTemplate) {
        this.favoriteMapper = favoriteMapper;
        this.sqlSessionFactory = sqlSessionFactory;
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
    public void storeFavoriteList(List<Favorite> favoriteList) {
        transactionTemplate.execute(status -> {
            MybatisBatch<Favorite> favoriteBatchInsert = new MybatisBatch<>(sqlSessionFactory, favoriteList);
            MybatisBatch.Method<Favorite> method = new MybatisBatch.Method<>(FavoriteMapper.class);
            return favoriteBatchInsert.execute(method.insert());
        });
    }

    @Override
    @Transactional
    public void updateFavoriteList(List<Favorite> favoriteList) {
        transactionTemplate.execute(status -> {
            MybatisBatch<Favorite> favoriteBatchUpdate = new MybatisBatch<>(sqlSessionFactory, favoriteList);
            MybatisBatch.Method<Favorite> method = new MybatisBatch.Method<>(FavoriteMapper.class);
            return favoriteBatchUpdate.execute(method.update(favorite -> {
                LambdaUpdateWrapper<Favorite> wrapper = new LambdaUpdateWrapper<>();
                wrapper.eq(Favorite::getUserId, favorite.getUserId())
                        .eq(Favorite::getSubjectId, favorite.getSubjectId())
                        .set(Favorite::getCount, favorite.getCount());
                return wrapper;
            }));
        });
    }

    @Override
    public List<Favorite> getLimitFavoriteListById(String id) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, id)
                .orderByDesc(Favorite::getCount)
                .last("LIMIT 20");
        return favoriteMapper.selectList(wrapper);
    }

}
