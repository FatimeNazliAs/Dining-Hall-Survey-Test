package dininghall.common;

import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;


public class FileHelper implements Serializable {
    private static final String HOME = "D:/DigiturkCoUk/content";

    private static final String PRODUCT_THUMBNAIL_PATH = "D:/DigiturkCoUk/thumbnail";

    public static String getHOME() {
        return HOME;
    }

    public static String getPRODUCT_THUMBNAIL_PATH() {
        return PRODUCT_THUMBNAIL_PATH;
    }

    public String createDateBasedDirectory(Date argDate, String prefix) {
        String newDir = null;

        if (HOME != null && argDate != null) {
            try {
                String format = "yyyy-MM-dd";

                DateFormat dateFormatter = new SimpleDateFormat(format);

                String date = dateFormatter.format(argDate);

                File f = new File(HOME);

                String dir = null;

                newDir = HOME + "/" + prefix + "/" + date;

                Path p = Paths.get(newDir);

                if (!Files.exists(p)) {
                    new File(newDir).mkdirs();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return newDir;

    }

    public String createDateBasedDirectory(Date argDate, String prefix, String suffix) {
        String newDir = null;

        if (HOME != null && argDate != null) {
            try {
                String format = "yyyy-MM-dd";

                DateFormat dateFormatter = new SimpleDateFormat(format);

                String date = dateFormatter.format(argDate);

                File f = new File(HOME);

                String dir = null;

                newDir = HOME + "/" + prefix + "/" + date + "/" + suffix;

                Path p = Paths.get(newDir);

                if (!Files.exists(p)) {
                    new File(newDir).mkdirs();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return newDir;

    }

    public String saveFile(UploadedFile uploadedFile, String prefix) {
        String folderPath = createDateBasedDirectory(new Date(), prefix);

        String fileName = null;
        String fullPath = null;

        boolean done = false;
        while (!done) {
            fileName = UUID.randomUUID().toString();
            fullPath = folderPath + "/" + fileName + ".pdf";

            if (!checkFileIsExist(fullPath)) {
                done = true;
            }
        }

        try (InputStream input = uploadedFile.getInputStream()) {
            Path p = Paths.get(fullPath);

            Files.createFile(p);

            Files.copy(input, p, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return fullPath;
    }

    public String saveFile(UploadedFile uploadedFile, String prefix, String suffix, String extension) {
        String folderPath = createDateBasedDirectory(new Date(), prefix, suffix);

        String fileName = null;
        String fullPath = null;

        boolean done = false;
        while (!done) {
            fileName = UUID.randomUUID().toString();
            fullPath = folderPath + "/" + fileName + extension;

            if (!checkFileIsExist(fullPath)) {
                done = true;
            }
        }

        try (InputStream input = uploadedFile.getInputStream()) {
            Path p = Paths.get(fullPath);

            Files.createFile(p);

            Files.copy(input, p, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return fullPath;
    }

    public boolean checkFileIsExist(String fileName) {
        Path p = Paths.get(fileName);

        if (Files.exists(p)) {
            return true;
        }

        return false;
    }

    public boolean deleteFile(String filePath) {
        Path p = Paths.get(filePath);
        try {
            if (Files.exists(p)) {
                Files.delete(p);
            }

            return true;

        } catch (IOException ioe) {

        }

        return false;
    }

    public static boolean deleteDirectory(String dir) {

        Path path = Paths.get(dir);

        // read java doc, Files.walk need close the resources.
        // try-with-resources to ensure that the stream's open directories are closed
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                    .forEach(FileHelper::deleteDirectoryExtract);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    // extract method to handle exception in lambda
    public static void deleteDirectoryExtract(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.err.printf("Unable to delete this path : %s%n%s", path, e);
        }
    }

}
