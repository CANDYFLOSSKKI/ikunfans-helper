package com.ctey.ikunfansdb.Service.Impl;

import com.baomidou.mybatisplus.core.batch.MybatisBatch;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctey.ikunfansdb.Mapper.SubjectMapper;
import com.ctey.ikunfansdb.Model.Subject;
import com.ctey.ikunfansdb.Service.SubjectService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    private final SubjectMapper subjectMapper;
    private final SqlSessionFactory sqlSessionFactory;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public SubjectServiceImpl(SubjectMapper subjectMapper, SqlSessionFactory sqlSessionFactory, TransactionTemplate transactionTemplate) {
        this.subjectMapper = subjectMapper;
        this.sqlSessionFactory = sqlSessionFactory;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public List<Integer> getIdList() {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Subject::getId);
        return subjectMapper.selectList(wrapper).stream().map(i -> i.getId().intValue()).toList();
    }

    @Override
    @Transactional
    public void storeSubjectList(List<Subject> subjectList) {
        transactionTemplate.execute(status -> {
            MybatisBatch<Subject> subjectBatchInsert = new MybatisBatch<>(sqlSessionFactory, subjectList);
            MybatisBatch.Method<Subject> method = new MybatisBatch.Method<>(SubjectMapper.class);
            return subjectBatchInsert.execute(method.insert());
        });
    }

    @Override
    public List<Subject> getFavoriteSubjectList(List<Long> idList) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Subject::getId, idList)
                .orderByDesc(Subject::getScoreRank);
        return subjectMapper.selectList(wrapper);
    }

    @Override
    public boolean hasSubject(long id) {
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getId, id);
        return subjectMapper.exists(wrapper);
    }
}
