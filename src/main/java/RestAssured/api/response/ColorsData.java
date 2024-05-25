package RestAssured.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorsData {
    public Integer id;
    public String name;
    public Integer year;
    public String color;
    public String pantone_value;
}

