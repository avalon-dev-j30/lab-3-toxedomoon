package ru.avalon.java.actions;

import ru.avalon.java.Lab3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDeleteAction implements Action {

    private Path originPath;

    public FileDeleteAction(String originFileName) {
        this.originPath = Paths.get(originFileName);
    }

    @Override
    public void run() {
        synchronized (Lab3.monitor) {
            try {
                delete();
            } catch (IOException e) {
                System.out.println("Файл не удален!..");
                e.printStackTrace();
            }
            Lab3.monitor.notifyAll();
        }
    }

    private void delete() throws IOException {

        if (!Files.exists(originPath)) {
            System.out.println("Файл не найден!..");
        } else {
            Files.delete(originPath);
            System.out.println("Файл удален!..");
        }
    }

    @Override
    public void close() throws Exception {

    }
}
