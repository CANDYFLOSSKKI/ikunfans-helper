package com.ctey.ikunfanscommon.Entity.Bangumi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectGetItemData {
    private int id;
    private String url;
    private int type;
    private String name;
    @JsonProperty("name_cn")
    private String nameCN;
    private String summary;
    @JsonProperty("air_date")
    private String airDate;
    @JsonProperty("air_weekday")
    private String airWeekday;
    private SubjectImageData images;
    private int eps;
    @JsonProperty("eps_count")
    private int epsCount;
    private SubjectRatingData rating;
    private int rank;
    private Object collection;
}
