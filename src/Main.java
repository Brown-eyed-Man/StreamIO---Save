import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(93, 3, 2, 124.13);
        GameProgress gameProgress2 = new GameProgress(6, 7, 5, 1755.69);
        GameProgress gameProgress3 = new GameProgress(55, 12, 8, 2669.26);

        File savegameDir = new File("D:/Games/savegames");
        File save1 = new File("D:/Games/savegames/save1.dat");
        File save2 = new File("D:/Games/savegames/save2.dat");
        File save3 = new File("D:/Games/savegames/save3.dat");
        save(save1, gameProgress1);
        save(save2, gameProgress2);
        save(save3, gameProgress3);

        List<String> saves = new ArrayList<>();
        for (File file : Objects.requireNonNull(savegameDir.listFiles())) {
            if (file.getName().endsWith(".dat")) {
                saves.add(file.getPath());
            }
        }

        zipFiles("D:/Games/savegames/Saves.zip", saves);

        for (File file : Objects.requireNonNull(savegameDir.listFiles())) {
            if (file.getName().endsWith(".dat")) {
                file.delete();
            }
        }
    }

    public static void save(File file, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipFile, List<String> saves) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (String file : saves) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    File fileToZip = new File(file);
                    ZipEntry entry = new ZipEntry(fileToZip.getName());
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}