package com.dautev.courseware.xml;

import com.dautev.courseware.xml.domain.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

@RestController
public class CoursewareApi {
    private static final LinkedHashMap<Date,Message> messages = new LinkedHashMap<Date, Message>();
    static {
        messages.put(new Date(), new Message(new Date(), "Andrey", "You will respect my Authoritaaay!"));
    }
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ArrayList<Message> getMessages() {
        return new ArrayList<Message>(messages.values());
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public ResponseEntity postMessage(@RequestBody String message) throws UnsupportedEncodingException {
        //Serialization intentionally placed here (:
        message = URLDecoder.decode(message, "UTF-8");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(message)));

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaFile = new StreamSource(Thread.currentThread().
                    getContextClassLoader().
                    getResourceAsStream("messageSchema.xsd"));
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));

            Message messageObject = new Message(
                    new Date(),
                    document.getElementsByTagName("user").item(0).getTextContent(),
                    document.getElementsByTagName("message").item(0).getTextContent()
            );
            messages.put(new Date(), messageObject);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
