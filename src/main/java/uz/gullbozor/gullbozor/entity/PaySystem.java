package uz.gullbozor.gullbozor.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pay_system")
public class PaySystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_number", nullable = false)
    private Long payNumber;

    @Column(name = "pay_value", nullable = false)
    private Double payValue;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
