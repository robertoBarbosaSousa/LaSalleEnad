package com.lasalle.perguntasenad.presenter.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.util.Log;

/**
 * @author lennon.chaves
 */
public final class XMLUtil {

    private XMLUtil() {
    }

    /**
     * Recupera o document pelo input stream.
     * 
     * @param svgInputStream
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document getDocumentByInputStream( InputStream svgInputStream ) throws ParserConfigurationException,
                                                                                 SAXException, IOException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

        Document doc = docBuilder.parse( svgInputStream );
        doc.getDocumentElement().normalize();
        return doc;
    }

    /**
     * Transforma Document em String.
     * 
     * @param doc
     * @return
     */
    public static String xmlDocumentToString( Document doc ) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "no" );
            transformer.setOutputProperty( OutputKeys.METHOD, "xml" );
            transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
            transformer.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );

            transformer.transform( new DOMSource( doc ), new StreamResult( sw ) );
            return sw.toString();
        } catch ( Exception ex ) {
            Log.e( XMLUtil.class.getName(), ex.getClass().getName() );
            return null;
        }
    }
}
