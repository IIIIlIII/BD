package example;


import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

    public static void main(String[] args) {
        DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder;

        try {
            builder=builderFactory.newDocumentBuilder();
            Document doc=builder.parse("lab_6.xml");
            XPathFactory pathFactory = XPathFactory.newInstance();
            XPath xpath = pathFactory.newXPath();

            List<String> list_doctors=getDoctor(doc, xpath, "Иван");
            System.out.println("Докторов с именем Иван:"+list_doctors.size());
            for (String doctor : list_doctors) {
                System.out.println(doctor);
            }

            System.out.println("имя клиента с телефоном 880553535:");

            System.out.println(" - "+getClients(doc, xpath, 880553535));

            System.out.println("телефон стоматолгического отдела:"+ getPhoneDepartment(doc,xpath,"Стоматолгический"));

        } catch(ParserConfigurationException | SAXException | IOException | XPathExpressionException ex){
            ex.printStackTrace();
        }

    }

    public static List<String> getDoctor(Document doc, XPath xpath, String pos) throws DOMException, XPathExpressionException{

        List<String> list= new ArrayList<>();
        XPathExpression expr = xpath.compile(String.format("//doctors/doctor" +
                "[name=\"%s\"]/firstName", pos));
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            list.add(nodes.item(i).getTextContent());
        }
        return list;
    }

    public static List<String> getPhoneDepartment(Document doc, XPath xpath, String name) throws DOMException, XPathExpressionException{
        List<String> list= new ArrayList<>();
        XPathExpression expr = xpath.compile(String.format("//departments/department" +
                "[name_department=\"%s\"]/phone_department", name));
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            list.add(nodes.item(i).getTextContent());
        }
        return list;
    }

    public static String getClients(Document doc, XPath xpath, int phone) throws DOMException, XPathExpressionException{

        XPathExpression expr = xpath.compile(String.format("//clients/client" +
                "[phone_client=\"%d\"]/name", phone));
        return (String)expr.evaluate(doc, XPathConstants.STRING);
    }

}


