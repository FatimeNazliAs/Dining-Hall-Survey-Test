package dininghall.domain.email;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;

@Data
@ASqlTable(TableName = "email_send")
public class EmailSend implements ISqlTable, Serializable {
    @AColumn(name = "id", primary = true)
    int id;
    @AColumn(name = "text_header")
    String header;
    @AColumn(name = "text_body")
    String body;
    @AColumn(name = "who_local_user_id")
    long senderLocalUserId;
    @AColumn(name = "recipient_local_user_id")
    long recipientLocalUserId;
    @AColumn(name = "template_name")
    String templateName;
    @AColumn(name = "class_name")
    String className;
    @AColumn(name = "item_id")
    int itemId;
}
