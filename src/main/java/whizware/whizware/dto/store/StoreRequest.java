package whizware.whizware.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreRequest {
    private Long locationId;
    private String name;
}
