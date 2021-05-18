package kg.socservice.platesRecognition.services;

import kg.socservice.platesRecognition.entities.PlateRecognition;

import java.util.List;

public interface PlateRecognitionService {
    PlateRecognition create(PlateRecognition plateRecognition);
    public void saveData();
    List<PlateRecognition> getAll();
}
