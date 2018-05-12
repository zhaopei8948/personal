package online.zhaopei.personal.transformer;

import online.zhaopei.personal.configuration.PersonalProp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaopei on 18/5/10.
 */
@Component
public class CommonTransformer {

    private static final Log LOGGER = LogFactory.getLog(CommonTransformer.class);

    @Autowired
    private PersonalProp personalProp;

    private Map<Class<?>, Marshaller> marshallerMap = new HashMap<Class<?>, Marshaller>();

    public Object ByteArrayToObject(byte[] bytes) throws Exception {
        Assert.notNull(bytes, "bytes must not be null");
        Unmarshaller unmarshaller = this.getUnmarshaller();
        SAXParserFactory sax = SAXParserFactory.newInstance();
        sax.setNamespaceAware(false);
        XMLReader xmlReader = sax.newSAXParser().getXMLReader();
        Source source = new SAXSource(xmlReader, new InputSource(new ByteArrayInputStream(bytes)));

        //XMLReader reader = XMLReaderFactory.createXMLReader();
        //XMLFilterImpl nsfFilter = new XMLFilterImpl() {
        //    private boolean ignoreNamespace = true;

        //    @Override
        //    public void startDocument() throws SAXException {
        //        super.startDocument();
        //    }

        //    @Override
        //    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        //        if (this.ignoreNamespace) uri = "";
        //        super.startElement(uri, localName, qName, atts);
        //    }

        //    @Override
        //    public void endElement(String uri, String localName, String qName) throws SAXException {
        //        if (this.ignoreNamespace) uri = "";
        //        super.endElement(uri, localName, localName);
        //    }

        //    @Override
        //    public void startPrefixMapping(String prefix, String url) throws SAXException {
        //        if (!this.ignoreNamespace) super.startPrefixMapping("", url);
        //    }
        //};
        //nsfFilter.setParent(reader);
        //InputSource input = new InputSource(new ByteArrayInputStream(bytes));
        //SAXSource source = new SAXSource(nsfFilter, input);
        return this.getUnmarshaller().unmarshal(source);
        //return this.getUnmarshaller().unmarshal(new ByteArrayInputStream(bytes));
    }

    public Object StringToObject(String jaxbString) throws Exception {
        Assert.notNull(jaxbString, "jaxbString must not be null");
        return this.getUnmarshaller().unmarshal(new StringReader(jaxbString));
    }

    public <T> T StringToObject(String jaxbString, Class<T> clzz) throws Exception {
        return (T) this.getUnmarshaller(clzz).unmarshal(new StringReader(jaxbString));
    }

    public byte[] ObjectToByteArray(Object obj) throws Exception {
        Assert.notNull(obj, "obj must not be null");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.getMarshaller(obj.getClass()).marshal(obj, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String ObjectToString(Object obj, boolean formated) throws Exception {
        Assert.notNull(obj, "obj must not be null");
        StringWriter stringWriter = new StringWriter();
        this.getMarshaller(obj.getClass(), formated, true).marshal(obj, stringWriter);
        return stringWriter.toString();
    }

    private <T> Unmarshaller getUnmarshaller(Class<T> clzz) throws Exception {
        return JAXBContext.newInstance(clzz).createUnmarshaller();
    }

    private Unmarshaller getUnmarshaller() throws Exception {
        Assert.notNull(this.personalProp.getJaxbPackages(), "jaxbPackages must not be null");
        return JAXBContext.newInstance(this.personalProp.getJaxbPackages()).createUnmarshaller();
    }

    private Marshaller getMarshaller(Class clzz) throws Exception {
        return this.getMarshaller(clzz, this.personalProp.getJaxbFormated(), false);
    }

    private Marshaller getMarshaller(Class clzz, boolean formated, boolean fragment) throws Exception {
        if (!this.marshallerMap.containsKey(clzz)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(clzz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, this.personalProp.getCharset());
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formated);
            this.marshallerMap.put(clzz, marshaller);
        }
        return this.marshallerMap.get(clzz);
    }
}
