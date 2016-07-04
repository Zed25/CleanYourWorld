/*
 * Created by UFOS from Sasha
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.PlaceSelectedParser
 * Last modified: 7/2/16 12:50 PM
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

public class PlaceSelectedParser {

    /**
     * Tag XML di Google
     */
    private static final String STATUS = "status";
    private static final String PLACE_ID = "place_id";
    private static final String ID = "id";
    private static final String REFERENCE = "reference";
    private static final String RESULT = "result";
    private static final String GEOMETRY = "geometry";

    /**
     * Stato della risposta di Google
     */
    private static final String STATUS_OK = "OK";
    private static final String STATUS_ZERO_RESULT = "ZERO_RESULT";
    private static final String STATUS_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    private static final String STATUS_REQUEST_DENIED = "REQUEST_DENIED";
    private static final String STATUS_INVALID_REQUEST = "INVALID_REQUEST";

    /**
     * Attributi "locali"
     */
    private String status = "";


    // TODO: 05/01/16 verificare lo stato "il risultato" della connessione


    private ArrayList<PlaceSelectedItem> placeSelectedObjects = new ArrayList<PlaceSelectedItem>();                                           //struttura dati che immagazzinerà i dati letti

    public ArrayList<PlaceSelectedItem> getRadarPlaceSearchObjects() {                                                   //metodo di accesso alla struttura dati
        return placeSelectedObjects;
    }

    /**
     * @param xmlUrl: link del sito
     */
    public void parseXml(String xmlUrl) {
        // TODO: 06/01/16 scegliere il tipo di uscita della funzione
        Document xmlDocument;
        try {
            xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(xmlUrl).openStream());
            Element root = xmlDocument.getDocumentElement();
            NodeList rootChildNodes = root.getChildNodes();
            int len = rootChildNodes.getLength();
            for (int i = 0; i < rootChildNodes.getLength(); i++) {
                Node node = rootChildNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getNodeName().equals(STATUS))
                        setStatus(element.getTextContent());
                    if (status.equals(STATUS_OK) & element.getNodeName().equals(RESULT)) {
                        placeSelectedObjects.add(getPlaceSelectedItem(element));
                    } else if (status.equals(STATUS_ZERO_RESULT)) {
                        // TODO: 06/01/16 comunica l'impossibilità di effettuare la ricerca
                    } else {
                        // TODO: 06/01/16 interrompi la ricerca
                    }
                }
            }
        } catch (SAXException e) {
            eDebug(e.toString());
        } catch (IOException e) {
            eDebug(e.toString());
        } catch (ParserConfigurationException e) {
            eDebug(e.toString());
        } catch (FactoryConfigurationError e) {
            eDebug(e.toString());
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private PlaceSelectedItem getPlaceSelectedItem(Element element) {
        PlaceSelectedItem placeSelectedItem = new PlaceSelectedItem();
        if (element.getNodeName().equals(RESULT)) {
            NodeList result = element.getChildNodes();
            for (int i = 0; i < result.getLength(); i++) {
                Node child = result.item(i);
                if (child.getNodeName().equals(GEOMETRY)) {
                    String[] strings = child.getTextContent().trim().split("\n");
                    String lat = strings[0].trim();
                    String lng = strings[1].trim();
                    placeSelectedItem.setLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));

                } else if (child.getNodeName().equals(REFERENCE)) {
                    placeSelectedItem.setReference(child.getTextContent().trim());
                } else if (child.getNodeName().equals(ID)) {
                    placeSelectedItem.setId(child.getTextContent().trim());
                } else if (child.getNodeName().equals(PLACE_ID)) {
                    placeSelectedItem.setPlace_id(child.getTextContent().trim());
                }
            }

        }
        return placeSelectedItem;
    }

    static void eDebug(String debugString) {
        Log.e("RadarPlaceSearchParser", debugString + "\n");
    }
}
