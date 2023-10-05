package dininghall.view.survey;

import dininghall.domain.models.feedback.FeedA;
import dininghall.domain.models.feedback.FeedD;
import dininghall.domain.survey.FeedBackAvg;
import dininghall.domain.survey.FeedBackDetail;
import dininghall.domain.survey.SqlSyntax;
import dininghall.domain.survey.models.FeedBackAvgModel;
import dininghall.domain.survey.models.FeedBackDetailModel;
import dininghall.generic.LazyPack.LazyService;
import dininghall.generic.Manager.DbManager;
import dininghall.generic.information_schema;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ManagedBean(name = "feedbackListView")
@ViewScoped
@Data
public class FeedbackListView implements Serializable {
    private String[] styleArray = new String[]{"background-color: #e3f6fc;border-radius: 28px;border-bottom-right-radius: 0;border-top-right-radius: 0;",
            "background-color: #9edff34a;border-radius: 40px;border-bottom-left-radius: 0;border-top-left-radius: 0;width:25px;",
            "background-color:white;border-radius: 28px;border-bottom-right-radius: 0;border-top-right-radius: 0;",
            "background-color:white;border-radius: 40px;border-bottom-left-radius: 0;border-top-left-radius: 0;width:25px;"};
    int styleIndex = 0;

    @Data
    class TemplateItem {
        private String val1;
        private String displayLabel;
        private String dbName;
        private int displayIndex;
        private boolean sortAble;

        private boolean multi;
        private boolean nextPrev = false;
        private TemplateItem item;
        int maxSize = 200;
        String style = "0";

        public TemplateItem(String val1, String displayLabel, String dbName, int displayIndex, boolean sortAble) {
            this.val1 = val1;
            this.displayLabel = displayLabel;
            this.dbName = dbName;
            this.displayIndex = displayIndex;
            this.sortAble = sortAble;
            this.multi = multi;
            this.nextPrev = nextPrev;
            this.item = item;
        }

        public TemplateItem(String val1, String displayLabel, String dbName, int displayIndex, boolean sortAble, boolean multi, boolean nextPrev, TemplateItem item) {
            this.val1 = val1;
            this.displayLabel = displayLabel;
            this.dbName = dbName;
            this.displayIndex = displayIndex;
            this.sortAble = sortAble;
            this.multi = multi;
            this.nextPrev = nextPrev;
            this.item = item;
        }
    }

    Map<String, String> keyVl = new HashMap<>();
    private String columnTemplate = "";
    List<TemplateItem> items = new ArrayList<>();

    @Test
    public void addList() {

        var tmplate1 = new TemplateItem("dompId", "Id", "dompId", 0, true);
        var tmplate2 = new TemplateItem("menuDate", "Tarih", "menu_date", 2, true);
        var tmplate3 = new TemplateItem("categoryName", "Öğün", "category_id", 3, true);
        var tmplate4 = new TemplateItem("title1", "Başlangıç", "title", 4, false, true, true, new TemplateItem("rate1", "Oy", "rate", 4, false));
        var tmplate5 = new TemplateItem("title2", "Ana", "title", 5, false, true, true, new TemplateItem("rate2", "Oy", "rate", 5, false));
        var tmplate6 = new TemplateItem("title3", "Ara", "title", 6, false, true, true, new TemplateItem("rate3", "Oy", "rate", 6, false));
        var tmplate7 = new TemplateItem("title4", "Tatlı", "title", 7, false, true, true, new TemplateItem("rate4", "Oy", "rate", 7, false));
        var tmplate8 = new TemplateItem("title5", "Salata", "title", 8, false, true, true, new TemplateItem("rate5", "Oy", "rate", 8, false));
        var tmplate9 = new TemplateItem("title6", "İçecek", "title", 9, false, true, true, new TemplateItem("rate6", "Oy", "rate", 9, false));
        var tmplate10 = new TemplateItem("title7", "EK-1", "title", 10, false, true, true, new TemplateItem("rate7", "Oy", "rate", 10, false));
        var tmplate11 = new TemplateItem("title8", "EK-2", "title", 11, false, true, true, new TemplateItem("rate8", "Oy", "rate", 11, false));
        items.add(tmplate1);
        items.add(tmplate2);
        items.add(tmplate3);
        items.add(tmplate4);
        items.add(tmplate5);
        items.add(tmplate6);
        items.add(tmplate7);
        items.add(tmplate8);
        items.add(tmplate9);
        items.sort((Comparator.comparing(TemplateItem::getDisplayIndex)));

    }

