package whizware.whizware.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
