package whizware.whizware.dto.goods;

import lombok.Data;

@Data
public class GoodsRequest {

    private String name;
    private Long sellingPrice;
    private Long purchasePrice;
    private String description;
}
