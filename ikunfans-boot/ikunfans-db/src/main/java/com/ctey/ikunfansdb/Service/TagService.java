package com.ctey.ikunfansdb.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctey.ikunfansdb.Model.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    public void storeTagList(List<Tag> tagList);
    public List<Tag> getFavoriteTagList(List<Long> idList);

}
