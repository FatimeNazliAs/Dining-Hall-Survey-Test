package dininghall.domain.document;

import lombok.Data;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;
import java.util.Date;

@ASqlTable(TableName = "satellite_contract_view")
@Data
public class ContractModelView implements ISqlTable, Serializable {
    @AColumn(name = "satelitte_contract_id")
    private String satelliteContractId;
    @AColumn(name = "sozlesmeno")
    private String sozlesmeno;
    @AColumn(name = "adsoyad")
    private String adsoyad;
    @AColumn(name = "islemtarihi")
    private String islemtarihi;
    private Date sendDate;
    @AColumn(name = "sender_local_user_id")
    private long senderLocalUserId;
    @AColumn(name = "sender_full_name")
    private String senderFullName;

}
