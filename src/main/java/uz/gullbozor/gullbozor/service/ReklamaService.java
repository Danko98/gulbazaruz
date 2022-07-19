package uz.gullbozor.gullbozor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.Announce;
import uz.gullbozor.gullbozor.entity.Reklama;
import uz.gullbozor.gullbozor.repository.AnnounceRepo;
import uz.gullbozor.gullbozor.repository.ReklamaImageRepo;

import java.util.Optional;

@Service
public class ReklamaService {

    @Autowired
    private ReklamaImageRepo reklamaImageRepo;

    @Autowired
    private AnnounceRepo announceRepo;

    public ApiResponse addReklama(Reklama reklamaImageDto) {
        reklamaImageRepo.save(reklamaImageDto);
        return new ApiResponse("Successfully saved",true);
    }

    public ApiResponse getReklamaById(Integer id){
        if (!reklamaImageRepo.existsById(id)) {
            return new ApiResponse("Not found reklama",false);
        }
        return new ApiResponse(reklamaImageRepo.findById(id).get());
    }

    public ApiResponse getReklamaByPlaceNumber(Integer placeNumber){
        Optional<Reklama> byPlaceNumber = reklamaImageRepo.findByPlaceNumber(placeNumber);
        return byPlaceNumber.map(ApiResponse::new).orElseGet(() -> new ApiResponse("Not found reklama", false));
    }

    public ApiResponse deleteReklamaById(Integer id) {
        if (!reklamaImageRepo.existsById(id)) {
            return new ApiResponse("Not found reklama",false);
        }
        reklamaImageRepo.deleteById(id);
        return new ApiResponse("Successfully deleted",true);
    }







    public ApiResponse goReklama(Long announceId, Integer sum) {
        Optional<Announce> optionalAnnounce = announceRepo.findById(announceId);
        Announce announce = optionalAnnounce.get();

        switch (sum) {
            case 11000: announce.setTopNumber(3);
                return new ApiResponse("3x tarif reklama xizmati yoqildi.",true);

            case 15000: announce.setTopNumber(5);
                return new ApiResponse("5x tarif reklama xizmati yoqildi.",true);

            case 21000: announce.setTopNumber(10);
                return new ApiResponse("10x tarif reklama xizmati yoqildi.",true);

            default: return new ApiResponse("Noto'g'ri summa kiritildi",false);
        }

    }

}
