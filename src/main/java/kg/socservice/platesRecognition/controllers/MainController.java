package kg.socservice.platesRecognition.controllers;

import kg.socservice.platesRecognition.services.PlateRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

@Controller
public class MainController {
    @Autowired
    private PlateRecognitionService plateRecognitionService;

    @GetMapping("/")
    public String getUserList(Model model) throws ParserConfigurationException, IOException, SAXException, ParseException {
        plateRecognitionService.saveData();
        model.addAttribute("plates", plateRecognitionService.getAll());
        return "plates/plates_list";
    }
}
