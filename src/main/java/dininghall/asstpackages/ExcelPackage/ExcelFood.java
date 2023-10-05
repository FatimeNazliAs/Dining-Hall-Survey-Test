package dininghall.asstpackages.ExcelPackage;

import dininghall.asstpackages.ExcelPackage.CreatorFolder.CreateExcel;
import dininghall.asstpackages.ExcelPackage.Models.ExcelOutData;
import dininghall.asstpackages.ExcelPackage.Models.ExcelReadOut;
import dininghall.domain.survey.SurveyExcel.FoodManager;

import java.io.InputStream;
import java.util.List;

public class ExcelFood
{
    public <T> int export(List<T> list)
    {
        return new CreateExcel().Create(list);
    }

    public <T> ExcelOutData importExcel(InputStream stream,String clazzName)
    {

        ExcelOutData datas = new ExcelLanguageReader().ExcelReader(stream, clazzName,false);
        new FoodManager().FoodAddJust(datas.getDataList());
        for (ExcelReadOut out : datas.getErrors())
        {
            System.out.println(out.getRowNumber() + ". hata:" + out.getOutText());
        }
        for (ExcelReadOut out : datas.getAdd())
        {
            System.out.println(out.getRowNumber() + ". ekleme:" + out.getOutText());
        }
        for (ExcelReadOut out : datas.getUpdate())
        {
            System.out.println(out.getRowNumber() + ". güncelleme:" + out.getOutText());
        }
        return datas;
      /*  obj=obj;
        var descriptors = PropertyUtils.getPropertyDescriptors(obj);

        Map<String ,String > map=new HashMap<>();
        for (var field :descriptors)
        {
            try
            {
                map.put(field.getName(),field.getReadMethod().invoke(obj)+"");
            } catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e)
            {
                throw new RuntimeException(e);
            }
        }*/
    }
}
