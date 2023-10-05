package dininghall.domain.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import dininghall.generic.Interface.AColumn;
import dininghall.generic.Interface.ASqlTable;
import dininghall.generic.Interface.ISqlTable;

import java.io.Serializable;
import java.util.Date;

@ASqlTable(TableName = "satellite_contract")
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Contract implements ISqlTable, Serializable {
    @AColumn(name = "satellite_contract_id", primary = true)
    private int satelliteContractId;
    @AColumn(name = "sozlesmeno")
    private long sozlesmeno;
    @AColumn(name = "adsoyad")
    private String adsoyad;
    @AColumn(name = "islemtarihi")
    private Date islemtarihi;
    @AColumn(name = "paketadi")
    private String paketadi;
    @AColumn(name = "kimlikpassaport")
    private String kimlikpassaport;
    @AColumn(name = "dogumyeri")
    private String dogumyeri;
    @AColumn(name = "dogumtarihi")
    private Date dogumtarihi;
    @AColumn(name = "sokak")
    private String sokak;
    @AColumn(name = "postakodu")
    private String postakodu;
    @AColumn(name = "sehir")
    private String sehir;
    @AColumn(name = "ülke")
    private String ülke;
    @AColumn(name = "eposta")
    private String eposta;
    @AColumn(name = "onikiaylik")
    private double onikiaylik;
    @AColumn(name = "smartkart")
    private String smartkart;
    @AColumn(name = "ipkutu")
    private String ipkutu;
    @AColumn(name = "sozlesmetarih")
    private Date sozlesmetarih;
    @AColumn(name = "uyeimzaadsoyad")
    private String uyeimzaadsoyad;
    @AColumn(name = "telefon")
    private String telefon;
    @AColumn(name = "send_date")
    private Date sendDate;
    @AColumn(name = "sender_local_user_id")
    private long senderLocalUserId;
    @AColumn(name = "local_user_id")
    private long localUserId;
    @AColumn(name = "order_item_id")
    private long orderItemId;

}
