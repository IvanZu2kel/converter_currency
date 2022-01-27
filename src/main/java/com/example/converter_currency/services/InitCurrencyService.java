package com.example.converter_currency.services;

import com.example.converter_currency.api.request.DataFromXml;
import com.example.converter_currency.models.Currency;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class InitCurrencyService {
    private final static String CBR_URL = "http://www.cbr.ru/scripts/XML_daily.asp";

    /**
     * Инициализация на текущий день курсов валют
     *
     * @param currencyRateRepository
     * @param currencyRepository
     * @return
     */
    @Bean
    ApplicationRunner init(CurrencyRateRepository currencyRateRepository, CurrencyRepository currencyRepository) {
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findByDate(LocalDate.now());
        if (currencyRate.isEmpty()) {
            DataFromXml dataFromXml = parseRates();
            DataFromXml data = Optional.of(Objects.requireNonNull(dataFromXml)).orElseThrow();
            return init -> {
                currencyRepository.deleteAll();
                currencyRepository.save(new Currency("1", "111", "RUB", 1, "Российский рубль"));
                currencyRepository.saveAll(data.getCurrencies());
                currencyRateRepository.save(new CurrencyRate("1", null, "RUB", 1.0));
                currencyRateRepository.saveAll(data.getCurrencyRates());
            };
        } else return null;
    }

    private DataFromXml parseRates() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String format = sdf.format(new Date());
        try {
            Document doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(CBR_URL + "?date_req=" + format);
            doc.getDocumentElement().normalize();
            NodeList allNodes = doc.getElementsByTagName("ValCurs");
            Node node = allNodes.item(0);
            LocalDate date = getLocalDate(node);
            List<Currency> currencies = new ArrayList<>();
            List<CurrencyRate> currencyRates = new ArrayList<>();
            NodeList nodeList = doc.getElementsByTagName("Valute");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node n = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) n;
                    Currency currency = getCurrencyByElement(element);
                    currencies.add(currency);
                    CurrencyRate currencyRate = getCurrencyRateByElement(element, date);
                    currencyRates.add(currencyRate);
                }
            }
            return new DataFromXml()
                    .setCurrencies(currencies)
                    .setCurrencyRates(currencyRates);

        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CurrencyRate getCurrencyRateByElement(Element element, LocalDate date) throws ParseException {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);

        return new CurrencyRate()
                .setId(element.getAttribute("ID"))
                .setDate(date)
                .setCharCode(element
                        .getElementsByTagName("CharCode")
                        .item(0)
                        .getTextContent())
                .setRate((Double) df.parse((element.getElementsByTagName("Value").item(0).getTextContent())));
    }

    private Currency getCurrencyByElement(Element element) {
        return new Currency()
                .setId(element.getAttribute("ID"))
                .setName(element
                        .getElementsByTagName("Name")
                        .item(0)
                        .getTextContent())
                .setNumCode(element
                        .getElementsByTagName("NumCode")
                        .item(0)
                        .getTextContent())
                .setCharCode(element
                        .getElementsByTagName("CharCode")
                        .item(0)
                        .getTextContent())
                .setNominal(Integer.parseInt(element
                        .getElementsByTagName("Nominal")
                        .item(0)
                        .getTextContent()));
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
