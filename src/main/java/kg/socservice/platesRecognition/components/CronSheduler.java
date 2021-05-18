package kg.socservice.platesRecognition.components;

import kg.socservice.platesRecognition.services.PlateRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Component
public class CronSheduler {

    @Autowired
    private PlateRecognitionService plateRecognitionService;

    @Scheduled(cron = "* 0 0 ? * * *")
    public void saveSymbaseDataWithScheduler() throws ParserConfigurationException, IOException, ParseException, SAXException {
        System.out.println("Scheduler + "+ new Date());
        plateRecognitionService.saveData();
    }

}