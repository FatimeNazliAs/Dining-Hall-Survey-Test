package dininghall.asstpackages.Exporter;

import TestModels.PdfModel;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.Data;
import dininghall.domain.document.Contract;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PdfGenerator {
    // static String importPdfLocation = "sozlesme2.pdf", exportPdfLocation = "edited.pdf";
    static String timesFontLocation = "D:\\DigiturkCoUk\\font\\times.ttf";
    static String importPdfLocation = "D:\\DigiturkCoUk\\pdf\\sozlesme.pdf",
            exportPdfLocation = "D:\\DigiturkCoUk\\pdf\\edited.pdf";

    private List<PdfModel> contract(Contract contract) {
        if (contract == null)
            return null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String formattedDate = format.format(date);
        List<PdfModel> pdfModels = new ArrayList<>();
        pdfModels.add(new PdfModel(170, 63, contract.getSozlesmeno() + "", "sozlesmeno", 1));
        pdfModels.add(new PdfModel(225, 98, contract.getAdsoyad(), "adsoyad", 1));
        if (contract.getIslemtarihi() != null)

            pdfModels.add(new PdfModel(283, 63, format.format(contract.getIslemtarihi()), "islemtarihi", 1));
        pdfModels.add(new PdfModel(498, 63, contract.getPaketadi(), "paketadi", 1));
        pdfModels.add(new PdfModel(440, 114, contract.getKimlikpassaport(), "kimlikpassaport", 1));
        pdfModels.add(new PdfModel(170, 135, contract.getDogumyeri(), "dogumyeri", 1));
        if (contract.getDogumtarihi() != null)

            pdfModels.add(new PdfModel(525, 135, format.format(contract.getDogumtarihi()) + "", "dogumtarihi", 1));


        pdfModels.add(new PdfModel(250, 152, contract.getSokak(), "sokak", 1));
        pdfModels.add(new PdfModel(525, 152, contract.getPostakodu() == null ? "" : contract.getPostakodu(), "postakodu", 1));
        pdfModels.add(new PdfModel(200, 170, contract.getSehir() == null ? "" : contract.getSehir(), "sehir", 1));

        pdfModels.add(new PdfModel(475, 170, contract.getÜlke(), "ülke", 1));
        pdfModels.add(new PdfModel(340, 190, contract.getTelefon() == null ? "" : contract.getTelefon(), "telefon", 1));
        pdfModels.add(new PdfModel(475, 190, contract.getEposta(), "eposta", 1));

        pdfModels.add(new PdfModel(490, 228, contract.getOnikiaylik() + "", "12aylik", 1));

        pdfModels.add(new PdfModel(205, 692, contract.getSmartkart(), "smartkart", 1));
        pdfModels.add(new PdfModel(520, 692, contract.getIpkutu(), "ipkutu", 1));
        if (contract.getSozlesmetarih() != null)
            pdfModels.add(new PdfModel(280, 780, format.format(contract.getSozlesmetarih()), "sozlesmetarih", 1));
        pdfModels.add(new PdfModel(520, 780, contract.getUyeimzaadsoyad(), "uyeimzaadsoyad", 1));
        return pdfModels;
    }


    public boolean pdfWriteParametre(Contract contract) {
        PdfStamper stamper = null;
        PdfReader reader = null;
        var pdfParatmetres = this.contract(contract);
        try {

            // Var olan PDF dosyasını okuma
            reader = new PdfReader(importPdfLocation);
            //  int pageNumber = 1;
            stamper = new PdfStamper(reader, new FileOutputStream(exportPdfLocation));
            //static parametrelere ekleme yapma
/*            AcroFields acroFields = stamper.getAcroFields();
            acroFields.setField("acrooo","Test");*/
            // Dinamik parametre ekleme

            BaseFont font = BaseFont.createFont(BaseFont.TIMES_ROMAN, "UTF-8", false);
            //BaseFont font = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, false);
            stamper.getOverContent(1).addTemplate(stamper.getImportedPage(reader, 1), 0, 0);
            //  Collections.sort(pdfParatmetres, (o1, o2) -> o1.getPageNum() - o2.getPageNum());
            for (var item : pdfParatmetres) {


                stamper.getOverContent(item.getPageNum()).beginText();
                // stamper.getOverContent(pageNumber).setFontAndSize(BaseFont.createFont(), 12);
                stamper.getOverContent(item.getPageNum()).setFontAndSize(font, 8);

                //x sağa doğru artan =nesne  100 verince solda 200 verince ortada 300 verince solda
                //y yukarı doğru artan =nesne  100 verince alta 200 verince ortada 300 verince üste
                //     stamper.getOverContent(pageNumber).setTextMatrix(x, y);
                //y bu yüzden 845-y olarak değiştirildi
                stamper.getOverContent(item.getPageNum()).setTextMatrix(item.getX(), 895 - item.getY());
                stamper.getOverContent(item.getPageNum()).showText(item.getValue());
                stamper.getOverContent(item.getPageNum()).endText();
            }
            stamper.close();
            reader.close();
            System.out.println("PDF dosyası düzenlendi");
            return true;
        } catch (IOException | DocumentException e) {
            System.out.println("PDF dosyası düzenlenemedi");
            e.printStackTrace();

        } finally {
            if (reader != null)
                reader.close();
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        }
    }

    public byte[] pdfWriteBaos(Contract contract) {
        PdfStamper stamper = null;
        PdfReader reader = null;
        var pdfParatmetres = this.contract(contract);

        try {

            // Var olan PDF dosyasını okuma
            reader = new PdfReader(importPdfLocation);
            //  int pageNumber = 1;
            var baos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, baos);
            //static parametrelere ekleme yapma
/*            AcroFields acroFields = stamper.getAcroFields();
            acroFields.setField("acrooo","Test");*/
            // Dinamik parametre ekleme

            //   BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, "UTF-8", false);
            BaseFont font = BaseFont.createFont(timesFontLocation,
                    BaseFont.IDENTITY_H,
                    BaseFont.NOT_EMBEDDED);
            stamper.getOverContent(1).addTemplate(stamper.getImportedPage(reader, 1), 0, 0);
            //  Collections.sort(pdfParatmetres, (o1, o2) -> o1.getPageNum() - o2.getPageNum());
            for (var item : pdfParatmetres) {


                stamper.getOverContent(item.getPageNum()).beginText();
                // stamper.getOverContent(pageNumber).setFontAndSize(BaseFont.createFont(), 12);
                stamper.getOverContent(item.getPageNum()).setFontAndSize(font, 8);

                //x sağa doğru artan =nesne  100 verince solda 200 verince ortada 300 verince solda
                //y yukarı doğru artan =nesne  100 verince alta 200 verince ortada 300 verince üste
                //     stamper.getOverContent(pageNumber).setTextMatrix(x, y);
                //y bu yüzden 845-y olarak değiştirildi
                stamper.getOverContent(item.getPageNum()).setTextMatrix(item.getX(), 895 - item.getY());
                stamper.getOverContent(item.getPageNum()).showText(item.getValue());
                stamper.getOverContent(item.getPageNum()).endText();
            }
            stamper.close();
            reader.close();
            System.out.println("PDF dosyası düzenlendi");
            return baos.toByteArray();
        } catch (IOException | DocumentException e) {
            System.out.println("PDF dosyası düzenlenemedi");
            e.printStackTrace();
            return null;

        } finally {
            if (reader != null)
                reader.close();
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

}
