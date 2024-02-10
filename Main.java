
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
//        String name = "Ivanov Ivan Ivanovich 15.01.1998 1234567 m";
        while (flag) {
            System.out.println("Формат данных для ввода <Фамилия> <Имя> <Отчество> <дата _ рождения> <номер _ телефона> <пол>");
            System.out.println("Пример: Иванов Иван Иванович 15.01.1980 1234567 m");
            System.out.print("Пожалуйста, введите новую запись строкой через пробелы: ");
            String name = scanner.nextLine();
            String[] newRecord = parse(name);
            switch (checkError(newRecord)) {
                case 1:
                    System.out.println("Error 1: не все поля записи заполнены, повторите попытку ");
                    break;
                case 2:
                    System.out.println("Error 2: в записи присутствуют лишние поля, повторите попытку");
                    break;
                case 3:
                    System.out.println("Error 3: использован некорректный формат даты рождения, используйте дд.мм.гггг");
                    break;
                case 4:
                    System.out.println("Error 4: введен некорректный номер телефона, повторите попытку");
                    break;
                case 5:
                    System.out.println("Error 5: введен неверный формат пола, используйте английские символы m или f");
                    break;
                case 0:
                    System.out.println(Arrays.toString(newRecord));
                    writeToFile(newRecord);
                    flag = false;
                    break;
                default:
            }
        }
    }

    static String[] parse(String record) {
        return record.split(" ");
    }

    static int checkError(String[] record) {
        if (record.length < 6) return 1;
        if (record.length > 6) return 2;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
        if (!pattern.matcher(record[3]).matches()) {
            return 3;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            sdf.parse(record[3]);
        } catch (ParseException e) {            return 3;
        }
        try {
            Integer.parseInt(record[4]);
        } catch (NumberFormatException e) {
            return 4;
        }
        if (!record[5].equals("m") && !record[5].equals("f")) {
            return 5;
        }
        return 0;
    }

    static void writeToFile(String[] record) throws IOException {
        String filename = record[0] + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (String s : record) {
                writer.write(s);
                writer.write(" ");
            }
            {
                writer.newLine();
            }
            System.out.println("Запись успешно добавлена в файл " + filename);
        }
    }
}
