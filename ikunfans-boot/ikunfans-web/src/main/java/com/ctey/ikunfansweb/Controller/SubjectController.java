package com.ctey.ikunfansweb.Controller;

import com.ctey.ikunfanscommon.Entity.SubjectInfoData;
import com.ctey.ikunfanscommon.Req.SubjectContentReq;
import com.ctey.ikunfanscommon.Resp.DefaultDataResp;
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
        List<SubjectInfoData> data = subjectWebService.collectTargetSubjects(auth, req);
        return ModelTransformUtil.getDefaultDataInstance("AI推荐", data);
    }

    @GetMapping("/user")
    public DefaultDataResp getUserSubject(@RequestHeader("Authorization") String auth) {
        List<SubjectInfoData> data = subjectWebService.collectUserSubjects(auth);
        return ModelTransformUtil.getDefaultDataInstance("用户看过", data);
    }

}
