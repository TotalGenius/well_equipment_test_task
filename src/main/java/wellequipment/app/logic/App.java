package wellequipment.app.logic;

import wellequipment.app.entity.Well;

import java.util.List;
import java.util.Scanner;

public class App {
    private WellAndEquipmentApp wellAndEquipmentApp;
    private Scanner scanner;
    private boolean isStopped;
    private final String text = "Application info:" +
            "\n--------------------------------------" +
            "\n-create countOfEquipment wellName (Создание N кол-ва оборудования на скважине) " +
            "\ncountOfEquipment-количество оборудовани" +
            "\nwellName-название скважины\n" +
            "--------------------------------------\n" +
            "-getInfo wellNames (Вывод общей информации об оборудовании на скважинах)\n" +
            "wellNames-название скважин (перечислите через запятую или пробел)\n" +
            "-getInfo (Вывод общей информации об оборудовании на всех скважинах)" +
            "--------------------------------------\n" +
            "-export filePath (Экспорт всех данных в xml файл)\n" +
            "filePath-путь и/или название файла\n" +
            "--------------------------------------\n" +
            "-help (Справочник по командам)\n" +
            "--------------------------------------\n" +
            "-exit (Выход из программы)\n" +
            "--------------------------------------\n";

    public App() {
        wellAndEquipmentApp = new WellAndEquipmentApp();
        scanner = new Scanner(System.in);
        isStopped = false;
    }

    public void start() {
        System.out.println(text);
        do {
            System.out.print("Введите команду:");
            String[] command = scanner.nextLine().trim().split("[ ,]");
            switch (command[0]) {
                case "-create":
                    try {
                        int equipCount = Integer.parseInt(command[1]);
                        String wellName = command[2];
                        wellAndEquipmentApp.create(equipCount, wellName);
                    } catch (NumberFormatException e) {
                        System.out.println("Невреное введено значение. countOfEquipment может быть только целочиленным ");
                    }
                    break;
                case "-getInfo":
                    List<Well> wellList = null;
                    if (command.length > 1) {
                        String[] wellNames = new String[command.length - 1];
                        for (int i = 0; i < wellNames.length; i++) {
                            int j = i + 1;
                            wellNames[i] = command[j];
                        }

                        wellList = wellAndEquipmentApp.getInfo(wellNames);
                    } else {
                        wellList = wellAndEquipmentApp.getInfo();
                    }
                    int count = 1;
                    for (Well well : wellList) {
                        System.out.println(count++ + ". Имя скважины:" + well.getName() + ", количество оборудования: " + well.getEquipments().size());
                    }
                    break;
                case "-export":
                    wellAndEquipmentApp.parseToXML(command[1]);
                    break;
                case "-exit":
                    System.out.println("Завершение работы программы");
                    isStopped = true;
                    break;
                case "-help":
                    System.out.println(text);
                default:
                    System.out.println("Неверно набрана команда. Повторите попытку");
                    break;
            }

        } while (!isStopped);
    }
}
