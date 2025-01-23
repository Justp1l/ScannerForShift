import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Shift {
    public static void main(String[] args) throws FileNotFoundException, IOException{

        //Введение файлов
        File absDirection = new File("");
        String originalPath = absDirection.getCanonicalPath();
        String separator = File.separator;

        System.out.println("Сколько входных файлов планируется?");
        Scanner sc = new Scanner(System.in);
        String answer0 = sc.nextLine();   
        while (!isInt(answer0)) {
            System.out.println("Необходимо вписать число входящих файлов");
            answer0=sc.nextLine();
        }
        
        String path;
        File inFile;
        Scanner fileScanner;
        ArrayList<String> dataArrayList = new ArrayList<>();
        
        for (int i = 0; i < Integer.parseInt(answer0); i++) {
            path = originalPath + separator + "Входные данные" + separator + "in" + (i+1) + ".txt";
            inFile = new File(path);
            if (inFile.exists()) {
                System.out.println(path);
                fileScanner = new Scanner(inFile, StandardCharsets.UTF_8);
                while (fileScanner.hasNextLine()) {
                    dataArrayList.add(fileScanner.nextLine());     
                }           
            }
        }
        String[] data = dataArrayList.toArray(new String[dataArrayList.size()]);      
        
        // Подсчёт элементов каждого типа 
        int ctInt = 0, ctDouble = 0, ctString = 0;
        for (int i = 0; i< dataArrayList.size(); i++){
            if ( isInt(data[i]))  {
                ctInt++;
            }else if (isDouble(data[i])) {
                ctDouble++;               
            }else if (isString(data[i])) {
                ctString++;
            }else {
                continue;
            }
        }
                        
        // Перераспределение элементов по трём массивам: stringContainer, intContainer и doubleContainer
        int intCounter=0, doubleCounter=0, stringCounter=0;
        int[] intContainer = new int[ctInt];
        Double[] doubleContainer = new Double[ctDouble];
        String[] stringContainer = new String[ctString];
        for (int i = 0; i < data.length; i++){
            if (data[i].equals(null)) {
                continue;
            }
            if ( isInt(data[i]))  {
                intContainer[intCounter] = Integer.parseInt(data[i]);
                intCounter++;
            }else if (isDouble(data[i])) {
                doubleContainer[doubleCounter] = Double.parseDouble(data[i]);
                doubleCounter++;                
            }else if (isString(data[i])) {
                stringContainer[stringCounter] = data[i];
                stringCounter++;
            }else {
                continue;
            } 
        }

        String pathToSave = absDirection.getAbsolutePath();
        String dataIntPath = pathToSave + separator;
        String dataDoublePath = pathToSave + separator;
        String dataStringPath = pathToSave + separator;
        
        // Создание файлов и запись информации с специализированных массивов

        // Числа
        File dataInt = new File(dataIntPath + "integers.txt");
        createNewIntFile(intCounter, dataInt, intContainer);
        
        // Числа с дробной частью
        File dataDouble = new File(dataDoublePath + "floats.txt");
        createNewDoubleFile(doubleCounter, dataDouble, doubleContainer);

        // Строки
        File dataString = new File(dataStringPath + "strings.txt");
        createNewStringFile(stringCounter, dataString, stringContainer);
        
        
        //Введение метода добавления префикса и изменения пути создания файла
        Scanner prefix, additionalPathToFile;
        String prefixName = "", additionalPath;
        Scanner methodsScanner = new Scanner(System.in);
        System.out.println("Вы можете использовать команды -p для создания префикса и -o для изменения пути; enter - выход");
        String methods;
        methods = methodsScanner.nextLine();
        while ( methods != "") {
            switch (methods) {
                case "-p":
                    dataInt.delete();
                    dataDouble.delete();
                    dataString.delete();
                    prefix = new Scanner(System.in);
                    System.out.println("Введите желаемый префикс ");
                    prefixName = prefix.nextLine();
                    dataIntPath = pathToSave + prefixName + "integers.txt";
                    dataDoublePath = pathToSave + prefixName + "floats.txt";
                    dataStringPath = pathToSave + prefixName + "strings.txt";
                    dataInt = new File(pathToSave + separator + prefixName + "integers.txt");
                    createNewIntFile(intCounter, dataInt, intContainer);
                    dataDouble = new File(pathToSave + separator + prefixName + "floats.txt");
                    createNewDoubleFile(doubleCounter, dataDouble, doubleContainer);
                    dataString = new File(pathToSave + separator + prefixName + "strings.txt");
                    createNewStringFile(stringCounter, dataString, stringContainer);
                    break;
                case "-o":
                    dataInt.delete();
                    dataDouble.delete();
                    dataString.delete();
                    System.out.println("Введите дополнительный путь для сохранения результатов");
                    additionalPathToFile = new Scanner(System.in);
                    additionalPath = additionalPathToFile.nextLine();
                    File additionalFile = new File(additionalPath);
                    if (!additionalFile.exists()) {
                        additionalFile.mkdirs();
                    }
                    pathToSave += separator + additionalPath + separator; 
                    dataInt = new File(pathToSave + prefixName + "integers.txt");
                    createNewIntFile(intCounter, dataInt, intContainer);
                    dataDouble = new File(pathToSave + prefixName + "floats.txt");
                    createNewDoubleFile(doubleCounter, dataDouble, doubleContainer);
                    dataString = new File(pathToSave + prefixName + "strings.txt");
                    createNewStringFile(stringCounter, dataString, stringContainer);
                break;
                default:
                    break;
            } 
            System.out.println("Вы можете использовать команды -p для создания префикса и -o для изменения пути; enter - выход");
            methods = methodsScanner.nextLine();
        }

        //Внедрение функции расширения данных
        String additionalMethods, additionalAnswer;
        Scanner additionalScanner = new Scanner(System.in);
        Scanner additionerListenner = new Scanner(System.in);
        FileWriter additionalIntWriter = new FileWriter(dataInt, true);
        FileWriter additionalDoubleWriter = new FileWriter(dataDouble, true);
        FileWriter additionalStringWriter = new FileWriter(dataString, true);
        System.out.println("С помощью метода -a Вы можете добавить любые данные в программу");
        additionalMethods = additionalScanner.nextLine();
        while ( additionalMethods != "") {
            switch (additionalMethods) {
                case "-a":
                    System.out.println("Введите добавочные эллементы. Программа будет записывать Ваши действия до команды -c");
                    additionalAnswer = additionerListenner.nextLine();
                    if ( additionalAnswer.equals("-c")) {
                        break; 
                     }else {
                        dataArrayList.add(additionalAnswer);
                        if ( isInt(additionalAnswer))  {
                            Integer.parseInt(additionalAnswer);
                            additionalIntWriter.write(additionalAnswer + "\n");
                            ctInt++;
                        }else if (isDouble(additionalAnswer)) {
                            Double.parseDouble(additionalAnswer);
                            additionalDoubleWriter.write(additionalAnswer + "\n");
                            ctDouble++;               
                        }else if (isString(additionalAnswer)) {
                            additionalStringWriter.write(additionalAnswer + "\n");
                            ctString++;
                        }
                        continue;
                    }
                default:
                    break;
            }
            System.out.println("С помощью метода -a Вы можете добавить любые данные в программу");
            additionalMethods = additionalScanner.nextLine();
        }
        additionalIntWriter.close();
        additionalDoubleWriter.close();
        additionalStringWriter.close();
        

            // Сбор статистики
        int maxStringLen = 0, minStringLen = stringContainer[0].length();
        Double sumNum = 0.0, averageNum = null, maxNum = 0.0, minNum = 0.0;
        if (intCounter > 0) {
            minNum = Double.valueOf(intContainer[0]);
        }
            data = dataArrayList.toArray(new String[dataArrayList.size()]);
            for (int i = 0; i < dataArrayList.size();i++) {
                if (isInt(data[i]) || isDouble(data[i])) {
                    if (Double.parseDouble(data[i]) > Double.valueOf(maxNum)) {
                    maxNum = Double.parseDouble(data[i]); 
                    }
                    if (Double.parseDouble(data[i]) < Double.valueOf(minNum)) {
                    minNum = Double.parseDouble(data[i]);
                    }
                sumNum += Double.parseDouble(data[i]);
                }else if (isString(data[i])) {
                    if (data[i].length() > maxStringLen) {
                        maxStringLen = data[i].length();
                    } 
                    if (data[i].length() < minStringLen) {
                        minStringLen = data[i].length();
                    }
                }else {
                    continue;
                }
            }
            averageNum = sumNum/(ctInt+ctDouble);

            //Работа с доп. опциями ([-s, -f] - статисткиа)
        System.out.println("-Вы хотите просмотреть статистику по данным? \n(-s - короткая; -f - полная; enter - отмена)");    
        Scanner option = new Scanner(System.in);
        Scanner choise = new Scanner(System.in);
        String stringOrInt;
        String additionalOption;
        
        additionalOption = option.nextLine();
        while (additionalOption != "") {
            switch (additionalOption) {
                 case "-f":
                    System.out.println("Выбери тип данных для которого требуется статистика: \nСтроки - \"S\";\nЧисла - \"I\".");
                    stringOrInt = choise.nextLine();
                    switch (stringOrInt) {
                        case "S":
                            System.out.println(">>Максимальная длина строки: " + maxStringLen + "\n>>Минимальная длина строки: " + minStringLen);
                            break;
                        case "I":
                            System.out.println(">>Максимальное значение: " + maxNum + " \n>>Минимальное значение: " + minNum + " \n>>Сумма знчений: " + sumNum + "\n>>Среднее арифметическое: " + averageNum);
                            break;
                        default:
                            break;
                    }
                    break;
                case "-s":
                System.out.println("Выбери тип данных для которого требуется статистика: \nСтроки - \"S\";\nЧисла - \"I\".");
                stringOrInt = choise.nextLine();
                    switch (stringOrInt) {
                        case "S":
                            System.out.println(">>Количество строк: " + ctString);
                            break;
                        case "I":
                            System.out.println(">>Количество чисел: " + (ctInt + ctDouble));
                            break;
                        default:
                            break;
                    }
                        break;
                    default:
                        break;
            }
            System.out.println("\n-Посмотреть статистику повторно? \n(-s - короткая; -f - полная; enter - отмена)");   
            additionalOption = option.nextLine();   
        }   
        sc.close();
        methodsScanner.close();
        option.close();
        choise.close();
        additionerListenner.close();
        additionalScanner.close();
    }
           
        private static boolean isInt(String s) throws NumberFormatException {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        private static boolean isDouble(String s) throws NumberFormatException, NullPointerException {
            try {
                if (s != null) {
                    Double.parseDouble(s);    
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        private static boolean isString (String s) {
            if (s == " " | s.isBlank()) {
                return false;
            } else {
                return true;
            }
        } 
        private static void createNewIntFile (int counter, File data, int[] array) throws FileNotFoundException{
            if (counter <= 0) {
                data.delete();
            } else {
                PrintWriter dataPrinter = new PrintWriter(data);
                for(int i = 0; i < array.length; i++ ){
                    dataPrinter.println(array[i]);
                }
                dataPrinter.close();
            }
        }
        private static void createNewDoubleFile (int counter, File data, Double[] array) throws FileNotFoundException{
            if (counter <= 0) {
                data.delete();
            } else {
                PrintWriter dataPrinter = new PrintWriter(data);
                for(int i = 0; i < array.length; i++ ){
                    dataPrinter.println(array[i]);
                }
                dataPrinter.close();
            }
        }  
        private static void createNewStringFile (int counter, File data, String[] array) throws FileNotFoundException{
            if (counter <= 0) {
                data.delete();
            } else {
                PrintWriter dataPrinter = new PrintWriter(data);
                for(int i = 0; i < array.length; i++ ){
                    dataPrinter.println(array[i]);
                }
                dataPrinter.close();
                
            }
        }       
}
    // // Распределение по массивам готово. Чтение файлов готово, осталось:
    // 1. Подключить статистику - R
    // 2. Допилить сохранения - R
    // 2.2. Запилить FileWiter - +
    // 3. Продумать логику сохранений (в т.ч и командами)
