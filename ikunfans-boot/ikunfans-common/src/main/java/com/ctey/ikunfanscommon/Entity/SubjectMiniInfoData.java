package com.ctey.ikunfanscommon.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectMiniInfoData {
    private Long id;
    private String name;
    @JsonProperty("nameCN")
    private String nameCN;
    @JsonProperty("airDate")
    private String airDate;
    @JsonProperty("airWeekday")
    private Integer airWeekday;
    private String image;
    private Double score;
    private Integer rank;
}
