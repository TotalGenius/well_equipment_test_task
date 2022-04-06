package wellequipment.app.XMLParser;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import wellequipment.app.entity.Well;


import java.io.File;
import java.util.List;

//В данном классе реализуем запись данных из БД в XML файл
public class JaxbWriter {

    //Запись данных в XML файл
    public void write(List<Well> wellList, String filename) {
        //поле для хранение данных из БД
        Wells wells = new Wells();
        wells.setWells(wellList);
        File file = new File(filename);
        JAXBContext context = null;

        try {
            context = JAXBContext.newInstance(Wells.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wells, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }
}
