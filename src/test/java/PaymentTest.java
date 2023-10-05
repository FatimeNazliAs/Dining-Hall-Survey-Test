
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import java.net.http.HttpResponse;



public class PaymentTest
{
    // one instance, reuse
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Test
    public void paymentTest()
    {
        // ####################### DÜZENLEMESİ ZORUNLU ALANLAR #######################
        //
        // API Entegrasyon Bilgileri - Mağaza paneline giriş yaparak BİLGİ sayfasından alabilirsiniz.
        String merchant_id = "256093";
        String merchant_key = "6X8Q6dhNzGSTMqxW";
        String merchant_salt = "chMu7yG9acLEgSSA";

        //
        // Müşterinizin sitenizde kayıtlı veya form vasıtasıyla aldığınız eposta adresi
        String emailstr = "info@keas.com.tr";

        //
        // Tahsil edilecek tutar. 9.99 için 9.99 * 100 = 999 gönderilmelidir.
        int payment_amountstr = 999;

        //
        // Sipariş numarası: Her işlemde benzersiz olmalıdır!! Bu bilgi bildirim sayfanıza yapılacak bildirimde geri gönderilir.
        String merchant_oid = "1";

        //
        // Müşterinizin sitenizde kayıtlı veya form aracılığıyla aldığınız ad ve soyad bilgisi
        String user_namestr = "Test";

        //
        // Müşterinizin sitenizde kayıtlı veya form aracılığıyla aldığınız adres bilgisi
        String user_addressstr = "Test";

        //
        // Müşterinizin sitenizde kayıtlı veya form aracılığıyla aldığınız telefon bilgisi
        String user_phonestr = "Test";

        //
        // Başarılı ödeme sonrası müşterinizin yönlendirileceği sayfa
        // !!! Bu sayfa siparişi onaylayacağınız sayfa değildir! Yalnızca müşterinizi bilgilendireceğiniz sayfadır!
        // !!! Siparişi onaylayacağız sayfa "Bildirim URL" sayfasıdır (Bakınız: 2.ADIM Klasörü).
        String merchant_ok_url = "https://www.keas.com.tr/init";

        //
        // Ödeme sürecinde beklenmedik bir hata oluşması durumunda müşterinizin yönlendirileceği sayfa
        // !!! Bu sayfa siparişi iptal edeceğiniz sayfa değildir! Yalnızca müşterinizi bilgilendireceğiniz sayfadır!
        // !!! Siparişi iptal edeceğiniz sayfa "Bildirim URL" sayfasıdır (Bakınız: 2.ADIM Klasörü).
        String merchant_fail_url = "https://www.keas.com.tr";


        //
        // !!! Eğer bu örnek kodu sunucuda değil local makinanızda çalıştırıyorsanız
        // buraya dış ip adresinizi (https://www.whatismyip.com/) yazmalısınız. Aksi halde geçersiz paytr_token hatası alırsınız.
        /**
         HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         String user_ip = request.getHeader("X-FORWARDED-FOR");
         if (user_ip == null || user_ip == "")
         {
         user_ip = request.getRemoteAddr();
         }
         */
        String user_ip = "212.175.155.51";

        //
        // ÖRNEK $user_basket oluşturma - Ürün adedine göre object'leri çoğaltabilirsiniz
        Object[][] user_basket = {
                new Object[]{"Örnek ürün 1", "18.00", 1}, // 1. ürün (Ürün Ad - Birim Fiyat - Adet)
                new Object[]{"Örnek ürün 2", "33.25", 2}, // 2. ürün (Ürün Ad - Birim Fiyat - Adet)
                new Object[]{"Örnek ürün 3", "45.42", 1}, // 3. ürün (Ürün Ad - Birim Fiyat - Adet)
        };
        /* ############################################################################################ */



        // İşlem zaman aşımı süresi - dakika cinsinden
        String timeout_limit = "30";

        //
        // Hata mesajlarının ekrana basılması için entegrasyon ve test sürecinde 1 olarak bırakın. Daha sonra 0 yapabilirsiniz.
        String debug_on = "1";

        //
        // Mağaza canlı modda iken test işlem yapmak için 1 olarak gönderilebilir.
        String test_mode = "1";

        //
        // Taksit yapılmasını istemiyorsanız, sadece tek çekim sunacaksanız 1 yapın
        String no_installment = "1";

        //
        // Sayfada görüntülenecek taksit adedini sınırlamak istiyorsanız uygun şekilde değiştirin.
        // Sıfır (0) gönderilmesi durumunda yürürlükteki en fazla izin verilen taksit geçerli olur.
        String max_installment = "0";

        //
        // Para birimi olarak TL, EUR, USD gönderilebilir. USD ve EUR kullanmak için kurumsal@paytr.com
        // üzerinden bilgi almanız gerekmektedir. Boş gönderilirse TL geçerli olur.
        String currency = "TL";

        //
        // Türkçe için tr veya İngilizce için en gönderilebilir. Boş gönderilirse tr geçerli olur.
        String lang = "";


        // Gönderilecek veriler oluşturuluyor
        Map<String, String> data = new HashMap<>();
        data.put("merchant_id", merchant_id);
        data.put("user_ip", user_ip);
        data.put("merchant_oid", merchant_oid);
        data.put("email", emailstr);
        data.put("payment_amount", String.valueOf(payment_amountstr));

        //
        // Sepet içerği oluşturma fonksiyonu, değiştirilmeden kullanılabilir.
        Gson gson = new Gson();
        String user_basket_json = gson.toJson(user_basket);
        String user_basketstr = "";
        try
        {
            user_basketstr = Base64.getEncoder().encodeToString(user_basket_json.getBytes("utf-8"));

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        data.put("user_basket", user_basketstr);

        //
        // Token oluşturma fonksiyonu, değiştirilmeden kullanılmalıdır.
        String Birlestir = merchant_id + user_ip + merchant_oid + emailstr + payment_amountstr + user_basketstr + no_installment + max_installment + currency +
                test_mode + merchant_salt;

        String hexValue = "";

        try
        {
            hexValue = HmacSHA256.generateHMAC(merchant_key, Birlestir);

        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }

        data.put("paytr_token", hexValue);
        //

        //
        data.put("debug_on", debug_on);
        data.put("test_mode", test_mode);
        data.put("no_installment", no_installment);
        data.put("max_installment", max_installment);
        data.put("user_name", user_namestr);
        data.put("user_address", user_addressstr);
        data.put("user_phone", user_phonestr);
        data.put("merchant_ok_url", merchant_ok_url);
        data.put("merchant_fail_url", merchant_fail_url);
        data.put("timeout_limit", timeout_limit);
        data.put("currency", currency);
        data.put("lang", lang);

        try
        {
            sendPost(data);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    private void sendPost(Map<String, String> data) throws Exception
    {
        HttpRequest.BodyPublisher publisher = buildFormDataFromMap(data);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(publisher)
                .uri(URI.create("https://www.paytr.com/odeme/api/get-token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build()
                ;

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        String[] body = response.body().split(":");

        String token = body[2];

        token = token.replace("\"", "").replace("}", "");

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<String, String> data)
    {
        var builder = new StringBuilder();

        for (Map.Entry<String, String> entry : data.entrySet())
        {
            if (builder.length() > 0)
            {
                builder.append("&");
            }

            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));

            builder.append("=");

            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }

        System.out.println(builder.toString());

        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private void sendGet() throws Exception
    {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://httpbin.org/get"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

}


class HmacSHA256
{
    public static final String HMAC_SHA256 = "HmacSHA256";

    public static String generateHMAC(final String key, final String data) throws NoSuchAlgorithmException, InvalidKeyException
    {
        if (key == null || data == null) throw new NullPointerException();

        final Mac hMacSHA256 = Mac.getInstance(HMAC_SHA256);

        byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);

        final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, HMAC_SHA256);

        hMacSHA256.init(secretKey);

        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

        byte[] res = hMacSHA256.doFinal(dataBytes);

        return Base64.getEncoder().encodeToString(res);
    }
}



