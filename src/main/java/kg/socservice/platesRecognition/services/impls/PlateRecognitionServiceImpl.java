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
import java.util.List;

@Service
public class PlateRecognitionServiceImpl implements PlateRecognitionService {

    @Autowired
    private PlateRecognitionRepo plateRecognitionRepo;

    @Override
    public PlateRecognition create(PlateRecognition plateRecognition) {
        return plateRecognitionRepo.save(plateRecognition);
    }

//    @Override
//    public void saveData() {
//        Unirest.setTimeouts(0, 0);
//        try {
//
//            HttpResponse<String> response = Unirest.post("http://192.168.1.166/ISAPI/ContentMgmt/search")
//                    .header("Authorization", "Basic YWRtaW46R2FyYWoyMDIx")
//                    .header("Content-Type", "application/xml")
//                    .body("<?xml version: '1.0' encoding='utf-8'?>\r\n        <CMSearchDescription>\r\n            <searchID>ANY_STRING_HERE</searchID><trackIDList><trackID>103</trackID></trackIDList>\r\n            <timeSpanList><timeSpan><startTime>2020-06-22T02:25:13Z</startTime><endTime>2021-06-22T02:25:13Z</endTime></timeSpan></timeSpanList>\r\n            <contentTypeList><contentType>metadata</contentType></contentTypeList>\r\n            <maxResults>100</maxResults><searchResultPostion>0</searchResultPostion>\r\n            <metadataList>\r\n                <metadataDescriptor>//recordType.meta.std-cgi.com/vehicleDetection</metadataDescriptor>\r\n                <SearchProperity><plateSearchMask/><nation>0</nation></SearchProperity>\r\n            </metadataList>\r\n        </CMSearchDescription>")
//                    .asString();;
//
//            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
//                    new InputSource(new StringReader(response.toString())));
//
//            NodeList nodeList = document.getDocumentElement().getChildNodes();
//
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) node;
//                    if (element.getNodeName().contains("mediaSegmentDescriptor")) {
//                        System.out.println(element.getElementsByTagName("NO").item(0).getTextContent());
//                    }
//                }
//            }
//
////            //And from this also.....
////            for (int i = 0; i < nodeList.getLength(); i++) {
////                Element element1 = (Element) nodeList.item(i);
////                System.out.println(element1.getElementsByTagName("matchList").item(0).getTextContent());
////            }
//        } catch (UnirestException | ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void saveData() throws ParserConfigurationException, IOException, SAXException {

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
                System.out.println("contentType: " + getValue("contentType", element));
                System.out.println("codecType: " + getValue("codecType", element));
                System.out.println("playbackURI: " + getValue("playbackURI", element));
            }
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
