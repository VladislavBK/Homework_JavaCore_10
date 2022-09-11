import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String fileName = "data.xml";

        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString("JsonFile.json", json);
    }

    private static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> staff = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        Node root = doc.getDocumentElement();
        System.out.println("Корневой элемент " + root.getNodeName());
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("Текущий элемент " + node.getNodeName());
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                System.out.println("Текущий узел: " + node.getNodeName());
                Element element = (Element) node;
                NamedNodeMap map = element.getAttributes();
                staff = (List<Employee>) map;
                for (int x = 0; x < map.getLength(); x++) {
                    String attrName = map.item(x).getNodeName();
                    String attrValue = map.item(x).getNodeValue();
                    System.out.println("Атрибут: " + attrName + "; значение: " + attrValue);
                }
            }
        }
        return staff;
    }

    private static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        Gson gson = new GsonBuilder()
                .create();
        String json = gson.toJson(list, listType);
        return json;
    }

    private static void writeString(String fileName, String jsonFile) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(jsonFile);
        fileWriter.close();
    }
}
