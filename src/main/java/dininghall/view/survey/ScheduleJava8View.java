package dininghall.view.survey;

import dininghall.asstpackages.ExcelPackage.Models.ExcelOutData;
import dininghall.asstpackages.ExcelPackage.Models.ExcelReadOut;
import dininghall.asstpackages.ExcelPackage.Models.OutPutExcel;
import dininghall.domain.survey.DayOfMenuParentVW;
import dininghall.domain.survey.SurveyExcel.DayOfMenuManager;
import dininghall.domain.survey.SurveyExcel.SurveyExcelManager;
import dininghall.domain.survey.exceltemplate.Breakfast;
import dininghall.domain.survey.models.DayOfMenuWithDetailViewModel;
import dininghall.generic.Manager.PostgreSqlManager;
import dininghall.view.survey.helper.CustomSchedule;
import dininghall.view.survey.models.CompModel;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.timeline.TimelineRangeEvent;
import org.primefaces.model.*;
import dininghall.domain.survey.DayOfMenu;
import dininghall.domain.survey.DayOfMenuParent;
import dininghall.domain.survey.exceltemplate.Lunch;
import dininghall.generic.Manager.DbManager;
import dininghall.view.survey.models.OptionMenu;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static javax.faces.application.FacesMessage.*;

@ManagedBean(name = "scheduleJava8View")
@ViewScoped
@Data
public class ScheduleJava8View implements Serializable {
    OutPutExcel outputs = new OutPutExcel();

    @ManagedProperty("#{extenderService}")
    private ExtenderService extenderService;
    private AutoCompleteView autoComplate;
    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

    private boolean slotEventOverlap = true;
    private boolean showWeekNumbers = false;
    private boolean showHeader = true;
    private boolean draggable = false;
    private boolean resizable = false;
    private boolean selectable = false;
    private boolean showWeekends = false;
    private boolean tooltip = true;
    private boolean allDaySlot = true;
    private boolean rtl = false;
    private double aspectRatio = Double.MIN_VALUE;
    private String leftHeaderTemplate = "prev,next today";
    private String centerHeaderTemplate = "title";
    private String rightHeaderTemplate = "dayGridMonth,timeGridWeek,timeGridDay,listYear";
    private String nextDayThreshold = "09:00:00";
    private String weekNumberCalculation = "local";
    private String weekNumberCalculator = "date.getTime()";
    private String displayEventEnd;
    private String timeFormat;
    private String slotDuration = "00:30:00";
    private String slotLabelInterval;
    private String slotLabelFormat;
    private String scrollTime = "06:00:00";
    private String minTime = "04:00:00";
    private String maxTime = "20:00:00";
    private String locale = "en";
    private String serverTimeZone = ZoneId.systemDefault().toString();
    private String timeZone = "";
    private String clientTimeZone = "local";
    private String columnHeaderFormat = "";
    private String view = "timeGridWeek";
    private String height = "auto";

    private String extenderCode = "// Write your code here or select an example from above";
    private String selectedExtenderExample = "";
    private List<Date> range;
    private int selectedImport = 1;
    private Map<String, ExtenderService.ExtenderExample> extenderExamples;

    @PostConstruct
    public void init() {
        PostgreSqlManager.Command("DELETE FROM day_of_menu_parent WHERE domp_id IN (SELECT distinct day_of_menu_parent_view.domp_id FROM day_of_menu_parent_view where child_count=0)");

        eventModel = new DefaultScheduleModel();

        addEvents2EventModel(LocalDateTime.now());
        lazyEventModel = new LazyScheduleModel() {

            @Override
            public void loadEvents(LocalDateTime start, LocalDateTime end) {
                for (int i = 1; i <= 5; i++) {
                    LocalDateTime random = getRandomDateTime(start);
                    addEvent(DefaultScheduleEvent.builder()
                            .title("Lazy Event " + i)
                            .startDate(random)
                            .endDate(random.plusHours(3))
                            .build());
                }
            }
        };

        extenderExamples = extenderService.createExtenderExamples();
        if (autoComplate == null) {
            autoComplate = new AutoCompleteView();
            autoComplate.init();
        }

    }


