
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        String name = "Ivanov Ivan Ivanovich 15.01.1995 1234567 m";
        String[] newRecord = parse(name);
        switch (checkError(newRecord)) {
            case 1:
                System.out.println("Error 1: record does not contain all fields ");
                break;
            case 2:
                System.out.println("Error 2: record contains extra fields");
                break;
            case 0:
                System.out.println(Arrays.toString(newRecord));
                writeToFile(newRecord);
                break;
            default:
        }

    }

    static String[] parse(String record) {
        return record.split(" ");
    }

    static int checkError(String[] record) {
        if (record.length < 6) return 1;
        if (record.length > 6) return 2;
        return 0;
    }

    static String[] createRecord(String[] record){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
        if (!pattern.matcher(record[3]).matches()) {
            throw new RuntimeException("Incorrect date format");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            sdf.parse(record[3]);
        } catch (ParseException e) {
            System.out.println("Incorrect date format");;
        }
        try{
            Integer.parseInt(record[4]);
        } catch (NumberFormatException e){
            System.out.println("Incorrect phone number");
        }
        if (!record[5].equals("m") && !record[5].equals("f")){
            throw new RuntimeException("Incorrect sex");
        }
        return record;
    }

    static void writeToFile(String[] record) throws IOException{
        String filename = record[0] + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true))) {
            for (String s : record) {
                writer.write(s);
                writer.write(" ");
            } {
                writer.newLine();
            }
            System.out.println("Массив строк успешно записан в файл " + filename);
        }
    }
}
