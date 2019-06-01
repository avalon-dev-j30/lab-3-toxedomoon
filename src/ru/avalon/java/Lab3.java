package ru.avalon.java;

import ru.avalon.java.actions.FileContentAction;
import ru.avalon.java.actions.FileCopyAction;
import ru.avalon.java.actions.FileDeleteAction;
import ru.avalon.java.actions.FileMoveAction;
import ru.avalon.java.console.ConsoleUI;

import java.io.*;


/**
 * Лабораторная работа №3
 * <p>
 * Курс: "Программирование на платформе Java. Разработка
 * многоуровневых приложений"
 * <p>
 * Тема: "Потоки исполнения (Threads) и многозадачность" 
 *
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class Lab3 extends ConsoleUI<Commands> {

    public final static Object monitor = new Object();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String originFileName, targetDirectory;

    /**
     * Точка входа в приложение.
     * 
     * @param args 
     */
    public static void main(String[] args) {

        System.out.println("Введите в консоль `help` чтобы увидеть все команды..");
        new Lab3().run();
    }
    /**
     * Конструктор класса.
     * <p>
     * Инициализирует экземпляр базового типа с использоавнием
     * перечисления {@link Commands}.
     */
    Lab3() {
        super(Commands.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCommand(Commands command) throws IOException {
        try {
            synchronized (monitor) {
                switch (command) {
                    case copy:
                        /*
                         * TODO №6 Обработайте команду copy
                         */
                        System.out.println("Введите путь к исходному файлу который необходимо скопировать");
                        originFileName = reader.readLine();
                        System.out.println("Введите путь к целевому каталогу");
                        targetDirectory = reader.readLine();

                        FileCopyAction copy = new FileCopyAction(originFileName, targetDirectory);
                        copy.start();
                        monitor.wait();
                        break;
                    case move:
                        /*
                         * TODO №7 Обработайте команду move
                         */
                        System.out.println("Введите путь к исходному файлу для перемещения");
                        originFileName = reader.readLine();
                        System.out.println("Введите путь к целевому каталогу");
                        targetDirectory = reader.readLine();

                        FileMoveAction move = new FileMoveAction(originFileName, targetDirectory);
                        move.start();
                        monitor.wait();
                        break;
                    /**
                     * Добавил команду delete
                     */
                    case delete:
                        System.out.println("Введите путь к исходному файлу для удаления");
                        originFileName = reader.readLine();

                        FileDeleteAction delete = new FileDeleteAction(originFileName);
                        delete.start();
                        monitor.wait();
                        break;
                    /**
                     * Добавил команду `content` для вывода списка файлов в директории
                     */
                    case content:
                        System.out.println("Введите путь к исходному файлу для просмотра содержимого");
                        originFileName = reader.readLine();

                        FileContentAction ls = new FileContentAction(originFileName);
                        ls.start();
                        monitor.wait();
                        break;
                    /**
                     * Добавил конмаду `help` для вывода всех команд
                     */
                    case help:
                        System.out.println("copy \t\t- копирует файл в указанный каталог с тем же именем,\n" +
                                "\t\t\t  исходный файл сохраняется в исходном каталоге\n");
                        System.out.println("move \t\t- копирует файл в указанный каталог с тем же именем,\n" +
                                "\t\t\t  исходный файл удаляется в исходном каталоге\n");
                        System.out.println("delete \t\t- удаляет файл из каталога\n");
                        System.out.println("content \t- показывает все файлы и каталоги в указанном каталоге");
                        System.out.println("exit \t\t- закрыть приложение");
                        break;
                    case exit:
                        close();
                        break;
                    /*
                     * TODO №9 Обработайте необработанные команды
                     */
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
