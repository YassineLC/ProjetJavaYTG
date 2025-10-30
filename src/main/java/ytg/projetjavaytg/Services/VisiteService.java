package ytg.projetjavaytg.Services;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ytg.projetjavaytg.Models.Visite;
import ytg.projetjavaytg.Repositories.VisiteRepository;
import ytg.projetjavaytg.exception.ResourceNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class VisiteService {

    private final VisiteRepository visiteRepository;

    public VisiteService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }

    public List<Visite> getAllVisites() {
        return visiteRepository.findAll();
    }

    public Optional<Visite> getVisiteById(Long id) {
        Optional<Visite> visite = visiteRepository.findById(id);
        if (visite.isPresent()){
            return visite;
        }
        throw new ResourceNotFoundException("Aucune visite trouver avec l'id " + id);
    }

    @Transactional
    public Visite createVisite(Visite visite) {
        visite.setDateCreation(Instant.now());
        return visiteRepository.save(visite);
    }
}