    private void addEvents2EventModel(LocalDateTime referenceDate) {
        ArrayList<DayOfMenuParentVW> list = new DbManager(DayOfMenuParentVW.class).GetAll();
        Map<String, Map<Date, List<DayOfMenuParent>>> menuMap = new HashMap<>();

        for (var item : list) {
            if (menuMap.get(item.getCategoryId() + "") == null)
                menuMap.put(item.getCategoryId() + "", new HashMap<>());
            if (menuMap.get(item.getCategoryId() + "").get(item.getMenuDate()) == null)
                menuMap.get(item.getCategoryId() + "").put(item.getMenuDate(), new ArrayList<>());
            DayOfMenuParent item_real = new DayOfMenuParent();
            item_real.setCategoryId(item.getCategoryId());
            item_real.setCreatedDate(item.getCreatedDate());
            item_real.setDompId(item.getDompId());
            item_real.setMenuDate(item.getMenuDate());
            item_real.setLocalUserId(item.getLocalUserId());
            item_real.setUpdatedDate(item.getUpdatedDate());
            item_real.setChildCount(item.getChildrenCount());
            menuMap.get(item.getCategoryId() + "").get(item.getMenuDate()).add(item_real);


        }
        for (var item : menuMap.entrySet()) {
            for (var dt : item.getValue().entrySet()) {
                Date date = new Date(dt.getKey().getTime());

                LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                //localDateTime = localDateTime.withYear(2023).withMonth(2);
                int category = -1;
                if (item.getKey().equals("1"))
                    category = CustomSchedule.breakfast;
                else if (item.getKey().equals("2"))
                    category = CustomSchedule.lunch;
                else if (item.getKey().equals("3"))
                    category = CustomSchedule.dinner;

                eventModel.addEvent(CustomSchedule.FoodTime(localDateTime,
                        category, dt.getValue().get(0).getDompId(), dt.getValue().get(0).getChildCount()));
            }

        }
    }


