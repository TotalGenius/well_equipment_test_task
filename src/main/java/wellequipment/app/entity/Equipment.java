package wellequipment.app.entity;


import jakarta.xml.bind.annotation.XmlAttribute;

import java.util.Objects;

public class Equipment {
    private int id;
    private String name;
    private int well_id;

    public Equipment() {
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public int getWell_id() {
        return well_id;
    }
    @XmlAttribute
    public void setWell_id(int well_id) {
        this.well_id = well_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id == equipment.id && well_id == equipment.well_id && Objects.equals(name, equipment.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, well_id);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", well_id=" + well_id +
                '}';
    }
}
