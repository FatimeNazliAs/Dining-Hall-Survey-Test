package dininghall.domain.models.feedback;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class FeedD {
            int dompId;
            Date menuDate;
            int categoryId;
            String  categoryName;

            List<FeedA> list=new ArrayList<>();

    }
