package kg.socservice.platesRecognition.services;

import kg.socservice.platesRecognition.entities.PlateRecognition;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface PlateRecognitionService {
    PlateRecognition create(PlateRecognition plateRecognition);
    public void saveData() throws ParserConfigurationException, IOException, SAXException, ParseException;
    List<PlateRecognition> getAll();
}