    private String columnNestedTemplate = "";
    private String normalStyle = "normalcolumnclass";
    private String miltinext = "multinextcolumnclass";
    private String multiprevclass = "multiprevcolumnclass";

    LazyDataModel<FeedBackAvgModel> feedBackAvgLazyDataModel;
    LazyDataModel<FeedBackDetailModel> feedBackAvgNestedLazyDataModel;
    LazyDataModel<Map<String, String>> mapkey;
    LazyDataModel<Map<String, String>> mapkey2;

    Map<String, String> selectedMap;
    List<ColumnModel> nestedcolumns;
    List<ColumnModel> columns;
    List<ColumnModel> columnsNested;
    private Map<String, Class> validColumns;
    private Map<String, Class> validNestedColumns;
    private SurveyStacked surveyStacked;
    private int selectedCategory;

    private Map<String, FeedD> MapList(List<FeedBackAvg> list) {

        Map<String, FeedD> list_map = new HashMap<>();
        for (var item : list) {
            if (list_map.get(item.getDompId()) == null) {
                var ffedd = new FeedD();
                ffedd.setCategoryId(item.getCategoryId());
                ffedd.setCategoryName(item.getCategoryName());
                ffedd.setDompId((int) item.getDompId());
                ffedd.setMenuDate(item.getMenuDate());
                ffedd.setList(new ArrayList<>());
                list_map.put(item.getDompId() + "", ffedd);
            }
            int tempIndex = 0;
            for (int j = 0; j < item.getAvgRatings().size(); j++) {

                var bigDecimalValue = (item.getAvgRatings().get(j) + "").substring(0, 3);


                FeedA as = new FeedA();
                as.setKey(item.getColumnKeys().get(j));

                as.setAvgRating(Double.parseDouble(bigDecimalValue));
                as.setTitle(item.getTitles().get(j));
                as.setDispayName(item.getTitles().get(j));
                as.setCategoryName(item.getFoodCategoryNames().get(j));
                list_map.get(item.getDompId() + "").getList().add(as);

            }
        }

        return list_map;
    }

    private Map<String, FeedD> MapList2(List<FeedBackDetail> list) {
        Map<String, FeedD> list_map = new HashMap<>();
        for (var item : list) {
            if (list_map.get(item.getSurveyId()) == null) {
                var ffedd = new FeedD();

                ffedd.setDompId((int) item.getSurveyId());
                ffedd.setList(new ArrayList<>());
                list_map.put(item.getSurveyId() + "", ffedd);
            }
            int tempIndex = 0;
            for (int j = 0; j < item.getTitles().size(); j++) {

                var bigDecimalValue = (item.getRatings().get(j) + "");


                FeedA as = new FeedA();
                as.setKey(item.getColumnKeys().get(j));

                as.setAvgRating(Double.parseDouble(bigDecimalValue));
                as.setTitle(item.getTitles().get(j));
                as.setDispayName(item.getAnswers().get(j));
                as.setCategoryName(item.getFoodCategoryNames().get(j));
                list_map.get(item.getSurveyId() + "").getList().add(as);

            }
        }

        return list_map;
    }

    @Test
    List<Map<String, String>> testcode(Map<String, FeedD> items) {


        Map<Integer, Map<String, Integer>> list_map = new HashMap<>();
        Map<String, String> sa = new HashMap<>();
        String columsa = "";
        List<Map<String, String>> datatableItems = new ArrayList<>();
        validColumns = new HashMap<>();
        this.items = new ArrayList<>();
        for (var item : items.entrySet()) {
            Map<String, String> maplist = new HashMap<>();

            var i = getCollect("dompId");
            if (i.size() == 0) {
                var tmplate1 = new TemplateItem("dompId", "ID", "dompId", 0, true);
                validColumns.put("dompId", int.class);
                this.items.add(tmplate1);

            }

            i = getCollect("menuDate");
            if (i.size() == 0) {
                var tmplate1 = new TemplateItem("menuDate", "menuDate", "menuDate", 0, true);
                validColumns.put("menuDate", Date.class);
                this.items.add(tmplate1);
            }
            i = getCollect("categoryName");
            if (i.size() == 0) {
                var tmplate1 = new TemplateItem("categoryName", "categoryName", "categoryName", 0, true);
                validColumns.put("categoryName", int.class);
                this.items.add(tmplate1);
            }

            maplist.put("dompId", item.getValue().getDompId() + "");
            maplist.put("menuDate", item.getValue().getMenuDate() + "");
            maplist.put("categoryName", item.getValue().getCategoryName());


            for (var value : item.getValue().getList()) {
                i = getCollect(value.getKey());
                if (i.size() == 0) {
                    var tmplate11 = new TemplateItem(value.getKey() + "", value.getCategoryName(), "title",
                            11, false, true, true, new TemplateItem("rate" + value.getKey(),
                            "Oy", "rate", 11, false));
                    validColumns.put(value.getKey(), String.class);
                    validColumns.put("rate" + value.getKey(), String.class);

                    this.items.add(tmplate11);

                }
                maplist.put(value.getKey() + "", value.getTitle() + "");
                maplist.put("rate" + value.getKey() + "", value.getAvgRating() + "");


            }
            datatableItems.add(maplist);


        }
        //  validColumns = Stream.of(FeedBackAvgModel.class.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Field::getType));
        columns = createDynamicColumns(columnTemplate, validColumns, false);
        // getFeedBackAvgLazyDataModel().setRowCount(0);

        return datatableItems;
    }

