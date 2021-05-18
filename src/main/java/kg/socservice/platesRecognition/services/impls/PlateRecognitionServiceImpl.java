package kg.socservice.platesRecognition.services.impls;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import kg.socservice.platesRecognition.entities.PlateRecognition;
import kg.socservice.platesRecognition.repos.PlateRecognitionRepo;
import kg.socservice.platesRecognition.services.PlateRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void saveData() throws ParserConfigurationException, IOException, SAXException, ParseException {
        List<String> plates = new ArrayList<>();
        Unirest.setTimeouts(0, 0);
        try {

            HttpResponse<String> response = Unirest.post("http://192.168.1.166/ISAPI/ContentMgmt/search")
                    .header("Authorization", "Basic YWRtaW46R2FyYWoyMDIx")
                    .header("Content-Type", "application/xml")
                    .body("<?xml version: '1.0' encoding='utf-8'?>\r\n        <CMSearchDescription>\r\n            <searchID>ANY_STRING_HERE</searchID><trackIDList><trackID>103</trackID></trackIDList>\r\n            <timeSpanList><timeSpan><startTime>2020-06-22T02:25:13Z</startTime><endTime>2021-06-22T02:25:13Z</endTime></timeSpan></timeSpanList>\r\n            <contentTypeList><contentType>metadata</contentType></contentTypeList>\r\n            <maxResults>100</maxResults><searchResultPostion>0</searchResultPostion>\r\n            <metadataList>\r\n                <metadataDescriptor>//recordType.meta.std-cgi.com/vehicleDetection</metadataDescriptor>\r\n                <SearchProperity><plateSearchMask/><nation>0</nation></SearchProperity>\r\n            </metadataList>\r\n        </CMSearchDescription>")
                    .asString();

            java.io.FileWriter fw = new java.io.FileWriter("src/main/resources/plates.xml");
            fw.write(response.getBody());
            fw.close();

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        File stocks = new File("src/main/resources/plates.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(stocks);
        doc.getDocumentElement().normalize();

        System.out.println(doc.getDocumentElement().getNodeName());
        NodeList nodes = doc.getElementsByTagName("mediaSegmentDescriptor");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
//                System.out.println("playbackURI: " + getValue("playbackURI", element));
                plates.add(getValue("playbackURI", element));

            }
        }
        for (int i = 0; i < plates.size(); i++) {
            String date = null;
            String plate = null;
            int indexOfSabaka = plates.get(i).indexOf("@") + 1;
            date = plates.get(i).substring(indexOfSabaka, indexOfSabaka + 14);
            int indexOf_ = indexOfSabaka + 15;
            String substr = plates.get(i).substring(indexOf_, plates.get(i).length()-1);
            int indexOf_i = substr.indexOf("&");
            plate = plates.get(i).substring(indexOf_, indexOf_+indexOf_i);

            PlateRecognition plateRecognition = PlateRecognition.builder()
                    .plate(plate)
                    .recognitionDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(date))
                    .camIp("192.168.1.166")
                    .count(1)
                    .build();
            plateRecognitionRepo.save(plateRecognition);

        }
    }

    static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }


    @Override
    public List<PlateRecognition> getAll() {
        return plateRecognitionRepo.findAll();
    }
}
