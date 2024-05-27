package com.ctey.ikunfanscommon.Entity.Bangumi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCollectionData {
    @JsonProperty("on_hold")
    private int onHold;
    private int dropped;
    private int wish;
    private int collect;
    private int doing;
}
