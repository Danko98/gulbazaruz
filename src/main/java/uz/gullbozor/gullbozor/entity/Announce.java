package uz.gullbozor.gullbozor.entity;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "announce")
public class Announce extends BaseEntity {

    @Column(name = "price")
    private double price;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "title")
    private String title;

    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;

    @Column(columnDefinition="text", length=1500)
    private String description;


    private String phoneNumber;
    private Integer callingCount;
    private Integer topNumber;
    private Integer department;
    private Long categoryId;
    private Long regionId;
    private Long cityId;
    private Long shopId;
    private Long sellerId;
    private double height;
    private double diameter;
    private double weight;

    private boolean isSeller;
    private boolean isWithPot;
    private boolean isWithFertilizer;
    private boolean isAllowed;



}
