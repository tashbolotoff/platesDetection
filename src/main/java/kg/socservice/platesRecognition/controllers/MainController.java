package kg.socservice.platesRecognition.controllers;

import kg.socservice.platesRecognition.services.PlateRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    private PlateRecognitionService plateRecognitionService;

    @GetMapping("/list")
    public String getUserList(Model model) {
        model.addAttribute("plates", plateRecognitionService.getAll());
        return "plate_list";
    }
}
