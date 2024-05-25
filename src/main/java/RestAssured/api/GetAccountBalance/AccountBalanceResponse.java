package RestAssured.api.GetAccountBalance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.restassured.RestAssured.given;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceResponse {
    private Integer balance;

    public static AccountBalanceResponse accountBalanceResponse(){
        return given()
                .get("wallet/b2c/wallet/")
                .then()
                .extract().as(AccountBalanceResponse.class);
    }
}
