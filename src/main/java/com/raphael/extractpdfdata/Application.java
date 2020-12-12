package com.raphael.extractpdfdata;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class Application {

    @RequestMapping(value = "/pdfData", method = RequestMethod.GET)
    public String extrairDados() {
        try (PDDocument pdfDocument = PDDocument.load(new File("src/main/resources/teste.pdf"))) {

            pdfDocument.getClass();

            if (!pdfDocument.isEncrypted()) {

                PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
                pdfTextStripperByArea.setSortByPosition(Boolean.TRUE);

                PDFTextStripper pdfTextStripper = new PDFTextStripper();

                return pdfTextStripper.getText(pdfDocument);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/pdfDataArea", method = RequestMethod.GET, produces = "application/json")
    public List<Dados> extrairDadosByArea() {
        List<Dados> listaDados = new ArrayList<>();
        try (PDDocument document = PDDocument.load(new File("src/main/resources/teste.pdf"))) {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            Rectangle rect = new Rectangle(472, 20, 101, 39);
            Rectangle rect2 = new Rectangle(364, 331, 84, 22);
            Rectangle rect3 = new Rectangle(450, 352, 124, 23);
            stripper.addRegion("numeroNfe", rect);
            stripper.addRegion("tributos", rect2);
            stripper.addRegion("valorNota", rect3);
            PDPage firstPage = document.getPage(0);
            stripper.extractRegions(firstPage);
            listaDados.add(new Dados(
                    rect.toString(),
                    stripper.getTextForRegion("numeroNfe")
                            .replace("\n", " ")));
            listaDados.add(new Dados(
                    rect2.toString(),
                    stripper.getTextForRegion("tributos")
                            .replace("\n", " ")));
            listaDados.add(new Dados(
                    rect3.toString(),
                    stripper.getTextForRegion("valorNota")
                            .replace("\n", " ")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaDados;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
