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
@Table(name = "shop")
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shopName;
    private Long sellerId;
    private String phoneNumber1;
    private String phoneNumber2;
    private Integer regionId;
    private Integer cityId;
    private String streetHouse;

}
