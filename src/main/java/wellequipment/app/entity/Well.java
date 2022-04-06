package wellequipment.app.entity;


import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class Well {
    private int id;
    private String name;
    private List<Equipment> equipments;

    public Well() {
        equipments = new ArrayList<>();
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

    public List<Equipment> getEquipments() {
        return equipments;
    }
    @XmlElementWrapper(name = "equipments")
    @XmlElement(name = "equipment")
    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Well well = (Well) o;
        return id == well.id && Objects.equals(name, well.name) && Objects.equals(equipments, well.equipments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, equipments);
    }

    @Override
    public String toString() {
        return "Well{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", equipments=" + equipments +
                '}';
    }
}
