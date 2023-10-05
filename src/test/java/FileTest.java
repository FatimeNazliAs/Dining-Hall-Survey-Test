import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileTest
{
    // private static String HOME = System.getProperty("user.home");
    private static String HOME = "D:/AsstClinicImg";
    private static final String EMAIL_FILE_PATH = "D:\\DigiturkCoUk\\gmail\\template\\sozlesme.pdf";


    @Test
    public void pdfTest()
    {
        try
        {
            // Create a new document
            Document document = new Document();

            // Create a PdfWriter for the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(EMAIL_FILE_PATH));

            // Open the document
            document.open();

            // Add text to the document
            document.add(new Paragraph("This is some text that will be displayed in the PDF."));

            // Get the direct content of the PDF
            PdfContentByte directContent = writer.getDirectContent();

            // Set the font and size of the text
            directContent.setFontAndSize(BaseFont.createFont(), 12);

            // Insert text at a specific position
            directContent.beginText();
            directContent.showTextAligned(PdfContentByte.ALIGN_LEFT, "Inserted Text", 50, 750, 0);
            directContent.endText();

            // Close the document
            document.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void pdfEditTest()
    {
        try
        {
            // Create a PdfReader for the PDF file
            PdfReader reader = new PdfReader(EMAIL_FILE_PATH);

            // Create a PdfStamper for the PDF file
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("D:\\DigiturkCoUk\\gmail\\template\\output.pdf"));

            // Get the fields from the PDF
            AcroFields acroFields = stamper.getAcroFields();

            Map<String, AcroFields.Item> fields = acroFields.getFields();

            fields.entrySet().forEach(entry -> {
                System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            });


            // Close the stamper
            stamper.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Test
    public void pdfEditComment()
    {
        // Open the PDF file
        PdfReader reader = null;
        try
        {
            reader = new PdfReader("D:\\DigiturkCoUk\\gmail\\template\\sozlesme.pdf");

            // For each PDF page
            for (int i = 1; i <= reader.getNumberOfPages(); i++)
            {

                // Get a page a PDF page
                PdfDictionary page = reader.getPageN(i);
                // Get all the annotations of page i
                PdfArray annotsArray = page.getAsArray(PdfName.ANNOTS);

                // If page does not have annotations
                if (page.getAsArray(PdfName.ANNOTS) == null)
                {
                    continue;
                }

                // For each annotation
                for (int j = 0; j < annotsArray.size(); ++j)
                {
                    // For current annotation
                    PdfDictionary curAnnot = annotsArray.getAsDict(j);
                    // Check the annotation subtype and print its text if not null
                    writeAnnotation(curAnnot, reader, i);
                }
            }

            reader.close();


        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * Check the annotation subtype and print its text.
     *
     * @param annot      annotation to write.
     * @param reader     pdf document containing the annotation.
     * @param pageNumber pdf page number containing the annotation.
     * @throws IOException
     */
    public static void writeAnnotation(PdfDictionary annot, PdfReader reader, int pageNumber) throws IOException
    {

        if (annot == null)
        {
            return;
        }

//        System.out.print(annot.get(PdfName.SUBTYPE));
//        System.out.print(" -> Rect: " + annot.get(PdfName.RECT));

        PdfString text = null;
        boolean mayHaveTextAnnotated = false;

        // Highlights with comments (balloons) and Highliths
        if (PdfName.HIGHLIGHT.equals(annot.get(PdfName.SUBTYPE)))
        {
            // Only Highlights with comments may have text
            text = (PdfString) annot.get(PdfName.CONTENTS);
            mayHaveTextAnnotated = true;
        } else if (PdfName.UNDERLINE.equals(annot.get(PdfName.SUBTYPE)))
        {
            text = annot.getAsString(PdfName.CONTENTS);
            mayHaveTextAnnotated = true;
            // A comment (a balloon with a comment)
        } else if (PdfName.TEXT.equals(annot.get(PdfName.SUBTYPE)))
        {
            text = annot.getAsString(PdfName.CONTENTS);
        } else
        {
            text = annot.getAsString(PdfName.CONTENTS);
        }

        if (text != null)
        {
            System.out.println(" -> " + text);
        }

        if (mayHaveTextAnnotated)
        {
            PdfArray rectangle = (PdfArray) annot.get(PdfName.RECT); // ex: [82.1569, 757.575, 124.395, 769.305]
            String textHighlighted = getTextFromRectangle(rectangle, reader, pageNumber);
            if (textHighlighted != null)
            {
                System.out.println(" Annotated text -> " + textHighlighted);
            }
        }
    }

    /**
     * Extracts the text {@code rectangle}, located on page {@code pageNumber}
     * of the pdf {@code reader}.
     * <p>The text extracted by this method is not perfect. Usually extracts an
     * unnecessary (not desirable) extra character. For instance, for an annotations
     * like "[This is an annotated] text", the method will extract
     * "This is an annotated t". </p> The extra character may appear before or
     * after the annotated text.
     *
     * @param reader
     * @param pageNumber
     * @param rectangle  ex: [82.1569, 757.575, 124.395, 769.305]
     * @return the extracted text or null.
     * @throws IOException
     */
    public static String getTextFromRectangle(PdfArray rectangle,
                                              PdfReader reader, int pageNumber) throws IOException
    {

        if (rectangle == null)
        {
            return null;
        }

        // Get the retangle coodinates
        float llx = rectangle.getAsNumber(0).floatValue();
        float lly = rectangle.getAsNumber(1).floatValue();
        float urx = rectangle.getAsNumber(2).floatValue();
        float ury = rectangle.getAsNumber(3).floatValue();

        Rectangle rect = new Rectangle(llx, lly, urx, ury);
        RenderFilter filter = new RegionTextRenderFilter(rect);
        TextExtractionStrategy strategy =
                new FilteredTextRenderListener(
                        new LocationTextExtractionStrategy(), filter);

        return PdfTextExtractor.getTextFromPage(reader, pageNumber, strategy);
    }

    @Test
    public void htmlFileTest()
    {
        String content = "";

        try
        {
            content = new String(Files.readAllBytes(Paths.get(EMAIL_FILE_PATH)), StandardCharsets.UTF_8);

            content = content.replace("!!extract!!", "tolga");

            System.out.println(content);
        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @Test
    public void createDateBasedDirectory()
    {
        String newDir = null;

        String baseDirectory = HOME;
        Date argDate = new Date();

        if (baseDirectory != null && argDate != null)
        {
            try
            {
                String format = "yyyy-MM-dd";

                DateFormat dateFormatter = new SimpleDateFormat(format);

                String date = dateFormatter.format(argDate);

                File f = new File(baseDirectory);

                String dir = null;

                newDir = baseDirectory + "/" + date;

                Path p = Paths.get(newDir);

                // assertTrue(Files.exists(p));

                if (!Files.exists(p))
                {
                    new File(newDir).mkdir();
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void givenExistentPath_whenConfirmsFileExists_thenCorrect()
    {
        Path p = Paths.get(HOME);

        assertTrue(Files.exists(p));
    }

    @Test
    public void givenNonexistentPath_whenConfirmsFileNotExists_thenCorrect()
    {
        Path p = Paths.get(HOME + "/inexistent_file.txt");

        System.out.println(p);

        assertTrue(Files.notExists(p));
    }

    @Test
    public void givenDirPath_whenConfirmsNotRegularFile_thenCorrect()
    {
        Path p = Paths.get(HOME);

        assertFalse(Files.isRegularFile(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsReadable_thenCorrect()
    {
        Path p = Paths.get(HOME);

        assertTrue(Files.isReadable(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsWritable_thenCorrect()
    {
        Path p = Paths.get(HOME);

        assertTrue(Files.isWritable(p));
    }

    @Test
    public void givenExistentDirPath_whenConfirmsExecutable_thenCorrect()
    {
        Path p = Paths.get(HOME);
        assertTrue(Files.isExecutable(p));
    }

    @Test
    public void givenSameFilePaths_whenConfirmsIsSame_thenCorrect() throws IOException
    {
        Path p1 = Paths.get(HOME);
        Path p2 = Paths.get(HOME);

        assertTrue(Files.isSameFile(p1, p2));
    }

    @Test
    public void givenFilePath_whenCreatesNewFile_thenCorrect() throws IOException
    {
        String fileName = "myfile_" + UUID.randomUUID().toString() + ".txt";
        Path p = Paths.get(HOME + "/" + fileName);
        assertFalse(Files.exists(p));

        Files.createFile(p);

        assertTrue(Files.exists(p));
    }

    @Test
    public void givenDirPath_whenCreatesNewDir_thenCorrect() throws IOException
    {
        String dirName = "myDir_" + UUID.randomUUID().toString();
        Path p = Paths.get(HOME + "/" + dirName);
        assertFalse(Files.exists(p));

        Files.createDirectory(p);

        assertTrue(Files.exists(p));
        assertFalse(Files.isRegularFile(p));
        assertTrue(Files.isDirectory(p));
    }

    @Test
    public void givenDirPath_whenFailsToCreateRecursively_thenCorrect() throws IOException
    {
        String dirName = "myDir_" + UUID.randomUUID().toString() + "/subdir";
        Path p = Paths.get(HOME + "/" + dirName);
        assertFalse(Files.exists(p));

        Files.createDirectory(p);
    }

    @Test
    public void givenDirPath_whenCreatesRecursively_thenCorrect() throws IOException
    {
        Path dir = Paths.get(
                HOME + "/myDir_" + UUID.randomUUID().toString());
        Path subdir = dir.resolve("subdir");
        assertFalse(Files.exists(dir));
        assertFalse(Files.exists(subdir));


        Files.createDirectories(subdir);

        assertTrue(Files.exists(dir));
        assertTrue(Files.exists(subdir));
    }

    @Test
    public void givenFilePath_whenCreatesTempFile_thenCorrect() throws IOException
    {
        String prefix = "log_";
        String suffix = ".txt";
        Path p = Paths.get(HOME + "/");

        Files.createTempFile(p, prefix, suffix);

        assertTrue(Files.exists(p));
    }

    @Test
    public void givenPath_whenCreatesTempFileWithDefaults_thenCorrect() throws IOException
    {
        Path p = Paths.get(HOME + "/");

        Files.createTempFile(p, null, null);

        assertTrue(Files.exists(p));
    }

    @Test
    public void givenNoFilePath_whenCreatesTempFileInTempDir_thenCorrect() throws IOException
    {
        Path p = Files.createTempFile(null, null);

        assertTrue(Files.exists(p));
    }

    @Test
    public void givenPath_whenDeletes_thenCorrect()
    {
        Path p = Paths.get(HOME + "/fileToDelete.txt");
        assertFalse(Files.exists(p));
        try
        {
            Files.createFile(p);
        } catch (IOException ioe)
        {

        }

        assertTrue(Files.exists(p));

        try
        {
            Files.delete(p);
        } catch (IOException ioe)
        {

        }

        assertFalse(Files.exists(p));
    }

    public void givenInexistentFile_whenDeleteFails_thenCorrect()
    {
        Path p = Paths.get(HOME + "/inexistentFile.txt");
        assertFalse(Files.exists(p));

        try
        {
            Files.delete(p);
        } catch (IOException ioe)
        {

        }

    }

    @Test
    public void givenInexistentFile_whenDeleteIfExistsWorks_thenCorrect()
    {
        Path p = Paths.get(HOME + "/inexistentFile.txt");
        assertFalse(Files.exists(p));

        try
        {
            Files.deleteIfExists(p);
        } catch (IOException ioe)
        {

        }

    }

    public void givenPath_whenFailsToDeleteNonEmptyDir_thenCorrect()
    {
        Path dir = Paths.get(
                HOME + "/emptyDir" + UUID.randomUUID().toString());

        try
        {
            Files.createDirectory(dir);
        } catch (IOException ioe)
        {

        }

        assertTrue(Files.exists(dir));

        Path file = dir.resolve("file.txt");

        try
        {
            Files.createFile(file);
        } catch (IOException ioe)
        {

        }

        try
        {
            Files.delete(dir);
        } catch (IOException ioe)
        {

        }

        assertTrue(Files.exists(dir));
    }

    @Test
    public void givenFilePath_whenCopiesToNewLocation_thenCorrect()
    {
        Path dir1 = Paths.get(
                HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(
                HOME + "/otherdir_" + UUID.randomUUID().toString());

        try
        {
            Files.createDirectory(dir1);
        } catch (IOException ioe)
        {

        }

        try
        {
            Files.createDirectory(dir2);
        } catch (IOException ioe)
        {

        }

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");

        try
        {
            Files.createFile(file1);
        } catch (IOException ioe)
        {

        }

        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));

        try
        {
            Files.copy(file1, file2);
        } catch (IOException ioe)
        {

        }

        assertTrue(Files.exists(file2));
    }

    @Test
    public void givenPath_whenCopyFailsDueToExistingFile_thenCorrect() throws IOException
    {
        Path dir1 = Paths.get(
                HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(
                HOME + "/otherdir_" + UUID.randomUUID().toString());

        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");

        Files.createFile(file1);
        Files.createFile(file2);

        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));

        Files.copy(file1, file2);

        Files.copy(file1, file2, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void givenFilePath_whenMovesToNewLocation_thenCorrect() throws IOException
    {
        Path dir1 = Paths.get(
                HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(
                HOME + "/otherdir_" + UUID.randomUUID().toString());

        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);

        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));

        Files.move(file1, file2);

        assertTrue(Files.exists(file2));
        assertFalse(Files.exists(file1));
    }

    @Test
    public void givenFilePath_whenMoveFailsDueToExistingFile_thenCorrect() throws IOException
    {
        Path dir1 = Paths.get(
                HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(
                HOME + "/otherdir_" + UUID.randomUUID().toString());

        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");

        Files.createFile(file1);
        Files.createFile(file2);

        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));

        Files.move(file1, file2);

        Files.move(file1, file2, StandardCopyOption.REPLACE_EXISTING);

        assertTrue(Files.exists(file2));
        assertFalse(Files.exists(file1));
    }

}
