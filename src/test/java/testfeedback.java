import dininghall.domain.models.feedback.FeedA;
import dininghall.domain.models.feedback.FeedD;
import dininghall.domain.survey.FeedBackAvg;
import dininghall.domain.survey.models.FeedBackAvgModel;
import dininghall.generic.LazyPack.LazyService;
import dininghall.generic.Manager.DbManager;

import java.math.BigDecimal;
import java.util.*;

public class testfeedback {


    public static void main(String[] args) {
        var a= MapList();
    }

    private static   Map<String, FeedD> MapList() {
        List<FeedBackAvg> list = new DbManager(FeedBackAvg.class).GetAll();
        Map<String, FeedD> list_map = new HashMap<>();
        for (var item : list) {
            if (list_map.get(item.getDompId()) == null) {
                var ffedd = new FeedD();
                ffedd.setCategoryId(item.getCategoryId());
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
                as.setCategoryName(item.getFoodCategoryNames().get(j));
                list_map.get(item.getDompId() + "").getList().add(as);

            }
        }

        return list_map;
    }
}
