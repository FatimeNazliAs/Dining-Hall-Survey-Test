package dininghall.asstpackages.ExcelPackage.Models;

public class ExcelReadOut
{
    private String outText;
    private int rowNumber;

    public ExcelReadOut()
    {
    }

    public ExcelReadOut(String outText, int rowNumber)
    {
        this.outText = outText;
        this.rowNumber = rowNumber;
    }

    public String getOutText()
    {
        return outText;
    }

    public void setOutText(String outText)
    {
        this.outText = outText;
    }



    public int getRowNumber()
    {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber)
    {
        this.rowNumber = rowNumber;
    }
}