    public LocalDateTime getRandomDateTime(LocalDateTime base) {
        LocalDateTime dateTime = base.withMinute(0).withSecond(0).withNano(0);
        return dateTime.plusDays(((int) (Math.random() * 30)));
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }


    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }


    public void addDayOfMenu(Date date) {
        List<CompModel> list = DayOfMenuManager.getCompModelList(autoComplate.getCategoryId(), autoComplate.getDompId());


        DayOfMenuParent parent = new DayOfMenuParent();
        parent.setMenuDate(date);
        parent.setCategoryId(autoComplate.getCategoryId());
        parent.setDompId(new DbManager(parent).Add());
        int i = 0;
        int next = 0;
        for (var item : autoComplate.getOptionMenuList()) {

            DayOfMenu menu = new DayOfMenu();
            menu.setDayOfMenuParentId(parent.getDompId());
            String str = columnkeychange(list, item, menu, next);
            menu.setColumnKey(str);
            menu.setFoodId(item.getSelectedFood().getFoodId());
            if (menu.getColumnKey() == null || menu.getColumnKey().length() == 0)
                continue;
            int id = new DbManager(menu).Add();
            id = id;

        }
    }

    private String columnkeychange(List<CompModel> list, OptionMenu item, DayOfMenu menu, int next) {

        boolean find = false;
        for (var t : list) {

            if (t.getFoodCategoryId() == item.getSelectedFood().getFoodCategoryId() && autoComplate.getCategoryId() == t.getCategoryId()) {
                if (!t.isUse()) {

                    t.setUse(true);
                    return t.getColumnkey();
                }


            }

        }
        for (var findItem : list) {
            if (findItem.isExtra() && !findItem.isUse()) {
                findItem.setUse(true);
                return findItem.getColumnkey();
            }
        }


        return "";
    }

    public void updateDayOfMenu(String id) {

        DayOfMenuWithDetailViewModel dayOfMenuWithDetailViewModel = null;
        List<OptionMenu> mewModelList = new ArrayList<>();
        Map<String, Object> mapList = new HashMap<>();
        mapList.put("category_id", autoComplate.getCategoryId());
        List<DayOfMenuWithDetailViewModel> dblist = new DbManager(DayOfMenuWithDetailViewModel.class).Get("day_of_menu_parent_id=" + id);
        List<CompModel> list = DayOfMenuManager.getCompModelList(autoComplate.getCategoryId(), autoComplate.getDompId());

        int next = 0;
        for (var item : list) {
            for (var dbitem : dblist) {
                if (item.getCategoryId() == dbitem.getCategoryId() && item.getColumnkey().equals(dbitem.getColumnKey())) {
                    item.setUse(true);
                    if (item.getColumnkey().contains("plus"))
                        next++;
                    break;
                }

            }
        }


        var upScs_parent = new DbManager(DayOfMenuParent.class).UpdateMap(mapList, id + "");

        for (var item : autoComplate.getOptionMenuList()) {
            if (item.getDofmVW() == null || item.getDofmVW().getDayOfMenuId() == 0) {
                mewModelList.add(item);

            } else {
                //  date=item.getDofmVW().getMenuDate();
                dayOfMenuWithDetailViewModel = item.getDofmVW();
                mapList = new HashMap<>();
                mapList.put("food_id", item.getSelectedFood().getFoodId());
                boolean find = false;
                boolean noChange = false;
                for (var it : dblist) {
                    if (it.getColumnKey().equals(item.getDofmVW().getColumnKey())) {

                        if (item.getSelectedFood().getFoodId() == it.getFoodId()) {
                            noChange = true;
                            break;
                        }
                        if (item.getDofmVW().getFoodCategoryId() != item.getSelectedFood().getFoodCategoryId()) {
                            for (var t : list) {
                                if (t.getColumnkey().equals(it.getColumnKey())) {
                                    if (item.getSelectedFood().getFoodId() != it.getFoodId()) {
                                        t.setUse(false);

                                        find = true;
                                        break;
                                    }

                                }
                            }
                        }
                    }
                }
                if (find) {
                    var str = columnkeychange(list, item, null, next);
                    mapList.put("column_key", str);

                }
                if (!noChange) {
                    var upScs_child = new DbManager(DayOfMenu.class).UpdateMap(mapList, item.getDofmVW().getDayOfMenuId() + "");
                    upScs_child = upScs_child;
                }
            }

        }


        for (var item : mewModelList) {


            DayOfMenu menu = new DayOfMenu();
            menu.setDayOfMenuParentId(Integer.parseInt(id + ""));
            String str = columnkeychange(list, item, menu, next);
            if (str.length()==0)
                continue;
            menu.setColumnKey(str);

            menu.setFoodId(item.getSelectedFood().getFoodId());

            int id_return = new DbManager(menu).Add();
            id_return = id_return;

        }
    }

    public void addEvent() {
        Instant instant = event.getStartDate().atZone(ZoneId.systemDefault()).toInstant();
        // Create a Date object from the Instant
        Date date = Date.from(instant);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        var dtStr = format.format(date);
        var obj_ = new DbManager(DayOfMenuParent.class).Get("menu_date='" + dtStr + "' and category_id=" + autoComplate.getCategoryId());
        if (event.isAllDay()) {
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null) {
            if (!obj_.isEmpty())
                return;
            addDayOfMenu(date);
        } else {
            //
            if (!obj_.isEmpty() && !autoComplate.getDompId().equals(autoComplate.getDefaultCategoryId()))
                return;
            updateDayOfMenu(event.getId());
            //eventModel.updateEvent(event);
        }
        this.init();
        event = new DefaultScheduleEvent<>();
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        autoComplate = new AutoCompleteView();
        autoComplate.init();
        autoComplate.setDompId(selectEvent.getObject().getId());
        autoComplate.setDefaultCategoryId(selectEvent.getObject().getId());
        autoComplate.setOptionMenuList(new ArrayList<>());
        DayOfMenuParent parent = new DbManager(DayOfMenuParent.class).GetFirst("domp_id=" + selectEvent.getObject().getId());
        autoComplate.setCategoryId(parent.getCategoryId());
        List<DayOfMenuWithDetailViewModel> modelList = new DbManager(DayOfMenuWithDetailViewModel.class).Get("day_of_menu_parent_id=" + selectEvent.getObject().getId());
        for (var item : modelList) {
            autoComplate.getOptionMenuList().add(autoComplate.newOptionMenu(item));
        }


        event = selectEvent.getObject();
        autoComplate.checkList();
    }

    public void onViewChange(SelectEvent<String> selectEvent) {
        view = selectEvent.getObject();
        FacesMessage message = new FacesMessage(SEVERITY_INFO, "View Changed", "View:" + view);
        addMessage(message);
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        autoComplate = new AutoCompleteView();
        autoComplate.setOptionMenuList(new ArrayList<>());
        autoComplate.init();
        event = DefaultScheduleEvent.builder()
                .startDate(selectEvent.getObject())
                .endDate(selectEvent.getObject().plusHours(1))
                .build();
        autoComplate.checkList();

    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(SEVERITY_INFO, "Event moved",
                "Delta:" + event.getDeltaAsDuration());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(SEVERITY_INFO, "Event resized",
                "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());

        addMessage(message);
    }

    public void onRangeSelect(TimelineRangeEvent event) {
        FacesMessage message = new FacesMessage(SEVERITY_INFO, "Range selected",
                "Start-Date:" + event.getStartDate() + ", End-Date: " + event.getEndDate());

        addMessage(message);
    }

    public void onEventDelete() {
        String eventId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("eventId");
        if (event != null) {
            ScheduleEvent<?> event = eventModel.getEvent(eventId);
            eventModel.deleteEvent(event);
        }
    }

    public void onExtenderExampleSelect(AjaxBehaviorEvent event) {
        ExtenderService.ExtenderExample example = extenderExamples.get(selectedExtenderExample);
        if (!"custom".equals(selectedExtenderExample) && example != null) {
            if (example.getDetails() != null && !example.getDetails().isEmpty()) {
                FacesMessage message = new FacesMessage(example.getName(), example.getDetails());
                FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), message);
            }
            this.extenderCode = example.getValue();
        }
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public double getAspectRatio() {
        return aspectRatio == 0 ? Double.MIN_VALUE : aspectRatio;
    }


    public List<SelectItem> getExtenderExamples() {
        return extenderExamples.values().stream() //
                .sorted(Comparator.comparing(ExtenderService.ExtenderExample::getName)) //
                .map(example -> new SelectItem(example.getKey(), example.getName())) //
                .collect(Collectors.toList());
    }

    public <T> void export() {

        //new SurveyExcelManager().Export();
        //   Date date = new Date();
        //    Calendar calendarEndDate = Calendar.getInstance();
        //      calendarStartDate.set();
        //  calendarEndDate.set(2023, 4, 1);
        FacesMessage message;
        if (range == null || range.get(0) == null || range.get(1) == null) {
            message = new FacesMessage(SEVERITY_WARN, "Başarısız", "Çıkartmak İstediğiniz Tarih Aralığını Girmediniz.!. ");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {

            new SurveyExcelManager().Export(range.get(0), range.get(1), selectedImport);
        }
        PrimeFaces.current().ajax().update("form:messages");

    }

    public void handleFileUpload(FileUploadEvent event) {
        Map<Integer, ExcelOutData> excelOutDataMap = new HashMap<>();
        try {
            if (selectedImport == 0) {
                FacesMessage message = new FacesMessage(SEVERITY_ERROR, "İçeri Aktarım Kategorisini Seçmediniz.", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
            if (selectedImport > 1)
                excelOutDataMap = new SurveyExcelManager().Import(event.getFile().getInputStream(), Lunch.class.getSimpleName(), selectedImport);
            else
                excelOutDataMap = new SurveyExcelManager().Import(event.getFile().getInputStream(), Breakfast.class.getSimpleName(), selectedImport);

            for (var item : excelOutDataMap.entrySet()) {
                var ex = item.getValue();
                outputs = new OutPutExcel();
                for (ExcelReadOut out : ex.getErrors()) {
                    outputs.outputError += out.getRowNumber() + ". Satırda Hata :" + out.getOutText();
                    FacesMessage message = new FacesMessage(SEVERITY_ERROR, "HATA", out.getRowNumber() + ". Satırda Hata :" + out.getOutText());
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    //replace("KEY", "Sutun").replace("already exists.", "veritabanında mevcut.") + " \n";
                }

                for (ExcelReadOut out : ex.getUpdate()) {
                    outputs.outputUpdate += out.getRowNumber() + ". Güncellendi :" + out.getOutText() + "\n";
                }
                for (ExcelReadOut out : ex.getAdd()) {
                    outputs.outputAdd += out.getRowNumber() + ". Eklendi :" + out.getOutText() + "\n";
                }


            }

            // this.originalImageFile = null;
            FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.init();

        } catch (IOException e) {
            // this.originalImageFile = null;
            FacesMessage message = new FacesMessage(SEVERITY_ERROR, "Başarısız", event.getFile().getFileName() + " is not upload.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }


    public void sablonExport() {
        FacesMessage message = new FacesMessage("Aktarım Başladı", "Boş Şablon Dışarı Aktarılıyor.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().update("form:messages");
        if (selectedImport == 2)
            selectedImport = 3;
        new SurveyExcelManager().SablonExport(selectedImport);

    }

    public void testExcel() {
        new SurveyExcelManager().SablonExport(3);

    }

    public void removeItem(OptionMenu optionMenu) {
        optionMenu = optionMenu;
        if (optionMenu.getDofmVW() == null) {
            autoComplate.getOptionMenuList().remove(optionMenu);
        } else {
            new DbManager(DayOfMenu.class).Delete("day_of_menu_id=" + optionMenu.getDofmVW().getDayOfMenuId());
            autoComplate.getOptionMenuList().remove(optionMenu);
            this.init();
            event = new DefaultScheduleEvent<>();
        }

        PrimeFaces.current().ajax().update("formSchedule:eventDetails");
        PrimeFaces.current().executeScript("PF('myschedule').update();PF('eventDialog').update();");


    }
}