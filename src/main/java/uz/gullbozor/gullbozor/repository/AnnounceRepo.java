package uz.gullbozor.gullbozor.repository;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.gullbozor.gullbozor.entity.Announce;

import java.sql.Timestamp;
import java.util.List;

public interface AnnounceRepo extends JpaRepository<Announce, Long> {







    Page<Announce> findAllByDepartment(Integer department, Pageable pageable);

    Page<Announce> findAllByCategoryId(Integer category, Pageable pageable);

    Page<Announce> findAllBySeller(boolean seller, Pageable pageable);

    Page<Announce> findAllByShopId(Long shopId, Pageable pageable);

    Page<Announce> findAllBySellerId(Long sellerId, Pageable pageable);

    Page<Announce> findAllByTopNumber(Integer topNumber, Pageable pageable);

    @Query(nativeQuery=true, value="SELECT *  FROM announce a WHERE a.top_number = 3 ORDER BY random() LIMIT 3")
    List<Announce> findByTopNumberRandom3();

    @Query(nativeQuery=true, value="SELECT *  FROM announce a WHERE a.top_number = 5 ORDER BY random() LIMIT 3")
    List<Announce> findByTopNumberRandom5();

    @Query(nativeQuery=true, value="SELECT *  FROM announce a WHERE a.top_number = 10 ORDER BY random() LIMIT 3")
    List<Announce> findByTopNumberRandom10();

//
//    Page<Announce> findAllByUserEntity_Id(Long userEntity_id, Pageable pageable);
//
//   // List<Announce> findByFlowerTypeAndOrderByCreateAtDesc(Integer flowerType);
//
//
//    List<Announce> findTop7ByFlowerTypeOrderByCreateAtDesc(Integer flowerType);
//
//    List<Announce> findTop7ByFlowerTypeAndCreateAtGreaterThanEqualOrderByCreateAtDesc(Integer flowerType, Timestamp createAt);
//







}
