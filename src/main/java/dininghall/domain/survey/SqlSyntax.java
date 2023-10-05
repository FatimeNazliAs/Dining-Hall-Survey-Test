package dininghall.domain.survey;

public class SqlSyntax
{
   public static String getCross(String value)
   {
       return "        EXISTS (\n" +" SELECT 1 FROM unnest(titles) t(title) WHERE title ILIKE '%"+value+"%'\n" + " )";

   }

    String sql="select * from (SELECT subquery.menu_date,\n" +
            "       subquery.domp_id,\n" +
            "       subquery.category_id,\n" +
            "       array_agg(subquery.food_id)            AS food_ids,\n" +
            "       array_agg(subquery.title)              AS titles,\n" +
            "       array_agg(subquery.food_category_id)   AS food_category_ids,\n" +
            "       array_agg(subquery.food_category_name) AS food_category_names,\n" +
            "       array_agg(subquery.avg_rating)         AS avg_ratings\n" +
            "FROM (SELECT domp.menu_date,\n" +
            "             domp.domp_id,\n" +
            "             domp.category_id,\n" +
            "             dom.food_id,\n" +
            "             f.food_category_id,\n" +
            "             fct.food_category_name,\n" +
            "             f.title,\n" +
            "             avg(si.rating) AS avg_rating\n" +
            "      FROM day_of_menu_parent domp\n" +
            "               JOIN day_of_menu dom ON dom.day_of_menu_parent_id = domp.domp_id\n" +
            "               JOIN food f ON f.food_id = dom.food_id\n" +
            "               JOIN food_category fct ON fct.food_category_id = f.food_category_id\n" +
            "               JOIN survey_item si ON dom.day_of_menu_id = si.day_of_menu_id\n" +
            "      GROUP BY domp.menu_date, domp.category_id, domp.domp_id, dom.food_id, f.title, fct.food_category_name, f.food_category_id) subquery\n" +
            "\n" +
            "GROUP BY subquery.menu_date, subquery.category_id, subquery.domp_id) as tbl\n" +
            "where 710=any(tbl.food_ids);";
}
