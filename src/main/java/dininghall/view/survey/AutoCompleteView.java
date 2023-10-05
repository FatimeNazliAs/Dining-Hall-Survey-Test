package dininghall.view.survey;

import lombok.Data;
import org.primefaces.PrimeFaces;
import dininghall.domain.models.CounterModel;
import dininghall.domain.survey.Category;
import dininghall.domain.survey.Food;
import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;
import dininghall.generic.Manager.DbManager;
import dininghall.generic.Manager.PostgreSqlManager;
import dininghall.view.survey.models.OptionMenu;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;


@Data
public class AutoCompleteView implements Serializable {

    private DayOfMenuWithDetailViewModel dayOfMenuVW = new DayOfMenuWithDetailViewModel();
    private List<OptionMenu> optionMenuList = new ArrayList<>();
    private OptionMenu selectedOptionMenu = new OptionMenu();
    private Map<String, Integer> typeMap = new HashMap<>();
    private int categoryId = -1;
    private String dompId = "-1";
    private String defaultCategoryId = "-1";
    private int UIIndex = 1;
    List<Category> categoryList = new ArrayList<>();


/*
    @ManagedProperty("#{countryService_1}")
    private CountryService countryService;

    private CustomerService service;
*/


    @PostConstruct
    public void init() {


        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Sabah");
        categoryList.add(category);
        category = new Category();
        category.setCategoryId(2);
        category.setCategoryName("Öğle");
        categoryList.add(category);
        category = new Category();
        category.setCategoryId(3);
        category.setCategoryName("Akşam");
        categoryList.add(category);

        String syntax = "SELECT Distinct fc.food_category_id as value,food_category_name as name from food\n" +
                "join food_category fc on food.food_category_id = fc.food_category_id";

        var list_row = PostgreSqlManager.Read(new CounterModel(), syntax);
        for (var item : list_row) {
            typeMap.put(item.getNames(), item.getValue());
        }


        optionMenuList = new ArrayList<>();
        OptionMenu menu = new OptionMenu();
/*
        List<DayOfMenuWithDetailViewModel> modelList = new DbManager(DayOfMenuWithDetailViewModel.class).GetAll();
        menu.setUIkey("itemTip" + UIIndex++);
        menu.setModelList(modelList);
        menu.setTypeList(typeMap);
        optionMenuList.add(menu);*/


    }

    public OptionMenu newOptionMenu(DayOfMenuWithDetailViewModel dayOfMenuVW) {
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.setFoodList(new ArrayList<>());
        optionMenu.setQuery("");
        optionMenu.setDofmVW(dayOfMenuVW);
        optionMenu.setSelectedFood(new DbManager(Food.class).GetFirst("food_id=" + dayOfMenuVW.getFoodId()));
        optionMenu.setDofmType(Long.valueOf(dayOfMenuVW.getFoodCategoryId()).intValue());
        optionMenu.setTypeList(typeMap);
        optionMenu.setUIkey("itemTip" + UIIndex++);
        return optionMenu;
    }

    public void changeOption(OptionMenu optionMenu) {
        optionMenu.setSelectedFood(null);
        PrimeFaces.current().executeScript("PF('formSchedule:" + optionMenu.getUIkey() + "_input').clear()");
        String sqlQuery = generateQuery(optionMenu);

    }

    public List<DayOfMenuWithDetailViewModel> complateAutoDOMW1(String autocompleteId, String query) {
        // Find the corresponding item object based on the autocomplete ID
        var item = autocompleteId;

        // Perform autocomplete logic based on the item object and the query
        return new ArrayList<>();
    }

    public String generateQuery(OptionMenu optionMenu) {

        int tp = optionMenu.getDofmType();
        //   String qu = optionMenu.getQuery().toLowerCase();
        //   FacesContext context = FacesContext.getCurrentInstance();

        //var o = UIComponent.getCurrentComponent(context).getAttributes().get(optionMenu.getUIkey());
        String sqlQuery = " title ilike '%" + optionMenu.getQuery() + "%' and food_category_id=" + optionMenu.getDofmType();
        //  return countries.stream().filter(t -> (t.getTitle() + "").toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
        return sqlQuery;

    }

    public Map<String, String> complateTest(String query) {
        Map<String, String> aa = new HashMap<>();
        aa.put("1", "1");
        aa.put("2", "2");
        aa.put("3", "3");
        return aa;
    }

