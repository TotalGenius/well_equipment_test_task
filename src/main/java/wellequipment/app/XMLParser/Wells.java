package wellequipment.app.XMLParser;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import wellequipment.app.entity.Well;

import java.util.List;

@XmlRootElement
public class Wells {
    private List<Well> wells;

    public List<Well> getWells() {
        return wells;
    }

    @XmlElementWrapper(name = "wells")
    @XmlElement(name = "well")
    public void setWells(List<Well> wells) {

        this.wells = wells;
    }
}
