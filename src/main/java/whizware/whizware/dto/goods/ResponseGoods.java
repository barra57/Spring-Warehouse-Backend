package whizware.whizware.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseGoods {
    private Long idGoods;
    private String nameGoods;
    private Long sellingPrice;
    private Long purchasePrice;
    private String description;
}
