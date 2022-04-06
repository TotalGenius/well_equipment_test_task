package wellequipment.app.connection;

import wellequipment.app.entity.Equipment;
import wellequipment.app.entity.Well;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//В данном классе реализуем методы для связи с БД, исходя из задания
public class DBHandler extends DBConnection {
    private Connection connection;

    //Создаем оборудование и скважины
    public void CreateEquipment(String wellName, int equipCount) {
        //Сначала ищем скважину с указанным названием в таблице
        Well well = getWell(wellName);
        if (well == null) {
            //Если скважины с таки названием нет, то добавляем новую скажину и оборудование к ней
            addNewWell(wellName, equipCount);
        } else {
            //Если скажина с таким названием имеется, то добаляем к ней оборудование
            addEquipment(wellName, equipCount);
        }
    }

    //Получаем указанный список из БД вместе с оборудование, которые на них установлены
    public List<Well> info(String[] wellNames) {
        List<Well> wells = new ArrayList<>();
        Well well = null;
        for (int i = 0; i < wellNames.length; i++) {
            well = getWell(wellNames[i]);
            if (well != null) {
                wells.add(well);
            }
        }
        return wells;
    }

    //Получаем список всех скважин из БД
    public List<Well> info() {
        List<Well> wellList = getAll();
        return wellList;
    }

    private List<Well> getAll() {
        List<Well> wells = new ArrayList<>();
        Well well = null;
        connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetWells = statement.executeQuery("SELECT * FROM well");

            while (resultSetWells.next()) {
                well = new Well();
                well.setId(resultSetWells.getInt("id"));
                well.setName(resultSetWells.getString("name"));
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM equipment WHERE well_id =?");
                preparedStatement.setInt(1,well.getId());
                ResultSet resultSetEquips = preparedStatement.executeQuery();

                while (resultSetEquips.next()) {
                    Equipment equipment = new Equipment();
                    equipment.setId(resultSetEquips.getInt("id"));
                    equipment.setName(resultSetEquips.getString("name"));
                    equipment.setWell_id(resultSetEquips.getInt("well_id"));
                    well.getEquipments().add(equipment);
                }
                wells.add(well);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wells;
    }

    public Well getWell(String name) {
        connection = getConnection();
        Well well = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id, name FROM well WHERE name=?");
            preparedStatement.setString(1, name);
            ResultSet resultSetWell = preparedStatement.executeQuery();
            if (resultSetWell.next()) {
                well = new Well();
                well.setId(resultSetWell.getInt("id"));
                well.setName(resultSetWell.getString("name"));
                preparedStatement = connection.prepareStatement("SELECT id, name, well_id FROM equipment WHERE well_id =?");
                preparedStatement.setInt(1, well.getId());
                ResultSet resultSetEquip = preparedStatement.executeQuery();
                while (resultSetEquip.next()) {
                    Equipment equipment = new Equipment();
                    equipment.setId(resultSetEquip.getInt("id"));
                    equipment.setName(resultSetEquip.getString("name"));
                    equipment.setWell_id(resultSetEquip.getInt("well_id"));
                    well.getEquipments().add(equipment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return well;
    }

    private void addNewWell(String wellName, int equipCount) {
        connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO well(name) VALUES (?)");
            preparedStatement.setString(1, wellName);
            preparedStatement.execute();

            Well well = getWell(wellName);
            connection = getConnection();
            for (int i = 1; i <= equipCount; i++) {
                String equipmentName = well.getName() + "_equipment#" + i;
                int equipmentWell_id = well.getId();
                preparedStatement = connection.prepareStatement("INSERT INTO equipment(name, well_id) VALUES(?,?)");
                preparedStatement.setString(1, equipmentName);
                preparedStatement.setInt(2, equipmentWell_id);
                preparedStatement.execute();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addEquipment(String wellName, int equipCount) {
        Well well = getWell(wellName);
        PreparedStatement preparedStatement = null;
        connection = getConnection();
        int countOfNewEquipment = well.getEquipments().size() + 1;
        try {
        for (int i = 1; i <= equipCount; i++) {
            String equipmentName = well.getName() + "_equipment#" + countOfNewEquipment++;
            int equipmentWell_id = well.getId();

                preparedStatement = connection.prepareStatement("INSERT INTO equipment(name, well_id) VALUES(?,?)");
                preparedStatement.setString(1, equipmentName);
                preparedStatement.setInt(2, equipmentWell_id);
                preparedStatement.execute();
                preparedStatement.close();

        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
