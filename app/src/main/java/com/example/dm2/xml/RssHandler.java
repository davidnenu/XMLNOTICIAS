package com.example.dm2.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;
public class RssHandler extends DefaultHandler{
    private List<Web> webs;
    private Web webActual;
    private StringBuilder sbTexto;
    public List<Web> getWebs(){
        return webs;
    }
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        if (this.webActual != null)
            sbTexto.append(ch, start, length);
    }
    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        super.endElement(uri, localName, name);
        if (this.webActual != null) {
            if (localName.equals("title")) {
                webActual.setNombre(sbTexto.toString());
            } else if (localName.equals("link")) {
                webActual.setUrl(sbTexto.toString());
            }
            else if (localName.equals("item")) {
                webs.add(webActual);
            }

            sbTexto.setLength(0);
        }
    }
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        webs = new ArrayList<Web>();
        sbTexto = new StringBuilder();
    }
    @Override
    public void startElement(String uri, String localName,
                             String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equals("item")) {
           webActual = new Web();
        }
    }
}