    public List<Food> complateAutoDOMW(String query) {

        selectedOptionMenu = selectedOptionMenuR();
        selectedOptionMenu.setQuery(query);
        String sqlQuery = generateQuery(selectedOptionMenu);
        resItemTip(selectedOptionMenu.getUIkey());
        if (selectedOptionMenu.getDofmType() == 0)
            return new ArrayList<>();

        List<Food> countries = new DbManager(Food.class).Get(sqlQuery);
        return countries;
    }


    public void action() {
        int a = 0;
        a = 0;
    }

    public OptionMenu selectedOptionMenuR() {

        var i = FacesContext.getCurrentInstance();
        var ss = i.getExternalContext().getRequestParameterMap().get("javax.faces.source");
        var t_ = ss.replace("formSchedule:", "");
        for (var item : optionMenuList) {
            if (t_.equals(item.getUIkey())) {
                uiList.add(item.getUIkey());
                this.selectedOptionMenu = item;
                return item;
            }


        }
        checkList();
        return null;
    }

    public void changeSelected(Food food) {
        var context = FacesContext.getCurrentInstance();
        this.selectedOptionMenu = selectedOptionMenuR();
        this.selectedOptionMenu.setSelectedFood(food);
        int a = 0;
        a = 0;
    }

    public void getNewA() {
        if (optionMenuList.size() > 15)
            return;
        OptionMenu menu = new OptionMenu();
        List<DayOfMenuWithDetailViewModel> modelList = new DbManager(DayOfMenuWithDetailViewModel.class).GetAll();
        menu.setUIkey("itemTip" + UIIndex++);
        menu.setModelList(modelList);
        menu.setTypeList(typeMap);


        optionMenuList.add(menu);
        checkList();


    }

    public void checkList() {
        //   List<Integer> selectedLists = new ArrayList<>();

        // Map<String, Integer> listTemp = new HashMap<>();


       /* Map<String, Integer> listTemp = new HashMap<>();

        for (var item : typeMap.entrySet())
        {
            listTemp.put(item.getKey(), item.getValue());
        }
        for (var item : optionMenuList)
        {

            boolean b = listTemp.containsValue(item.getDofmType());
            if (b)
            {
                boolean r = listTemp.values().remove(item.getDofmType());
                r = r;

            }


        }
        for (var item :optionMenuList)
        {
            item.setTypeList(new HashMap<>());
            for (var item_temp : listTemp.entrySet())
            {
                item.getTypeList().put(item_temp.getKey(), item_temp.getValue());
            }
            for (Map.Entry<String, Integer> entry : typeMap.entrySet()) {
                if (entry.getValue() == item.getDofmType()) {
                    item.getTypeList().put(entry.getKey(),entry.getValue());
                    break;
                }
            }
        }*/
        Map<String, Integer> listTemp = new HashMap<>(typeMap);
// Geçici bir liste oluşturulur ve typeMap'in içeriği kopyalanır
/*
        optionMenuList.stream()
                .filter(item -> listTemp.containsValue(item.getDofmType()))
                .forEach(item -> listTemp.values().remove(item.getDofmType()));
*/
// optionMenuList üzerinde bir akış oluşturulur, geçici listede item.getDofmType() değerini içerenleri filtreler ve bu değerleri kaldırır

        optionMenuList.forEach(item ->
        {
            item.setTypeList(new HashMap<>(listTemp));
            // Her bir optionMenuList öğesi için typeList özelliği, geçici listenin bir kopyası olarak ayarlanır

            for (Map.Entry<String, Integer> entry : typeMap.entrySet()) {
                if (entry.getValue() == item.getDofmType()) {
                    item.getTypeList().put(entry.getKey(), entry.getValue());
                    break;
                }
            }
            // typeMap üzerinde bir döngü oluşturulur ve item.getDofmType() değerine sahip olan giriş, typeList'e eklenir
        });
    }

    List<String> uiList = new ArrayList<>();

    public boolean resItemTip(String uikey) {
        var a = FacesContext.getCurrentInstance().getViewRoot();
        // PanelGrid bileşenine erişin

// InputText bileşenini bulun
        //   UIComponent inputText = panelGrid.findComponent("inputTextId");

// InputText bileşenini PanelGrid bileşeninin children listesinden kaldırın
        //   panelGrid.getChildren().remove(inputText);
        for (int i = 0; i <= UIIndex; i++) {
            PrimeFaces.current().executeScript("document.getElementById(\"formSchedule:\"+index+\"_itemtip\").style.display=\"none\";");

        }
        FacesContext.getCurrentInstance().getViewRoot().findComponent("formSchedule:" + uikey).getFacet("itemtip").setRendered(true);

        return true;

    }

    class slc_1 {
        String no;
        int option;
    }
}