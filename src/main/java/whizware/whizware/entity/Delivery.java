package whizware.whizware.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Warehouse warehouse;
    @ManyToOne
    private Store store;
    @ManyToOne
    private Goods goods;
    private Long quantity;
    private BigDecimal totalPrice;
    @JsonFormat(pattern = "DD-MM-yyyy")
    private Date date;

}
