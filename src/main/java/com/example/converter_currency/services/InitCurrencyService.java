package com.example.converter_currency.services;

import com.example.converter_currency.api.request.DataFromXml;
import com.example.converter_currency.models.CurrencyRate;
import com.example.converter_currency.repositories.CurrencyRateRepository;
import com.example.converter_currency.repositories.CurrencyRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class InitCurrencyService {
    private final static String CBR_URL = "http://www.cbr.ru/scripts/XML_daily.asp";

    @Bean
    ApplicationRunner init(CurrencyRateRepository currencyRateRepository, CurrencyRepository currencyRepository) {
        DataFromXml data = parseRates();
        return null;
    }

    private DataFromXml parseRates() {
        try {
            Document doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(CBR_URL);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("ValCurs");
            Node node = nodeList.item(0);
            LocalDate date = getLocalDate(node);
            List<Currency> currencies = new ArrayList<>();
            List<CurrencyRate> currencyRates = new ArrayList<>();





        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private LocalDate getLocalDate(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return LocalDate.parse((element.getAttribute("Date")), formatter);
        } else
            return null;
    }
}
