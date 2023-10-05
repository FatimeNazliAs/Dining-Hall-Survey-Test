import TestModels.PdfModel;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.primefaces.model.DefaultStreamedContent;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class PdfTest {

    public static void main(String[] args) throws IOException {
    String a_a="";
        FileInputStream file = new FileInputStream(new File("testt.xlsx"));
        InputStream stream = new FileInputStream("sozlesme.pdf");
        byte[] asd=stream.readAllBytes();
      var base64Pdf= Base64.getEncoder().encodeToString(asd);
         stream=stream;
   /*     a_a= file.readAllBytes()+"";
        DefaultStreamedContent pdf = DefaultStreamedContent.builder()
                .contentType("application/pdf")
                .name("sozlesme.pdf")
                .stream(() -> stream)
                .build();*/
int a=0;
    }
    public static void sozlesmetest(){
        List<PdfModel> pdfModels=new ArrayList<>();
        pdfModels.add(new PdfModel(170,63,"111222333","sozlesmeno",1));
        pdfModels.add(new PdfModel(225,98,"Murat Kaya","adsoyad",1));
        pdfModels.add(new PdfModel(283,63,"04.02.2022","islemtarihi",1));
        pdfModels.add(new PdfModel(498,63,"UYDU SPOR PAKETİ","paketadi",1));
        pdfModels.add(new PdfModel(440,114,"10121215451","kimlikpassaport",1));
        pdfModels.add(new PdfModel(170,135,"ADANA","dogumyeri",1));
        pdfModels.add(new PdfModel(525,135,"02.05.1900","dogumtarihi",1));


        pdfModels.add(new PdfModel(250,152,"1024 SOKAK ","sokak",1));
        pdfModels.add(new PdfModel(525,152,"01000","postakodu",1));
        pdfModels.add(new PdfModel(200,170,"ADANA","sehir",1));

        pdfModels.add(new PdfModel(475,170,"TÜRKIYE","ülke",1));
        pdfModels.add(new PdfModel(340,190,"054548111111","telefon",1));
        pdfModels.add(new PdfModel(475,190,"muratkaya@gmail.com","eposta",1));

        pdfModels.add(new PdfModel(490,228,"199.00","12aylik",1));

        pdfModels.add(new PdfModel(205,692,"423456789","smartkart",1));
        pdfModels.add(new PdfModel(520,692,"567890345","ipkutu",1));
        pdfModels.add(new PdfModel(280,780,"04.02.2022","sozlesmetarih",1));
        pdfModels.add(new PdfModel(520,780,"Murat Kaya","uyeimzaadsoyad",1));
        pdfWriteParametre("sozlesme.pdf","edited.pdf",pdfModels);

    }
    public static void pdfWriteParametre(String importPdfLocation, String exportPdfLocation,List<PdfModel> pdfParatmetres)
    {
        try {
            // Var olan PDF dosyasını okuma
            PdfReader reader = new PdfReader(importPdfLocation);
            //  int pageNumber = 1;
            var stamper = new PdfStamper(reader, new FileOutputStream(exportPdfLocation));

            // Dinamik parametre ekleme

            BaseFont font = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, false);
            stamper.getOverContent(1).addTemplate(stamper.getImportedPage(reader, 1), 0, 0);
          //  Collections.sort(pdfParatmetres, (o1, o2) -> o1.getPageNum() - o2.getPageNum());
            for (var item:pdfParatmetres) {


            stamper.getOverContent(item.getPageNum()).beginText();
            // stamper.getOverContent(pageNumber).setFontAndSize(BaseFont.createFont(), 12);
            stamper.getOverContent(item.getPageNum()).setFontAndSize(font, 8);

            //x sağa doğru artan =nesne  100 verince solda 200 verince ortada 300 verince solda
            //y yukarı doğru artan =nesne  100 verince alta 200 verince ortada 300 verince üste
       //     stamper.getOverContent(pageNumber).setTextMatrix(x, y);
            //y bu yüzden 845-y olarak değiştirildi
            stamper.getOverContent(item.getPageNum()).setTextMatrix(item.getX(), 895-item.getY());
            stamper.getOverContent(item.getPageNum()).showText(item.getValue());
            stamper.getOverContent(item.getPageNum()).endText();
            }
            stamper.close();
            reader.close();
            System.out.println("PDF dosyası düzenlendi");
        } catch (IOException | DocumentException e) {
            System.out.println("PDF dosyası düzenlenemedi");
            e.printStackTrace();
        }
    }
}
