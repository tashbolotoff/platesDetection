package kg.socservice.platesRecognition.services;

import kg.socservice.platesRecognition.entities.PlateRecognition;

import java.util.List;

public interface PlateRecognitionService {
    PlateRecognition create(PlateRecognition plateRecognition);

    List<PlateRecognition> getAll();
}
