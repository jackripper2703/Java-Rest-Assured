package RestAssred;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ServerTime extends UserTime{
    private String updatedAt;

    public ServerTime(String name, String job, String updatedAt){
        super(name, job);
        this.updatedAt = updatedAt;
    }

}

