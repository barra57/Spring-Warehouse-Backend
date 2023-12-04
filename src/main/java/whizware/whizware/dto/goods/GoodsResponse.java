package whizware.whizware.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GoodsResponse {
    private Long id;
    private String name;
    private Long sellingPrice;
    private Long purchasePrice;
    private String description;
}
