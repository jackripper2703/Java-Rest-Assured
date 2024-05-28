package RestAssured.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Requirements {

    @JsonProperty("videoCard")
    private String videoCard;

    @JsonProperty("hardDrive")
    private Integer hardDrive;

    @JsonProperty("osName")
    private String osName;

    @JsonProperty("ramGb")
    private Integer ramGb;

}