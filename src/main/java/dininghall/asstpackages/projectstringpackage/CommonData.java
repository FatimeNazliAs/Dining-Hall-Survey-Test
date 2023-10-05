package dininghall.asstpackages.projectstringpackage;

import org.apache.commons.collections4.map.HashedMap;

import javax.faces.bean.ManagedBean;
import java.util.Map;

@ManagedBean(name = "rs")
public class CommonData {
    static Map<String, String> allData;

    public String getTemplateData(String data) {
        if (allData == null) {
            allData = new HashedMap<>();
            allData.put("infomailadress", "info@keas.com.tr");
            allData.put("projectdescription", "description");
            allData.put("projectname", "KeasAnket");
            allData.put("indexKeywords", "Keas,Keywords");
            allData.put("websiteadress", "localhost");
            allData.put("login", "Giriş Yap");
        }
        return allData.get("infomailadress");
    }

}