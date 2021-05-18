package kg.socservice.platesRecognition.services.impls;

import kg.socservice.platesRecognition.entities.PlateRecognition;
import kg.socservice.platesRecognition.repos.PlateRecognitionRepo;
import kg.socservice.platesRecognition.services.PlateRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;

@Service
public class PlateRecognitionServiceImpl implements PlateRecognitionService {

    @Autowired
    private PlateRecognitionRepo plateRecognitionRepo;

    @Override
    public PlateRecognition create(PlateRecognition plateRecognition) {
        return plateRecognitionRepo.save(plateRecognition);
    }

    @Override
    public List<PlateRecognition> getAll() {
        return plateRecognitionRepo.findAll();
    }
}
