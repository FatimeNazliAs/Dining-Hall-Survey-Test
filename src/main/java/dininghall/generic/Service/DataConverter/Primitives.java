package dininghall.generic.Service.DataConverter;

import org.postgresql.jdbc.PgArray;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Primitives {

    public static <T> Object Convert(T type, Object data) throws SQLException
    {
        var type_ = (Field) type;

        if ((type_.getType() == ArrayList.class || type_.getType()==List.class) && data instanceof PgArray) {
            var dataString = ((PgArray) data);
            try {
                var obj = (Object[]) dataString.getArray();
                ArrayList list = new ArrayList<>();
                for (var item : obj) {
                    list.add(item);
                }
                return list;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (data instanceof BigDecimal) {
            var sad = ((Field) type).getType().getName();
            if (sad.equalsIgnoreCase("double"))
                return (((BigDecimal) data).doubleValue());
        }
        return (T) data;
    }

    private <T> T convert(T t, Object data) {
        return (T) data;
    }

}

