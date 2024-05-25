package RestAssured.api.PostAuthorizations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String access_token;
    private String token_type;
    private String scopes;
    private String refresh_token;
    private String device_id;
    private Boolean approve_needed;
}
