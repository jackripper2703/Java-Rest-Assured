package RestAssured.models;

import java.util.List;

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
public class FullUser {

    @JsonProperty("pass")
    private String pass;

    @JsonProperty("games")
    private List<GamesItem> games;

    @JsonProperty("login")
    private String login;

}