package com.ctey.ikunfanscommon.Entity.Bangumi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectFilterData {
    private List<Integer> type;
    private List<String> tag;
    @JsonProperty("air_date")
    private List<String> airDate;
    private List<String> rating;
    private List<String> rank;
    private boolean nsfw;

    // 筛选条件说明:
    // type         -> 条目类型(或关系)
    // tag          -> 标签(与关系)
    // airdate      -> 播出日期/发售日期(与关系)
    // rating       -> 指定评分(与关系)
    // rank         -> 指定排名(与关系)
    // nsfw         -> (忽略)是否包含NSFW结果

    // 筛选条件示例:
    // "filter": {
    //      "type": [2],
    //      "tag": ["童年", "原创"],
    //      "air_date": [">=2020-07-01", "<2020-10-01"],
    //      "rating": [">=6", "<8"],
    //      "rank": [">10", "<=18"],
    //      "nsfw": true
    //  }
}
