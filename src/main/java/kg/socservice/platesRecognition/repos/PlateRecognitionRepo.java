package kg.socservice.platesRecognition.repos;

import kg.socservice.platesRecognition.entities.PlateRecognition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateRecognitionRepo extends JpaRepository<PlateRecognition, Long> {

}
