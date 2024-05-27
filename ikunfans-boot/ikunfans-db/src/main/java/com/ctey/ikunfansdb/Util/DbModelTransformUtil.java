package com.ctey.ikunfansdb.Util;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectGetItemData;
import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;
import com.ctey.ikunfanscommon.Entity.SubjectInfoData;
import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import com.ctey.ikunfanscommon.Entity.UserInfoData;
import com.ctey.ikunfanscommon.Req.UserLoginReq;
import com.ctey.ikunfanscommon.Resp.SubjectDetailResp;
import com.ctey.ikunfanscommon.Util.CollectionEmptyUtil;
import com.ctey.ikunfansdb.Model.Subject;
import com.ctey.ikunfansdb.Model.User;

public class DbModelTransformUtil {
    public static TokenClaimEntity userToTokenClaim(User user) {
        return new TokenClaimEntity(
                user.getId(),
                user.getAccount()
        );
    }

    public static User userLoginReqToUser(UserLoginReq req) {
        return new User(
                null,
                req.getAccount(),
                req.getPassword()
        );
    }

    public static UserInfoData userToUserInfo(User user) {
        return new UserInfoData(
                user.getId(),
                user.getAccount()
        );
    }

    public static Subject subjectItemDataToSubject(SubjectGetItemData data) {
        return new Subject(
                Long.valueOf(data.getId()),
                data.getName(),
                CollectionEmptyUtil.forString(data.getNameCN()) ? data.getName() : data.getNameCN(),
                data.getAirDate(),
                data.getImages() == null ? "" : data.getImages().getLarge(),
                data.getSummary(),
                data.getEps(),
                data.getRating().getScore(),
                data.getRank()
        );
    }

    public static SubjectInfoData subjectItemDataToSubjectInfo(SubjectGetItemData data) {
        return new SubjectInfoData(
                Long.valueOf(data.getId()),
                data.getName(),
                CollectionEmptyUtil.forString(data.getNameCN()) ? data.getName() : data.getNameCN(),
                data.getAirDate(),
                data.getImages() == null ? "" : data.getImages().getLarge(),
                data.getSummary(),
                data.getEps(),
                data.getRating().getScore(),
                data.getRank()
        );
    }

    public static Subject subjectItemDataToSubject(SubjectPostItemData data) {
        return new Subject(
                Long.valueOf(data.getId()),
                data.getName(),
                CollectionEmptyUtil.forString(data.getNameCN()) ? data.getName() : data.getNameCN(),
                data.getDate(),
                data.getImage(),
                data.getSummary(),
                1,
                data.getScore(),
                data.getRank()
        );
    }

    public static SubjectInfoData subjectItemDataToSubjectInfo(SubjectPostItemData data) {
        return new SubjectInfoData(
                Long.valueOf(data.getId()),
                data.getName(),
                CollectionEmptyUtil.forString(data.getNameCN()) ? data.getName() : data.getNameCN(),
                data.getDate(),
                data.getImage(),
                data.getSummary(),
                1,
                data.getScore(),
                data.getRank()
        );
    }

    public static SubjectInfoData subjectToSubjectInfo(Subject subject) {
        return new SubjectInfoData(
                subject.getId(),
                subject.getName(),
                subject.getNameCN(),
                subject.getAirDate(),
                subject.getImage(),
                subject.getSummary(),
                subject.getEps(),
                subject.getScore(),
                subject.getScoreRank()
        );
    }

    public static Subject subjectDetailToSubject(SubjectDetailResp resp) {
        return new Subject(
                resp.getId(),
                resp.getName(),
                resp.getNameCN(),
                resp.getDate(),
                resp.getImage(),
                resp.getSummary(),
                resp.getEps(),
                resp.getScore(),
                resp.getRank()
        );
    }

}
