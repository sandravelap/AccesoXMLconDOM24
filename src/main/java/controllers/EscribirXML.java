package controllers;

import models.Coche;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.print.DocFlavor;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.util.ArrayList;

import static libs.UserData.ficheroEscribible;

public class EscribirXML {
    public String escribir(Path archivo){
        //definimos la variable para devolver los datos
        String mensaje = "El archivo no se puede crear";
        //definimos la ruta del archivo a escribir
        Path p = Path.of("target", String.valueOf(archivo.getFileName()));
        //inicializamos las clases para usar la API DOM
        DocumentBuilder builder;
        Document document = null;
        //creamos los archivos Coche a escribir
        Coche coche1 = new Coche(1, "Peugeot", "306", 1.9 );
        Coche coche2 = new Coche(2, "Mercedes", "GLA", 1.7 );
        Coche coche3 = new Coche(3, "Renault", "Twingo", 1.1 );
        ArrayList<Coche> coches = new ArrayList<Coche>();
        coches.add(coche1);
        coches.add(coche2);
        coches.add(coche3);

        //comprobamos si el archivo existe y se puede crear
        if (ficheroEscribible(p)) {
            //Esta clase abstracta permite crear el creador de XML a DOM de Java
            mensaje = "Fichero creado. ";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                //Creamos el documento de DOM que representará el archivo XML:
                builder = factory.newDocumentBuilder();
                document = builder.newDocument();
                addCoche(coches, document, p);
            } catch (ParserConfigurationException e) {
                mensaje = "Error al procesar el archivo.";
            }
        }
        return mensaje;
    }

    private static void addCoche(ArrayList<Coche> coches, Document doc, Path p) {
        // creamos la etiqueta raiz
        Node nodoRaiz = doc.createElement("concesionario");
        doc.appendChild(nodoRaiz);

        for (Coche coche : coches) {
            // creamos un elemento hijo para guardar cada coche
            Element etiqCoche = doc.createElement("coche");
            nodoRaiz.appendChild(etiqCoche);
            // creamos las etiquetas para los datos del objeto coche
            Element etiqMarca = doc.createElement("marca");
            etiqCoche.appendChild(etiqMarca);
            etiqMarca.appendChild(doc.createTextNode(coche.getMarca()));

            Element etiqModelo = doc.createElement("modelo");
            etiqCoche.appendChild(etiqModelo);
            etiqModelo.appendChild(doc.createTextNode(coche.getModelo()));

            Element etiqCilin = doc.createElement("cilindrada");
            etiqCoche.appendChild(etiqCilin);
            etiqCilin.appendChild(doc.createTextNode(String.valueOf(coche.getCilindrada())));

            //creamos el atributo id y lo añadimos a la etiqueta coche
            etiqCoche.setAttribute("id",String.valueOf(coche.getId()));
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(doc);
            Result result = new StreamResult(p.toFile());
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http:xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
