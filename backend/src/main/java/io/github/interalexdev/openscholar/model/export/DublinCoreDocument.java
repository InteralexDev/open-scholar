package io.github.interalexdev.openscholar.model.export;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "oai_dc:dc")
public record DublinCoreDocument(

        @JacksonXmlProperty(isAttribute = true, localName = "xmlns:oai_dc")
        String xmlnsOaiDc,

        @JacksonXmlProperty(isAttribute = true, localName = "xmlns:dc")
        String xmlnsDc,

        @JacksonXmlProperty(isAttribute = true, localName = "xmlns:xsi")
        String xmlnsXsi,

        @JacksonXmlProperty(isAttribute = true, localName = "xsi:schemaLocation")
        String schemaLocation,

        @JacksonXmlProperty(localName = "dc:title")
        String title,

        @JacksonXmlProperty(localName = "dc:creator")
        @JacksonXmlElementWrapper(useWrapping = false)
        List<String> creators,

        @JacksonXmlProperty(localName = "dc:publisher")
        String publisher,

        @JacksonXmlProperty(localName = "dc:date")
        String date,

        @JacksonXmlProperty(localName = "dc:type")
        String type,

        @JacksonXmlProperty(localName = "dc:language")
        String language,

        @JacksonXmlProperty(localName = "dc:identifier")
        String identifier,

        @JacksonXmlProperty(localName = "dc:subject")
        @JacksonXmlElementWrapper(useWrapping = false)
        List<String> subjects,

        @JacksonXmlProperty(localName = "dc:source")
        String source
) {
}
