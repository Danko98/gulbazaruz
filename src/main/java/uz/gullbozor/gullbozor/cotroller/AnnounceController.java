package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.AnnounceDto;
import uz.gullbozor.gullbozor.dto.FilterDto;
import uz.gullbozor.gullbozor.entity.Announce;
import uz.gullbozor.gullbozor.repository.AnnounceRepo;
import uz.gullbozor.gullbozor.service.AnnounceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/announce")
public class AnnounceController {

    @Autowired
    private AnnounceService announceService;

    @Autowired
    private AnnounceRepo announceRepo;


    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> addAnnounce(@RequestBody AnnounceDto announceDto) {
        ApiResponse apiResponse = announceService.addAnnounce(announceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/calling/{id}")
    public ResponseEntity<?> getAnnounceCallingCount(@PathVariable Long id) {
        Optional<Announce> optionalAnnounce = announceRepo.findById(id);
        if (optionalAnnounce.isPresent()){
            return ResponseEntity.ok(optionalAnnounce.get().getCallingCount());
        }else
            return ResponseEntity.ok("Not found Announce");
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/random")
    public ResponseEntity<?> getAnnouncesByShopId() {

        List<Announce> announceListByShopId = announceService.getIndexPage();
        return ResponseEntity.ok(announceListByShopId);
    }


    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/byShop/{shopId}")
    public ResponseEntity<?> getAnnouncesByShopId(@PathVariable Long shopId, @RequestParam int page) {

        Page<Announce> announceListByShopId = announceService.getAnnounceByShopId(shopId, page);
        return ResponseEntity.ok(announceListByShopId);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/announceOfCustomer")
    public ResponseEntity<?> getAnnouncesByShopId(@RequestParam int page) {

        Page<Announce> announceListByShopId = announceService.getAnnounceCustomer(10,page);
        return ResponseEntity.ok(announceListByShopId);
    }
//    @GetMapping("/byUserId/{userId}")
//    public ResponseEntity<?> getAnnounceByUserId(@PathVariable Long userId, @RequestParam int page) {
//        Page<Announce> announcePage = announceService.getAnnounceByUserId(userId, page);
//        return ResponseEntity.ok(announcePage);

//    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editAnnounce(@RequestBody AnnounceDto announceDto, @PathVariable Long id) {
        ApiResponse apiResponse = announceService.editAnnounce(announceDto,id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/byAdmin/{id}")
    public ResponseEntity<?> editAnnounceByAdmin(@RequestBody AnnounceDto announceDto, @PathVariable Long id) {
        ApiResponse apiResponse = announceService.editAnnounceFromAdmin(announceDto,id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/fromAdmin/{id}")
    public ResponseEntity<?> editAnnounceFromAdmin(@RequestBody AnnounceDto announceDto, @PathVariable Long id) {
        ApiResponse apiResponse = announceService.editAnnounce(announceDto,id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnnounceById(@PathVariable Long id) {
        ApiResponse apiResponse = announceService.getAnnounceById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/announceList")
    public List<Announce> getAnnounceList() {
        return announceService.getAnnounceList();
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/indexPage")
    public ResponseEntity<Page<Announce>> getPageAnnounce(@RequestParam int page) {
        Page<Announce> announcePage = announceService.getAnnouncePage(page);
        return ResponseEntity.ok(announcePage);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/byDepartment/{departmentId}")
    public ResponseEntity<Page<Announce>> getAnnounceByDepartment(@RequestParam int page, @PathVariable Integer departmentId) {
        Page<Announce> announcePage = announceService.getAnnounceByDepartment(page, departmentId);
        return ResponseEntity.ok(announcePage);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<Page<Announce>> getAnnounceByCategory(@RequestParam int page, @PathVariable Integer categoryId) {
        Page<Announce> announcePage = announceService.getAnnounceByCategory(page, categoryId);
        return ResponseEntity.ok(announcePage);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myAnnounce/{sellerId}")
    public ResponseEntity<Page<Announce>> getMyAnnounce(@RequestParam int page, @PathVariable Long sellerId) {
        Page<Announce> announcePage = announceService.getMyAnnounce(sellerId, page);
        return ResponseEntity.ok(announcePage);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//    @GetMapping("/{flowerType}")
//    public ResponseEntity<Page<Announce>> getPageAnnounce(@RequestParam int page, @RequestParam Integer flowerType) {
//        Page<Announce> announcePage = announceService.getAnnouncePageByFlowerType(page, flowerType);
//        return ResponseEntity.ok(announcePage);
//    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAnnounceById(@PathVariable Long id) {
        ApiResponse apiResponse = announceService.deleteAnnounceById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @DeleteMapping("/delete/{pin}")
    public ResponseEntity<?> deleteAllByPin(@PathVariable Integer pin) {
        ApiResponse apiResponse = announceService.deleteAllByPin(pin);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }




    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/filter")
    public ResponseEntity<?> announceFilter(@RequestBody FilterDto filterDto) {
        ApiResponse apiResponse = announceService.addAnnounce(filterDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }



}

