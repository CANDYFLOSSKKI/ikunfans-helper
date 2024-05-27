package com.ctey.ikunfansdb.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctey.ikunfansdb.Model.Favorite;

import java.util.List;

public interface FavoriteService extends IService<Favorite> {
    public List<Favorite> getFavoriteListById(String id);
    public void addFavorite(String userId, long subjectId);
    public void removeFavorite(String userId, long subjectId);
    public boolean getFavoriteStatus(String userId, long subjectId);

}
