package whizware.whizware.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LocationResponse {
    private Long id;
    private String name;
}
