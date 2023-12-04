package whizware.whizware.dto.store;

import lombok.Data;

@Data
public class StoreRequest {
    private Long id;
    private Long loc_id;
    private String name;
}
