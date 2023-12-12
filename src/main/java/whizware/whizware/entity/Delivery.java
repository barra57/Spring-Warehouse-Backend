package whizware.whizware.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
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
    private Long qty;
    private BigDecimal totalPrice;
    private String status;
    @JsonFormat(pattern = "DD-MM-yyyy")
    private Date date;

}
