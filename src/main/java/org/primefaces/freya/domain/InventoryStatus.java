package org.primefaces.freya.domain;

public enum InventoryStatus {
    INSTOCK("Stokta Var"),
    OUTOFSTOCK("Stokta Yok"),
    LOWSTOCK("Düşük Stok");
 
    private String text;
 
    InventoryStatus(String text) {
        this.text = text;
    }
 
    public String getText() {
        return text;
    }
}