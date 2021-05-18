package kg.socservice.platesRecognition.repos;

import kg.socservice.platesRecognition.entities.PlateRecognition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PlateRecognitionRepo extends JpaRepository<PlateRecognition, Long> {
    PlateRecognition getAllByRecognitionDateAndPlate(Date date, String plate);
    PlateRecognition getPlateRecognitionByPlateOrderByIdDesc(String plate);
}
