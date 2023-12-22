package whizware.whizware.dto.location;

import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationRequest {
    @NotBlank(message = "Please enter Location Name")
    private String name;
}