    List<Map<String, String>> testcode2(Map<String, FeedD> items) {


        Map<Integer, Map<String, Integer>> list_map = new HashMap<>();
        Map<String, String> sa = new HashMap<>();
        String columsa = "";
        List<Map<String, String>> datatableItems = new ArrayList<>();
        validColumns = new HashMap<>();
        nestedcolumns = new ArrayList<>();
        this.items = new ArrayList<>();
        for (var item : items.entrySet()) {
            Map<String, String> maplist = new HashMap<>();

            var i = getCollect("dompId");
            if (i.size() == 0) {
                var tmplate1 = new TemplateItem("dompId", "ID", "surveyId", 0, true);
                validColumns.put("dompId", int.class);
                this.items.add(tmplate1);

            }


            maplist.put("dompId", item.getValue().getDompId() + "");


            for (var value : item.getValue().getList()) {
                i = getCollect(value.getKey());
                if (i.size() == 0) {
                    var tmplate11 = new TemplateItem(value.getKey() + "", value.getTitle(), "title",
                            11, false, true, true, new TemplateItem("rate" + value.getKey(),
                            "Oy", "rate", 11, false));
                    validColumns.put(value.getKey(), String.class);
                    validColumns.put("rate" + value.getKey(), String.class);

                    this.items.add(tmplate11);

                }
                maplist.put(value.getKey() + "", value.getDispayName() + "");
                maplist.put("rate" + value.getKey() + "", value.getAvgRating() + "");


            }
            datatableItems.add(maplist);


        }
        //  validColumns = Stream.of(FeedBackAvgModel.class.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Field::getType));
        nestedcolumns = createDynamicColumns(columnTemplate, validColumns, true);
        // getFeedBackAvgLazyDataModel().setRowCount(0);

        return datatableItems;
    }

    private List<Map.Entry<String, Class>> getCollect(String key) {
        return validColumns.entrySet().stream()
                .filter(item -> key.equals(item.getKey()))
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void init() {
        // surveyStacked=new SurveyStacked(805);
        mapkey = new LazyDataModel<Map<String, String>>() {
            @Override
            public Map<String, String> getRowData(String rowKey) {
                for (var item : mapkey.getWrappedData()) {
                    if (item.get("dompId").equals(rowKey)) {
                        return item;
                    }
                }

                return null;
            }

            @Override
            public String getRowKey(Map<String, String> item) {
                return String.valueOf(item.get("dompId"));
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                return 0;
            }

            @Override
            public List<Map<String, String>> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {


                String sqlSq = "";
                Map<String, SortMeta> metaMap = new HashMap<>();
                Map<String, FilterMeta> filterMap = new HashMap<>();
          /*      if (map1.get("globalFilter") != null) {
                    sqlSq += SqlSyntax.getCross(map1.get("globalFilter").getFilterValue().toString());
                    map1.remove("globalFilter");
                }
*/
                boolean andA = false;
                sqlSq += " 1=1  ";
                if (selectedCategory > 0)
                    sqlSq += " and category_id=" + selectedCategory + " ";
                if (map1.get("globalFilter") != null) {
                    var strglobal = map1.get("globalFilter").getFilterValue().toString();
                    strglobal = " and EXISTS (" +
                            "    SELECT *" +
                            "    FROM unnest(titles) AS title" +
                            "    WHERE title ILIKE '%" + strglobal + "%'" +
                            "    ) ";
                    sqlSq += strglobal;
                }
                ;
                DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:feedBackAvgListDT");



                columns = new ArrayList<>();
                table.resetDynamicColumns();


                List<FeedBackAvg> dbList = new DbManager(FeedBackAvg.class).Get(sqlSq);
                var items = MapList(dbList);
                var itemListMap = testcode(items);
                mapkey.setRowCount(itemListMap.size());
                mapkey.setWrappedData(itemListMap);



                columns = columns;
                return itemListMap;

            }
        };
    }


    public void addMessage() {
    }

    //yeni FeedBackAvg eklerken seçili FeedBackAvg temizleme


    //yeni FeedBackAvg ekleme veya düzenleme kodu


    public void clearFilters() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:feedBackAvgListDT");
        if (dataTable != null) {
            dataTable.reset();

            PrimeFaces.current().ajax().update("form:msgs", "form:feedBackAvgListDT");
        }

    }

