package com.ctey.ikunfansweb.Controller;

import com.ctey.ikunfanscommon.Entity.SubjectInfoData;
import com.ctey.ikunfanscommon.Req.SubjectContentReq;
import com.ctey.ikunfanscommon.Resp.*;
import com.ctey.ikunfanscommon.Util.ModelTransformUtil;
import com.ctey.ikunfansweb.Service.SubjectWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {
    private final SubjectWebService subjectWebService;

    @Autowired
    public SubjectController(SubjectWebService subjectWebService) {
        this.subjectWebService = subjectWebService;
    }

    @PostMapping("/content")
    public DefaultDataResp getTargetSubject(@RequestHeader("Authorization") String auth, @RequestBody SubjectContentReq req) {
        SubjectContentResp data = subjectWebService.collectTargetSubjects(auth, req);
        return ModelTransformUtil.getDefaultDataInstance("番剧推荐", data);
    }

    @PostMapping("/content/simple")
    public DefaultDataResp getSimpleTargetSubject(@RequestHeader("Authorization") String auth, @RequestBody SubjectContentReq req) {
        SubjectContentResp data = subjectWebService.collectSimpleTargetSubjects(auth, req);
        return ModelTransformUtil.getDefaultDataInstance("番剧搜索", data);
    }

    @GetMapping("/user")
    public DefaultDataResp getUserSubject(@RequestHeader("Authorization") String auth) {
        List<SubjectInfoData> data = subjectWebService.collectUserSubjects(auth);
        return ModelTransformUtil.getDefaultDataInstance("用户收藏", data);
    }

    @GetMapping("/user/add")
    public DefaultMsgResp processUserAddSubject(@RequestHeader("Authorization") String auth, @RequestParam("id") Long id) {
        boolean flag = subjectWebService.processAddUserFavorite(auth, id);
        return ModelTransformUtil.getDefaultMsgInstance(flag, "添加收藏", "");
    }

    @GetMapping("/user/remove")
    public DefaultMsgResp processUserRemoveSubject(@RequestHeader("Authorization") String auth, @RequestParam("id") Long id) {
        boolean flag = subjectWebService.processRemoveUserFavorite(auth, id);
        return ModelTransformUtil.getDefaultMsgInstance(flag, "移除收藏", "");
    }

    @GetMapping("/calendar")
    public DefaultDataResp getCalendarSubject(@RequestHeader("Authorization") String auth) {
        SubjectCalendarResp data = subjectWebService.collectCalendarSubjects(auth);
        return ModelTransformUtil.getDefaultDataInstance("番剧日历", data);
    }

    @GetMapping("/detail")
    public DefaultDataResp getSubjectDetail(@RequestHeader("Authorization") String auth, @RequestParam("id") Long id) {
        SubjectDetailResp data = subjectWebService.collectSubjectDetail(auth, id);
        return ModelTransformUtil.getDefaultDataInstance("番剧详情", data);
    }

}
