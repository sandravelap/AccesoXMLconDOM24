package controllers;

import models.Coche;
import models.Concesionario;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class LeerXML {
    public String leerTodosNodos(Path p) {
        StringBuilder mensaje = new StringBuilder("El archivo no se puede leer. ");
        if (Files.isReadable(p)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            mensaje = new StringBuilder();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                try {
                    Document document = builder.parse(p.toFile());
                    Element nodoraiz = document.getDocumentElement();
                    NodeList nodos = nodoraiz.getChildNodes();
                    //la estrucutra siguiente recorre todos los nodos hijos incluyendo los nodos text vacíos
                    for (int i = 0; i < nodos.getLength(); i++) {
                        mensaje.append("Nombre del nodo: ").append(nodos.item(i).getNodeName()).append("\n");
                        //con getTextContent recuperamos todo el texto que contiene el nodo y sus hijos
                        mensaje.append("\t Contenido del nodo" ).append(nodos.item(i).getTextContent()).append("\n");
                        mensaje.append("\t Hijo del nodo: ").append(nodos.item(i).getParentNode().getNodeName()).append("\n");
                    }
                    mensaje.append("Archivo XML cargado.");
                } catch (SAXException e) {
                    mensaje.append("Error al procesar el archivo.");
                } catch (IOException e) {
                    mensaje.append("Error al leer el archivo");
                }
            } catch (
                    ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return mensaje.toString();
    }
    public String leerArchivoFiltrado(Path p) {
        //inicializamos la variable para almacenar el texto a devolver:
        StringBuilder mensaje = new StringBuilder("El archivo no se puede leer. ");
        //comprobamos si el archivo existe y se puede leer
        if (Files.isReadable(p)) {
            //Esta clase abstracta permite crear el parseador de XML a DOM de Java
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            mensaje = new StringBuilder();
            try {
                //El parseador permite crear un objeto Document que represente el archivo xml
                DocumentBuilder parser = factory.newDocumentBuilder();
                Document document = parser.parse(p.toFile());
                //obtener el nombre del nodo raíz:
                String nodoRaiz = document.getDocumentElement().getNodeName();
                mensaje.append("El elemento raíz del archivo " ).append( p.getFileName() ).append( " es " ).append(nodoRaiz).append("\n");
                //obtener los nodos hijos:
                Element elRaiz = document.getDocumentElement();
                NodeList hijos = elRaiz.getChildNodes();
                //obtener información de los nodos hijos si no sabemos la estructura del XML:
                mensaje.append("El nodo raíz ").append(nodoRaiz).append(" tiene " ).append(hijos.getLength()).append(" hijos. \n");
                //NodeList no admite ser recorrido con una estructura for each:
                for (int i = 0; i < hijos.getLength(); i++) {
                    //como conozco la estructura, me quedo sólo con el tipo de nodo que me interesa
                    //si elimino este condicional me saldrán nodos vacíos
                    if (hijos.item(i).getNodeType() == Node.ELEMENT_NODE) { //O 1
                        mensaje.append("Nodo ").append( i + 1).append(": ").append(hijos.item(i).getNodeName()).append("\n");
                        NodeList datosCoche = hijos.item(i).getChildNodes();
                        for (int j = 0; j < datosCoche.getLength(); j++) {
                            if (datosCoche.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                mensaje.append("\t").append(datosCoche.item(j).getNodeName()).append( ":  ").append(
                                        datosCoche.item(j).getChildNodes().item(0).getNodeValue()).append("\n");
                            }
                        }
                    }
                }
            } catch (ParserConfigurationException e) {
                mensaje.append("Error al procesar el archivo.");
            } catch (IOException e) {
                mensaje.append("Error al leer el archivo");
            } catch (SAXException e) {
                mensaje.append("Error al procesar el archivo");
            }
        }
        return mensaje.toString();
    }
    public String leerNodosConClases(Path p) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        StringBuilder mensaje = new StringBuilder("El archivo no se puede leer. ");
        if (Files.isReadable(p)) {
            mensaje = new StringBuilder();
            try {
                DocumentBuilder parser = factory.newDocumentBuilder();
                Document document = parser.parse(p.toFile());
                //ahora ya accedemos al contenido del xml, con nodos o con elementos
                Element elRaiz = document.getDocumentElement();
                NodeList marcas = elRaiz.getElementsByTagName("marca");
                NodeList modelos = elRaiz.getElementsByTagName("modelo");
                NodeList cilindradas = elRaiz.getElementsByTagName("cilindrada");
                NodeList coches = elRaiz.getElementsByTagName("coche");
                Concesionario concesionario= new Concesionario("Mi Concesionario");
                Coche cocheAux;
                concesionario.setCoches(new ArrayList<Coche>());
                for (int i=0; i<marcas.getLength();i++){
                    cocheAux = new Coche();
                    Element coche = (Element) coches.item(i);
                    cocheAux.setId(Integer.valueOf(coche.getAttribute("id")));
                    cocheAux.setMarca(marcas.item(i).getTextContent());
                    cocheAux.setModelo(modelos.item(i).getTextContent());
                    cocheAux.setCilindrada(Double.parseDouble(cilindradas.item(i).getTextContent()));
                    concesionario.getCoches().add(cocheAux);
                }
                mensaje.append(concesionario.toString());
            return(mensaje.toString());
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
        } else{
            mensaje.append("No se tiene acceso de lectura al fichero.");
        }
        return mensaje.toString();
    }
}