    private List<ColumnModel> createDynamicColumns(String columnTemplate, Map<String, Class> validColumns, boolean sizeAble) {


        // addList();
        var columns = new ArrayList<ColumnModel>();
        for (var item : items) {
            String key = item.val1;


            if (validColumns.containsKey(item.val1)) {


                columns.add(new ColumnModel(item.displayLabel.toUpperCase(), item.val1, validColumns.get(item.val1), item.sortAble, item.maxSize + "", item.style, item.isMulti() ? miltinext : normalStyle));

                if (item.isMulti()) {
                    var item_m = item.item;
                    columns.add(new ColumnModel(item_m.displayLabel.toUpperCase(), item_m.val1, validColumns.get(item_m.val1), item_m.sortAble, 30 + "", item_m.style, multiprevclass));
                }
            }
        }
        return columns;
    }


    public void updateColumns() {
        //reset table state
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:customers");
        table.resetDynamicColumns();

        //update columns
        // createDynamicColumns();
    }

    public void openNew() {
        if (selectedMap == null)
            return;

        if (surveyStacked == null || !selectedMap.get("dompId").equals(surveyStacked.getDompId() + ""))
            surveyStacked = new SurveyStacked(Long.parseLong(selectedMap.get("dompId")));

        columnNestedTemplate = "";

        mapkey2 = new LazyDataModel<Map<String, String>>() {
            FilterMeta initFilter;

            @Override
            public int count(Map<String, FilterMeta> map) {
                return 0;
            }

            @Override
            public List<Map<String, String>> load(int i, int i1, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                if (initFilter == null) {
                    initFilter = FilterMeta.builder().filterValue(selectedMap.get("dompId")).field("dompId").matchMode(MatchMode.EQUALS).build();

                }
                map1.put("dompId", initFilter);
                String sqlSq = "";
                List<FeedBackDetail> list = new LazyService().FilterOperation(null, new FeedBackDetail(), i, i1, map, map1, "", sqlSq);
                List<FeedBackDetailModel> modelList = new ArrayList<>();


                var items = MapList2(list);
                var itemListMap = testcode2(items);
                mapkey2.setRowCount(itemListMap.size());
                mapkey2.setWrappedData(itemListMap);

                return itemListMap;
            }
        };
    }

    public void onRowSelect() {
        surveyStacked = new SurveyStacked(Long.parseLong(selectedMap.get("dompId")));
    }

    public boolean getNullControl() {
        return selectedMap != null;
    }

    @Data
    public static class ColumnModel implements Serializable {

        private String header;
        private String property;
        private String type;
        private String text;
        private Object value;
        private int maxSize;
        private int colspan;
        private boolean sortAble = false;
        private String style = "";
        private Class<?> klazz;

        public ColumnModel(String header, String property, Class klazz, boolean sortAble) {
            this.header = header;
            this.property = property;
            this.klazz = klazz;
            this.sortAble = sortAble;
            initType();
        }

        public ColumnModel(String header, String property, Class klazz, boolean sortAble, String maxSize, String colspan, String style) {
            this.colspan = Integer.parseInt(colspan);
            this.maxSize = Integer.parseInt(maxSize);
            this.style = style;
            this.header = header;
            this.property = property;
            this.klazz = klazz;
            this.sortAble = sortAble;
            initType();
        }

        public ColumnModel(String header, String property, Class klazz, boolean sortAble, String text) {
            this.header = header;
            this.property = property;
            this.klazz = klazz;
            this.sortAble = sortAble;
            this.text = text;
            initType();
        }


        private void initType() {
            if (Temporal.class.isAssignableFrom(klazz)) {
                type = "date";
            } else if (klazz == Date.class) {
                type = "date";
            } else if (klazz.isEnum()) {
                type = "enum";
            }
        }
    }

}
