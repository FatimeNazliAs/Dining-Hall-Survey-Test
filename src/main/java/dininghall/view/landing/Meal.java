package dininghall.view.landing;

import lombok.Data;

@Data
public class Meal
{
    private String name;
    private int rating;
    private String comment;
    private String imageUrl;

    public Meal(String name, int rating, String comment, String imageUrl)
    {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.imageUrl = imageUrl;
    }

}
