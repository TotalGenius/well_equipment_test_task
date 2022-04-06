package wellequipment.app.logic;

import wellequipment.app.XMLParser.JaxbWriter;
import wellequipment.app.connection.DBHandler;
import wellequipment.app.entity.Well;

import java.util.List;

public class WellAndEquipmentApp {
    private DBHandler handler;

    public WellAndEquipmentApp() {
        handler = new DBHandler();
    }

    /*1.Создание N-количества оборудования на скважине
      При выборе этого пункта пользователь указывает кол-во оборудования и имя скважины.
      Программа создает указанное кол-во оборудования на скважине с указанным именем.
      При создании оборудования каждому присваивается свой id и свое уникальное имя
      (должно генерироваться программой с использованием латинских букв и цифр).
       Скважина, так же создается программой с указанным именем, если ее еще нет в таблице.
     */
    public void create(int equipCount, String wellName) {
        handler.CreateEquipment(wellName, equipCount);
    }

    /*2.Вывод общей информации об оборудовании на скважинах
    При выборе этого пункта пользователь указывает имена скважин, разделяя их пробелами или запятыми.
    Программа подсчитывает кол-во оборудования на каждой скважине и выдает на экран таблицу вида
     */
    public List<Well> getInfo(String[] wellNames) {
        List<Well> wells = handler.info(wellNames);
        return wells;
    }
    public List<Well> getInfo() {
        List<Well> wells = handler.info();
        return wells;
    }

    /*3.Экспорт всех данных в XML файл
    При выборе этого пункта пользователь указывает имя файла.
    Программа выбирает все скважины из базы и записывает данные
    по ним и оборудовании  в xml формате
     */
    public void parseToXML(String fileName) {
        List<Well> wells = handler.info();
        JaxbWriter writer = new JaxbWriter();
        writer.write(wells, fileName);
    }
}
