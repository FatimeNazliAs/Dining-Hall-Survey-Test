package TestModels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
    @AllArgsConstructor
public
class PdfModel{
    int x;
    int y;
    String value;
    String name;
    int pageNum;
}