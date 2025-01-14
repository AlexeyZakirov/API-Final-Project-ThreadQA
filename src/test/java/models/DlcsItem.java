package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DlcsItem {

    @JsonProperty("dlcName")
    private String dlcName;

    @JsonProperty("similarDlc")
    private SimilarDlc similarDlc;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isDlcFree")
    private Boolean isDlcFree;
}