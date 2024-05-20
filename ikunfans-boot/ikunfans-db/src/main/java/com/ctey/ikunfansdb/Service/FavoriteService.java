package com.ctey.ikunfansdb.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctey.ikunfansdb.Model.Favorite;

import java.util.List;

public interface FavoriteService extends IService<Favorite> {
    public List<Favorite> getFavoriteListById(String id);
    public List<Favorite> getLimitFavoriteListById(String id);
    public void updateFavoriteList(List<Favorite> favoriteList);
    public void storeFavoriteList(List<Favorite> favoriteList);

}
