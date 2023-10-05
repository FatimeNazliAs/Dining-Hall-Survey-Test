import dininghall.domain.survey.FeedBackVote;
import dininghall.generic.LazyPack.LazyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbTest
{
    public static void main(String[] args)
    {

        List<FeedBackVote> items = new LazyService().FilterOperation(null, new FeedBackVote(), 0, 100, new HashMap<>(), new HashMap<>(), "", "domp_id=805");
        List<String> labels = new ArrayList<>();


        labels = labels;
    }

}
