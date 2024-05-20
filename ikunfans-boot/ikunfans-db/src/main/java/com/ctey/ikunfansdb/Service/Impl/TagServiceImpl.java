package com.ctey.ikunfansdb.Service.Impl;

import com.baomidou.mybatisplus.core.batch.MybatisBatch;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctey.ikunfansdb.Mapper.TagMapper;
import com.ctey.ikunfansdb.Model.Tag;
import com.ctey.ikunfansdb.Service.TagService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    private final TagMapper tagMapper;
    private final SqlSessionFactory sqlSessionFactory;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public TagServiceImpl(TagMapper tagMapper, SqlSessionFactory sqlSessionFactory, TransactionTemplate transactionTemplate) {
        this.tagMapper = tagMapper;
        this.sqlSessionFactory = sqlSessionFactory;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    @Transactional
    public void storeTagList(List<Tag> tagList) {
        transactionTemplate.execute(status -> {
            MybatisBatch<Tag> tagBatchInsert = new MybatisBatch<>(sqlSessionFactory, tagList);
            MybatisBatch.Method<Tag> method = new MybatisBatch.Method<>(TagMapper.class);
            return tagBatchInsert.execute(method.insert());
        });
    }

    @Override
    public List<Tag> getFavoriteTagList(List<Long> idList) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Tag::getSubjectId, idList);
        return tagMapper.selectList(wrapper);
    }

}
