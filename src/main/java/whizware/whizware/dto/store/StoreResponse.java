package whizware.whizware.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StoreResponse {
    private Long id;
    private Long locationId;
    private String name;
}
