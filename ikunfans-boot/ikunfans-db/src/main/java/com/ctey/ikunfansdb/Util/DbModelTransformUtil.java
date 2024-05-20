package com.ctey.ikunfansdb.Util;

import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectPostItemData;
import com.ctey.ikunfanscommon.Entity.Bangumi.SubjectTagData;
import com.ctey.ikunfanscommon.Entity.SubjectInfoData;
import com.ctey.ikunfanscommon.Entity.TokenClaimEntity;
import com.ctey.ikunfanscommon.Req.UserLoginReq;
import com.ctey.ikunfanscommon.Entity.UserInfoData;
import com.ctey.ikunfansdb.Model.Favorite;
import com.ctey.ikunfansdb.Model.Subject;
import com.ctey.ikunfansdb.Model.Tag;
import com.ctey.ikunfansdb.Model.User;

import java.util.List;

public class DbModelTransformUtil {
    public static TokenClaimEntity userToTokenClaim(User user) {
        return new TokenClaimEntity(user.getId(), user.getAccount());
    }

    public static User userLoginReqToUser(UserLoginReq req) {
        return new User(null, req.getAccount(), req.getPassword());
    }

    public static UserInfoData userToUserInfo(User user) {
        return new UserInfoData(user.getId(), user.getAccount());
    }

    public static Tag subjectItemTagToTag(int id, SubjectTagData data) {
        return new Tag(Long.valueOf(id), data.getName(), data.getCount());
    }

    public static Favorite subjectItemDataToFavorite(String id, SubjectPostItemData data) {
        return new Favorite(id, Long.valueOf(data.getId()), 1);
    }

    public static Subject subjectItemDataToSubject(SubjectPostItemData data) {
        return new Subject(
                Long.valueOf(data.getId()),
                data.getName(),
                data.getNameCN(),
                data.getDate(),
                data.getImage(),
                data.getSummary(),
                data.getScore(),
                data.getRank()
        );
    }

    public static SubjectInfoData subjectAndTagListToSubjectInfo(Subject subject, List<String> tagList) {
        return new SubjectInfoData(
                subject.getId(),
                subject.getName(),
                subject.getNameCN(),
                subject.getAirDate(),
                subject.getImage(),
                subject.getSummary(),
                subject.getScore(),
                subject.getScoreRank(),
                tagList
        );
    }

}
