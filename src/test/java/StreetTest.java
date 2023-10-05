import dininghall.common.ExcelPOIHelper;
import dininghall.dao.info.StreetDAO;
import dininghall.dao.info.StreetDAOImpl;
import dininghall.domain.info.Street;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreetTest
{

    private StreetDAO streetDAO = new StreetDAOImpl();

    private ExcelPOIHelper excelPOIHelper = new ExcelPOIHelper();

    @Test
    public void readStreetFromExcel()
    {

        try
        {
            Map<Long, List<String>> data = excelPOIHelper.readExcel("T:/test_harf.xlsx");
            for (Map.Entry<Long, List<String>> entry : data.entrySet())
            {
                try
                {

                    Street street = new Street();
                    street.setNativeName(entry.getValue().get(3));
                    street.setEnglishName(entry.getValue().get(3));
                    street.setStateId(Integer.parseInt(entry.getValue().get(4)));

                    System.out.println(entry.getValue().get(3));

                    streetDAO.addStreet(street);

                } catch (NumberFormatException e)
                {
                    // e.printStackTrace();
                }
            }


        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }









}