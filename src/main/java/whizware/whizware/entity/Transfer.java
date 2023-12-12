package whizware.whizware.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Warehouse warehouse;
    @ManyToOne
    private Warehouse warehouse_target;
    @ManyToOne
    private Goods goods;

    private Integer quantity;
    private String status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
}
