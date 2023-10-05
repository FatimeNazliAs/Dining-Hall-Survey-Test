package dininghall.view.survey;

import lombok.Data;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import dininghall.domain.survey.Category;
import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;
import dininghall.generic.LazyPack.LazyService;
import dininghall.generic.Manager.DbManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "dayOfMenuView")
@ViewScoped
@Data
public class DayOfMenuView implements Serializable
{
    private Map<String, String> map_list;
    private LazyDataModel<DayOfMenuWithDetailViewModel> dayOfMenuLazyDataModel;
    private List<Category> categories;
    private DayOfMenuWithDetailViewModel selectedDayOfMenuVW;

    @PostConstruct
    public void init()
    {
        categories = new DbManager(Category.class).GetAll();
        dayOfMenuLazyDataModel = new LazyDataModel<>()
        {

            @Override
            public int count(Map<String, FilterMeta> map)
            {
                return 0;
            }

            @Override
            public List<DayOfMenuWithDetailViewModel> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1)
            {
                List<DayOfMenuWithDetailViewModel> list = new LazyService().FilterOperation(dayOfMenuLazyDataModel, new DayOfMenuWithDetailViewModel(), i, i1, map, map1, "");
                Map<String, String> map_list = new HashMap<>();
                for (var item : list)
                {
                    if (map_list.get(item.getCategoryId() + "") == null)
                        map_list.put(item.getCategoryId() + "", "");
                }
                return list;


    /*            String syntax = "SELECT Distinct\n" +
                        "    food_category_name as name,\n" +
                        "    f.food_category_id as value\n" +
                        "FROM day_of_menu\n" +
                        "         JOIN category c ON day_of_menu.category_id = c.category_id\n" +
                        "         JOIN food f ON day_of_menu.food_id = f.food_id\n" +
                        "         JOIN food_category fc ON f.food_category_id = fc.food_category_id\n" +
                        "\n";
                var list_row = PostgreSqlManager.Read(new CounterModel(), syntax);*/
            }
        };
    }

    public void addMessage()
    {
    }

    //yeni dayofmenu eklerken seçili dayofmenu temizleme
    public void newDayOfMenu()
    {
        selectedDayOfMenuVW = new DayOfMenuWithDetailViewModel();

    }

    //yeni dayofmenu ekleme veya düzenleme kodu
    public void saveDayOfMenu()
    {
        if (selectedDayOfMenuVW.getDayOfMenuId() > 0)
        {
            if (new DbManager(selectedDayOfMenuVW).Update())
            {
                /***updated succes***/
            } else
            {
                /****updated failed **/
            }

        } else
        {
            int id = new DbManager(selectedDayOfMenuVW).Add();
            if (id > 0)
            {
                /*** succes ***/
            } else
            {

                /*** failed ***/
            }
        }
    }

    public void deleteDayOfMenu()
    {
        new DbManager(selectedDayOfMenuVW).Delete();
    }
}
