package dininghall.domain.survey.SurveyExcel;


import dininghall.asstpackages.ExcelPackage.Annatations.AExcelColumn;
import dininghall.asstpackages.ExcelPackage.CreatorFolder.CreateExcel;
import dininghall.asstpackages.ExcelPackage.Models.ExcelOutData;
import dininghall.asstpackages.ExcelPackage.Models.ExcelReadOut;
import dininghall.domain.models.CounterModelStringString;
import dininghall.domain.survey.DayOfMenu;
import dininghall.domain.survey.DayOfMenuParent;
import dininghall.domain.survey.exceltemplate.Breakfast;
import dininghall.domain.survey.exceltemplate.Lunch;
import dininghall.domain.survey.exceltemplate.IExcel;
import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;
import dininghall.domain.survey.models.testModel;
import dininghall.generic.Manager.DbManager;
import dininghall.generic.Manager.PostgreSqlManager;
import dininghall.generic.Service.DataService;
import dininghall.view.survey.models.CompModel;
import org.ocpsoft.shade.org.apache.commons.beanutils.PropertyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DayOfMenuManager {
    testModel proccesItem = new testModel();

    class modelProc {
        DayOfMenuWithDetailViewModel dbitem;
        testModel model;
        boolean insert = false;
    }

    public void DayOfMenuAdd(ExcelOutData excelOutData, int categoryId) throws ParseException {

        PostgreSqlManager.Command("DELETE FROM day_of_menu_parent WHERE domp_id IN (SELECT distinct day_of_menu_parent_view.domp_id FROM day_of_menu_parent_view where child_count=0)");
        List<Map<String, testModel>> excelOutDataList = excelOutData.getDataList();
        emptyDataControl(excelOutDataList);
        var format = new SimpleDateFormat("yyyy/MM/dd");
        String sqlSyntax = "";
        boolean first = false;
        sqlSyntax = getHaveRowList(categoryId, excelOutDataList, format, sqlSyntax, first);
        List<DayOfMenuWithDetailViewModel> items = new DbManager(DayOfMenuWithDetailViewModel.class).Get(sqlSyntax);
        for (var excelItem : excelOutDataList) {
            try {
                boolean findDate = false;
                for (var dbItem : items) {

                    var date_item = excelItem.get("menu_date");

                    var date_item_date = new Date();
                    if (date_item != null)
                        date_item_date = format.parse(date_item.getValue());
                    if (dbItem.getMenuDate().equals(date_item_date)) {
                        findDate = true;
                        for (var entryExcelItem : excelItem.entrySet()) {
                            proccesItem = entryExcelItem.getValue();
                            DayOfMenuWithDetailViewModel temp = null;
                            if (entryExcelItem.getValue().getKey().equals("menu_date"))
                                continue;
                            boolean find = false;
                            for (var rdbItem : items) {
                                if (dbItem.getDayOfMenuParentId() == rdbItem.getDayOfMenuParentId() && entryExcelItem.getValue().getColumnKey().equals(rdbItem.getColumnKey())) {
                                    find = true;
                                    temp = rdbItem;
                                    break;
                                }
                            }
                            if (find) {
                                var menu = new DayOfMenu();
                                menu.setColumnKey(temp.getColumnKey());
                                menu.setDayOfMenuId(temp.getDayOfMenuId());
                                menu.setDayOfMenuParentId(temp.getDayOfMenuParentId());
                                int id_ = Integer.parseInt(entryExcelItem.getValue().getKey());
                                menu.setFoodId(id_);
                                if (id_ != temp.getFoodId()) {
                                    boolean a = new DbManager(menu).Update();
                                    System.out.println("proc:" + a);
                                    temp = null;
                                }
                            } else {
                                var menu = new DayOfMenu();
                                menu.setColumnKey(entryExcelItem.getValue().getColumnKey());
                                menu.setDayOfMenuParentId(dbItem.getDayOfMenuParentId());
                                menu.setFoodId(Integer.parseInt(entryExcelItem.getValue().getKey()));
                                int a = new DbManager(menu).Add();
                                System.out.println("added = " + a + "" + menu + ", categoryId = " + dbItem.getDayOfMenuParentId());
                            }

                        }

                        break;
                    }
                }
                if (!findDate) {

                    var date_item = excelItem.get("menu_date");

                    var date_item_date = new Date();
                    if (date_item != null)
                        date_item_date = format.parse(date_item.getValue());
                    DayOfMenuParent parent = new DayOfMenuParent();
                    parent.setCategoryId(categoryId);
                    parent.setMenuDate(date_item_date);
                    var parentMenu = new DbManager(parent).Add();
                    for (var entryExcelItem : excelItem.entrySet()) {
                        if (entryExcelItem.getKey().equals("menu_date"))
                            continue;
                        var menu = new DayOfMenu();
                        menu.setColumnKey(entryExcelItem.getValue().getColumnKey());
                        menu.setDayOfMenuParentId(parentMenu);
                        menu.setFoodId(Integer.parseInt(entryExcelItem.getValue().getKey()));
                        int a = new DbManager(menu).Add();
                        System.out.println("added = " + a + "" + menu + ", categoryId = " + parentMenu);

                    }
                }


            } catch (Exception e) {
                excelOutData.getErrors().add(new ExcelReadOut("Veritabanı Uyumsuzluğu ", proccesItem != null ? proccesItem.getRowNum() : -1));
            }
        }
        PostgreSqlManager.Command("DELETE FROM day_of_menu_parent WHERE domp_id IN (SELECT distinct day_of_menu_parent_view.domp_id FROM day_of_menu_parent_view where child_count=0)");

    }


    private static String getHaveRowList(int categoryId, List<Map<String, testModel>> excelOutDataList, SimpleDateFormat format, String sqlSyntax, boolean first) throws ParseException {
        for (var item : excelOutDataList) {

            if (first)
                sqlSyntax += " or ";
            first = true;
            var str = format.format(format.parse(item.get("menu_date").getValue()));
            sqlSyntax += " menu_date='" + str + "' and category_id=" + categoryId;

        }
        return sqlSyntax;
    }


    private static void emptyDataControl(List<Map<String, testModel>> excelOutDataList) {
        for (int i = 0; i < excelOutDataList.size(); i++) {
            var list_temp_control = excelOutDataList.get(i);
            int i_size = list_temp_control.size();
            if (i_size < 2)
                excelOutDataList.remove(i--);
        }
    }


    public void ExportDayOfMenu(Date startDate, Date endDate, int categoryId) {
        boolean removeFirst = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<DayOfMenuWithDetailViewModel> dayOfMenuWithDetailViewModelList = new DbManager(DayOfMenuWithDetailViewModel.class).Get(
                "menu_date between '" + format.format(startDate) + "' and '" + format.format(endDate) + "' and category_id=" + categoryId + " order by menu_date");

        List<Lunch> templates = new ArrayList<>();

        if (templates.isEmpty()) {
            removeFirst = true;
            var models = PostgreSqlManager.Read(new CounterModelStringString(), "SELECT fc.food_category_id, MIN(title) as value,fc.food_category_name AS name FROM food join food_category fc on food.food_category_id = fc.food_category_id  where fc.category=" + 3 + " GROUP BY fc.food_category_id ORDER BY fc.food_category_id ASC;\n");
            Lunch emflmt = new Lunch(new Date(), "", "", "", "", "", "", "", "", 1);

            for (var item : models) {
                emflmt = (Lunch) swapSS(emflmt, item.getValue(), item.getNames() + "", categoryId);
            }
            emflmt.setEk1(emflmt.getAna());
            emflmt.setEk2(emflmt.getAra());
            emflmt.setTarih(new GregorianCalendar(2000, 3, 1).getTime());
// templates.add(new ExcelDayOfMonthTemplate(),0);
            templates.add(0, emflmt);
        }
        Map<String, List<DayOfMenuWithDetailViewModel>> modelMap = new HashMap<>();
        for (var item : dayOfMenuWithDetailViewModelList) {
            if (modelMap.get(format.format(item.getMenuDate())) == null) {
                modelMap.put(format.format(item.getMenuDate()), new ArrayList<>());
            }
            modelMap.get(format.format(item.getMenuDate())).add(item);

        }
        for (var items : modelMap.entrySet()) {
            var emflmt = new Lunch();

            for (var item : items.getValue()) {
                emflmt = (Lunch) swapSS(emflmt, item.getTitle(), item.getColumnKey(), categoryId);
            }
            try {
                var date = new SimpleDateFormat("yyyy-MM-dd").parse(items.getKey());
                emflmt.setTarih(date);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            templates.add(emflmt);
        }
        if (templates != null && !templates.isEmpty()) {
            Collections.reverse(templates);
            templates.sort(Comparator.comparing(Lunch::getTarih));

        }

        new CreateExcel().Create(templates, removeFirst);
    }

    public void ExportDayOfMenuBreakFast(Date startDate, Date endDate, int categoryId) {
        boolean removeFirst = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<DayOfMenuWithDetailViewModel> dayOfMenuWithDetailViewModelList = new DbManager(DayOfMenuWithDetailViewModel.class).Get(
                "menu_date between '" + format.format(startDate) + "' and '" + format.format(endDate) + "' and category_id=" + categoryId + " order by menu_date");

        List<Breakfast> templates = new ArrayList<>();
        Map<String, List<DayOfMenuWithDetailViewModel>> modelMap = new HashMap<>();
        for (var item : dayOfMenuWithDetailViewModelList) {
            if (modelMap.get(format.format(item.getMenuDate())) == null) {
                modelMap.put(format.format(item.getMenuDate()), new ArrayList<>());
            }
            modelMap.get(format.format(item.getMenuDate())).add(item);

        }
        for (var items : modelMap.entrySet()) {
            var emflmt = new Breakfast();

            for (var item : items.getValue()) {
                emflmt = (Breakfast) swap(emflmt, item, categoryId);
            }
            try {
                var date = new SimpleDateFormat("yyyy-MM-dd").parse(items.getKey());
                emflmt.setTarih(date);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            templates.add(emflmt);
        }
        if (templates != null && !templates.isEmpty()) {
            Collections.reverse(templates);
            templates.sort(Comparator.comparing(Breakfast::getTarih));

        }
        if (templates.isEmpty()) {
            SablonExportDayOfMenu(1);
        } else new CreateExcel().Create(templates, removeFirst);

    }

    public IExcel swapBreakfast(IExcel emflmt, String title, String categoryName) {
        var ef = (Breakfast) emflmt;
        try {
            var fields = ef.getClass().getDeclaredFields();

            for (var item : fields) {
                int a = 0;

                a = a;


                if (item.getAnnotation(AExcelColumn.class) == null)
                    continue;
                var aexcolumn = item.getAnnotation(AExcelColumn.class);
                if (aexcolumn == null)
                    continue;
                if (!aexcolumn.columnKey().equals(categoryName))
                    continue;
                var item_prop = PropertyUtils.getPropertyDescriptor(ef, item.getName());
                DataService.setFieldWithMethodInvoke(ef, item_prop.getWriteMethod(), item, title);
            }

        } catch (Exception e) {
            int a = 0;
            a = a;
        } finally {
            return ef;
        }

    }

    public IExcel swapLunch2(IExcel emflmt, String title, String categoryName) {
        var ef = (Lunch) emflmt;
        try {
            var fields = ef.getClass().getDeclaredFields();

            for (var item : fields) {
                int a = 0;

                a = a;


                if (item.getAnnotation(AExcelColumn.class) == null)
                    continue;
                var aexcolumn = item.getAnnotation(AExcelColumn.class);
                if (aexcolumn == null)
                    continue;
                if (!aexcolumn.columnKey().equals(categoryName))
                    continue;
                var item_prop = PropertyUtils.getPropertyDescriptor(ef, item.getName());
                DataService.setFieldWithMethodInvoke(ef, item_prop.getWriteMethod(), item, title);
            }

        } catch (Exception e) {
            int a = 0;
            a = a;
        } finally {
            return ef;
        }

    }

    public IExcel swap(IExcel emflmt, DayOfMenuWithDetailViewModel item, int typeS) {
        if (typeS == 1)
            return swapBreakfast(emflmt, item.getTitle(), item.getColumnKey());
        return swapLunch2(emflmt, item.getTitle(), item.getCategoryName());
    }

    public IExcel swapSS(IExcel emflmt, String title, String categoryName, int typeS) {
        if (typeS == 1)
            return swapBreakfast(emflmt, title, categoryName);
        return swapLunch2(emflmt, title, categoryName);
    }


    public void SablonExportDayOfMenu(int selectedExport) {
        List<Lunch> templates = new ArrayList<>();
        List<CounterModelStringString> models = new ArrayList<>();


        models = PostgreSqlManager.Read(new CounterModelStringString(), "SELECT fc.food_category_id, MIN(title) as value,fc.food_category_name AS name FROM food join food_category fc on food.food_category_id = fc.food_category_id  where fc.category=" + selectedExport + " GROUP BY fc.food_category_id  ORDER BY fc.food_category_id ASC;\n");
        if (selectedExport == 1)
            SablonExportDayOfMenuBreakFast(models, selectedExport);
        else
            SablonExportDayOfMenuLunchDinnerFast(models, selectedExport);

    }

    public void SablonExportDayOfMenuBreakFast(List<CounterModelStringString> models, int selectedExport) {
        List<Breakfast> templates = new ArrayList<>();
        var emflmt = new Breakfast(new Date(), "", "", "", "", "", "", "", "", "", "", 1);
        var fields = emflmt.getClass().getDeclaredFields();
        for (var item : models) {
            for (var field : fields) {
                if (field.getName().contains(item.getNames())) {
                    var fEXA = field.getAnnotation(AExcelColumn.class);
                    if (fEXA != null)
                        emflmt = (Breakfast) swapSS(emflmt, item.getValue(), fEXA.columnKey() + "", selectedExport);
                }
            }

        }
        emflmt.setEk1(emflmt.getKahvalti11());
        emflmt.setEk2(emflmt.getKahvalti11());
        emflmt.setEk3(emflmt.getKahvalti11());
        emflmt.setEk4(emflmt.getKahvalti11());
        emflmt.setEk5(emflmt.getKahvalti11());

        //emflmt = new Breakfast(new Date(), emflmt.getKahvalti1(), emflmt.getKahvalti1(), emflmt.getKahvalti1(), emflmt.getKahvalti1(), emflmt.getKahvalti1(), 1);

        emflmt.setTarih(new Date());
// templates.add(new ExcelDayOfMonthTemplate(),0);
        templates.add(0, emflmt);

        new CreateExcel().Create(templates, false, true);
    }

    public void SablonExportDayOfMenuLunchDinnerFast(List<CounterModelStringString> models, int selectedExport) {
        List<Lunch> templates = new ArrayList<>();
        var emflmt = new Lunch(new Date(), "", "", "", "", "", "", "", "", 1);

        for (var item : models) {
            emflmt = (Lunch) swapSS(emflmt, item.getValue(), item.getNames() + "", selectedExport);
        }
        emflmt.setEk1(emflmt.getAna());
        emflmt.setEk2(emflmt.getCorba());
        emflmt.setTarih(new Date());
// templates.add(new ExcelDayOfMonthTemplate(),0);
        templates.add(0, emflmt);

        new CreateExcel().Create(templates, true);
    }

    public static List<CompModel> getCompModelList(int categoryId, String dompId) {
        List<CompModel> list = new ArrayList<>();
        List<DayOfMenuWithDetailViewModel> liste = new DbManager(DayOfMenuWithDetailViewModel.class).Get("category_id=" + categoryId," and ", "day_of_menu_parent_id=" + dompId);
        if (categoryId == 2) {
            list.add(new CompModel("corba", false, 2, 1, false));
            list.add(new CompModel("ana", false, 2, 2, false));
            list.add(new CompModel("ara", false, 2, 3, false));
            list.add(new CompModel("tatli", false, 2, 4, false));
            list.add(new CompModel("salata", false, 2, 5, false));
            list.add(new CompModel("icecek", false, 2, 6, false));
            list.add(new CompModel("plus", false, 2, -1, true));
            list.add(new CompModel("plustwo", false, 2, -1, true));
        } else if (categoryId == 3) {
            list.add(new CompModel("corba", false, 3, 1, false));
            list.add(new CompModel("ana", false, 3, 2, false));
            list.add(new CompModel("ara", false, 3, 3, false));
            list.add(new CompModel("tatli", false, 3, 4, false));
            list.add(new CompModel("salata", false, 3, 5, false));
            list.add(new CompModel("icecek", false, 3, 6, false));
            list.add(new CompModel("plus", false, 3, -1, true));
            list.add(new CompModel("plusTwo", false, 3, -1, true));

        } else {
            list.add(new CompModel("kahvalti1", false, 1, 7, false));
            list.add(new CompModel("kahvalti2", false, 1, 7, false));
            list.add(new CompModel("kahvalti3", false, 1, 7, false));
            list.add(new CompModel("kahvalti4", false, 1, 7, false));
            list.add(new CompModel("kahvalti5", false, 1, 7, false));
            list.add(new CompModel("kahvalti6", false, 1, -1, true));
            list.add(new CompModel("kahvalti7", false, 1, -1, true));
            list.add(new CompModel("kahvalti8", false, 1, -1, true));
            list.add(new CompModel("kahvalti9", false, 1, -1, true));
            list.add(new CompModel("kahvalti10", false, 1, -1, true));
        }
        for (var item:liste) {
            for (var val:list) {
                if (val.getColumnkey().equals(item.getColumnKey()))
                {
                    val.setUse(true);
                    break;
                }
            }
        }
        return list;
    }
}
