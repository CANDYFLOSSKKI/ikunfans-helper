package com.ctey.ikunfansdb.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctey.ikunfansdb.Model.Subject;

import java.util.List;

public interface SubjectService extends IService<Subject> {
    public List<Integer> getIdList();
    public List<Subject> getFavoriteSubjectList(List<Long> idList);
    public void storeSubjectList(List<Subject> subjectList);
    public boolean hasSubject(long id);
}
