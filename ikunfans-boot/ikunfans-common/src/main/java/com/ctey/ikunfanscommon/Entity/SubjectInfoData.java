package com.ctey.ikunfanscommon.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectInfoData {
    private Long id;
    private String name;
    @JsonProperty("nameCN")
    private String nameCN;
    @JsonProperty("airDate")
    private String airDate;
    private String image;
    private String summary;
    private Double score;
    private Integer rank;
    private List<String> tags;
}
