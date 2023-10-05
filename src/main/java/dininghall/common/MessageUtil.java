/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * @author Tolga
 */
@ManagedBean(name = "messageUtil")
@SessionScoped
public class MessageUtil implements Serializable {

    public MessageUtil() {

    }

    public static String getReplayMessage(String id) {
        String resultMessage = "";

        switch (id) {
            case "basarili":
                resultMessage = "Tebrikler Kullanıcı şifreniz başarıyla değiştirildi, yeni şifreniz ile giriş yapabilirsiniz.";
                break;

            case "baglanti-bilgisi-iletildi":
                resultMessage = "Şifre yenilemek için posta kutunuza gönderilen maili kontrol edebilirsiniz.";
                break;

            case "baglanti-gecerli-degil":
                resultMessage = "* Girdiğiniz doğrulama kodu geçerli değildir, üzgünüz :(";
                break;

            case "bulunamadi":
                resultMessage = "* Belirtilen bağlantı bilgisi bulunamamıştır.";
                break;

            case "suresi-dolmus":
                resultMessage = "* Belirtilen bağlantının süresi dolmuştur.";
                break;

            case "pasif":
                resultMessage = "* Belirtilen bağlantı aktif değildir.";
                break;

            case "kullanici-bulunamadi":
                resultMessage = "* Doğrulama yapmak istediğiniz kullanıcı bulunamamıştır.";
                break;

            case "siparis-basarili":
                resultMessage = "Tebrikler Siparişiniz başarıyla oluşturulmuştur, sipariş sürecini üye sayfanızdan kontrol edebilirsiniz.";
                break;

            case "order-error":
                resultMessage = "* Maalesef talebinizi gerçekleştiremiyoruz, lütfen tekrar işlem yapmayı deneyiniz.";
                break;

            case "sistem-hatasi":
                resultMessage = "* Sistemde hata meydana geldi, lütfen tekrar işlem yapmayı deneyiniz.";
                break;

        }

        return resultMessage;
    }


}
