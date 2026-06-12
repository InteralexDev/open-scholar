package io.github.interalexdev.openscholar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.interalexdev.openscholar.mapper.DublinCoreMapper;
import io.github.interalexdev.openscholar.model.PublicationMetadata;
import io.github.interalexdev.openscholar.model.export.DublinCoreDocument;
import org.springframework.stereotype.Service;

@Service
public class DublinCoreExportService {

    private final DublinCoreMapper dublinCoreMapper;
    private final XmlMapper xmlMapper;

    public DublinCoreExportService(DublinCoreMapper dublinCoreMapper, XmlMapper xmlMapper) {
        this.dublinCoreMapper = dublinCoreMapper;
        this.xmlMapper = xmlMapper;
    }

    public String export(PublicationMetadata metadata) {
        DublinCoreDocument document =
                dublinCoreMapper.toDublinCoreDocument(metadata);

        try {
            return xmlMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(document);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to export publication as Dublin Core XML", e);
        }
    }
}