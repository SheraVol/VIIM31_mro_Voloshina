import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;



public class Main  extends JPanel {
    private Polosa polosa;

    private final List<Tochka> tochki;

    public Main(Polosa polosi, List<Tochka> tochki) {
        this.polosa = polosi;
        this.tochki = tochki;
        setPreferredSize(new Dimension(800,600));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int centerPoX = getWidth() / 2;
        int centerPoY = getHeight() / 2;
        // Отрисовка полос
        g2d.setColor(Color.RED);
        int translatedX = (int) (polosa.getCenterX() + centerPoX);
        int translatedY = (int) (centerPoY - polosa.getCenterY());

        // Переводим координаты точки, на которую наклонена полоса
        int pivotedX = (int) (polosa.getWidth() / 2);
        int pivotedY = (int) (polosa.getLength() / 2);

        // Переводим угол наклона полосы
        double angle = -polosa.getAngle();
        g2d.rotate(angle, translatedX, translatedY);
        g2d.drawRect(translatedX - pivotedX, translatedY - pivotedY, (int) polosa.getWidth(), (int) polosa.getLength());
        g2d.setColor(Color.BLUE);
        for (Tochka tochka : tochki) {
            if (polosa.containsTochki(tochka)) {
                int translatedTochkaX = (int) (tochka.getCenterX() + centerPoX);
                int translatedTochkaY = (int) (centerPoY - tochka.getCenterY());
                g2d.fillOval(translatedTochkaX - 2, translatedTochkaY - 2, 5, 5);
            }
        }
    }

    public static void main(String[] args) {
        List<Polosa> polosi = readPolosiFromFile("polosi.txt");
        if (polosi.isEmpty()) {
            polosi = generatePolosiFromUserInput();
            savePolosiToFile("polosi.txt", polosi);
        }
        printPolosi(polosi);

    List<Tochka> tochki = readTochkiFromFile("tochki.txt");
        if (tochki.isEmpty()) {
        tochki = generateTochkiFromUserInput();
        saveTochkiToFile("tochki.txt", tochki);
    }
    printTochki(tochki);
        printTochkiInsidePolosi(polosi,tochki);
        Main mainPanel = new Main(polosi, tochki);

        JFrame frame = new JFrame("Polosa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
}


    public static List<Tochka> generateTochkiFromUserInput() {
        Scanner scanners = new Scanner(System.in);

        System.out.print("Введите количество точек: ");
        int counts = scanners.nextInt();

        return generateTochki(counts);
    }
    public static List<Tochka> generateTochki(int counts) {
        List<Tochka> tochki = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < counts; i++) {
            double centerX = random.nextDouble()*100;
            double centerY = random.nextDouble()*100;

            Tochka tochka = new Tochka(centerX, centerY);
            tochki.add(tochka);
        }

        return tochki;
    }
    public static List<Tochka> readTochkiFromFile(String filePath) {
        List<Tochka> tochki = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                double centerX = Double.parseDouble(parts[0]);
                double centerY = Double.parseDouble(parts[1]);

                Tochka tochka = new Tochka(centerX,centerY);
                tochki.add(tochka);
            }


        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Создайте новый файл.");
        }

        return tochki;
    }
    public static void saveTochkiToFile(String filePath, List<Tochka> tochki) {
        try {
            FileWriter writer = new FileWriter(filePath);
            PrintWriter printWriter = new PrintWriter(writer);

            for (Tochka tochka : tochki) {
                printWriter.println(tochka.centerX + "," + tochka.centerY );
            }
            printWriter.close(); // Закрытие PrintWriter
            writer.close(); // Закрытие FileWriter

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных в файл.");
        }
    }

    public static void printTochki(List<Tochka> tochki) {
        for (Tochka tochka : tochki) {
            System.out.println(tochka);
        }
    }
    public static List<Polosa> generatePolosiFromUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество полос: ");
        int count = scanner.nextInt();

        System.out.print("Введите угол наклона полос: ");
        double angle = scanner.nextDouble();

        System.out.print("Введите минимальную ширину полосы: ");
        double widthMin = scanner.nextDouble();

        System.out.print("Введите максимальную ширину полосы: ");
        double widthMax = scanner.nextDouble();

        System.out.print("Введите минимальную длину полосы: ");
        double lengthMin = scanner.nextDouble();

        System.out.print("Введите максимальную длину полосы: ");
        double lengthMax = scanner.nextDouble();


        return generatePolosi(count, angle, widthMin, widthMax, lengthMin, lengthMax);
    }

    public static List<Polosa> generatePolosi(int count, double angle, double widthMin, double widthMax,
                                              double lengthMin, double lengthMax) {
        List<Polosa> polosi = new ArrayList<>();
        Random random = new Random();

        for (int k = 0; k < count; k++) {
            double centerX = random.nextDouble()*100;
            double centerY = random.nextDouble()*100;
            double width = widthMin + (widthMax - widthMin) * random.nextDouble();
            double length = lengthMin + (lengthMax - lengthMin) * random.nextDouble();


            Polosa polosa = new Polosa(centerX, centerY, width, length, angle);
            polosi.add(polosa);

        }
        return polosi;
    }


    public static List<Polosa> readPolosiFromFile(String filePath) {
        List<Polosa> polosi = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                double centerX = Double.parseDouble(parts[0]);
                double centerY = Double.parseDouble(parts[1]);
                double width = Double.parseDouble(parts[2]);
                double length = Double.parseDouble(parts[3]);
                double angle = Double.parseDouble(parts[4]);

                Polosa polosa = new Polosa(centerX,centerY, width, length, angle);
                polosi.add(polosa);
            }


        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Создайте новый файл.");
        }

        return polosi;
    }

    public static void savePolosiToFile(String filePath, List<Polosa> polosi) {
        try {
            FileWriter writers = new FileWriter(filePath);
            PrintWriter printWriters = new PrintWriter(writers);

            for (Polosa polosa : polosi) {
                printWriters.println(polosa.centerX + "," + polosa.centerY + "," + polosa.width + "," + polosa.length  + "," + polosa.angle);
            }
            printWriters.close(); // Закрытие PrintWriter
            writers.close(); // Закрытие FileWriter

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных в файл.");
        }
    }

    public static void printPolosi(List<Polosa> polosi) {
        for (Polosa polosa : polosi) {
            System.out.println(polosa);
        }
    }

    public static void printTochkiInsidePolosi(List<Polosa> polosi, List<Tochka> tochki) {
            for (Tochka tochka : tochki) {
                for (Polosa polosa : polosi) {
                    if (polosa.containsTochki(tochka)) {
                        System.out.println("x: " + tochka.centerX + ", y: " + tochka.centerY);
                    }
                }
        }
    }

    }