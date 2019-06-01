package ru.avalon.java.actions;

import ru.avalon.java.Lab3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileContentAction implements Action {

    private final Path originPath;

    public FileContentAction(String originFileName) {
        this.originPath = Paths.get(originFileName);
    }


    @Override
    public void run() {
        synchronized (Lab3.monitor) {
            try {
                lookContent();
            } catch (IOException e) {
                System.out.println("Нет файлов в каталоге..");
                e.printStackTrace();
            }
            Lab3.monitor.notifyAll();
        }
    }

    private void lookContent() throws IOException {
        if (!Files.exists(originPath)) {
            System.out.println("Каталог не найден!..");
        } else {
            System.out.println("Файлы в каталоге " + originPath.getFileName() + ": ");

            DirectoryStream<Path> stream = Files.newDirectoryStream(originPath);
            for (Path files : stream) {
                System.out.print(files.getFileName() + "\t\t");
            }
            System.out.println();

            stream.close();
        }
    }

    @Override
    public void close() throws Exception {

    }


}
