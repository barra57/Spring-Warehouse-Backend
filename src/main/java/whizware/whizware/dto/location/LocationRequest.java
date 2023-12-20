package whizware.whizware.dto.location;

import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationRequest {
    @NotNull(message = "Please enter Location ID")
    private Long id;
    @NotBlank(message = "Please enter Location Name")
    private String name;
}
