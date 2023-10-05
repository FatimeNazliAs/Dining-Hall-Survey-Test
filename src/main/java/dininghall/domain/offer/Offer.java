package dininghall.domain.offer;

import java.util.Date;

public class Offer {
    private int offerId;
    private int offerTypeId;
    private int offerMethodId;
    private Date startAt;
    private Date endAt;
    private int discountAmount;
    private int minAmountInCart;
    private int maxTotalUsage;
    private int allowedMaxDiscount;
    private String code;
    private String codeDesc;
    private int maxUse;
    private int maxUseUser;
    private int enabled;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getOfferTypeId() {
        return offerTypeId;
    }

    public void setOfferTypeId(int offerTypeId) {
        this.offerTypeId = offerTypeId;
    }

    public int getOfferMethodId() {
        return offerMethodId;
    }

    public void setOfferMethodId(int offerMethodId) {
        this.offerMethodId = offerMethodId;
    }


    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getMinAmountInCart() {
        return minAmountInCart;
    }

    public void setMinAmountInCart(int minAmountInCart) {
        this.minAmountInCart = minAmountInCart;
    }

    public int getMaxTotalUsage() {
        return maxTotalUsage;
    }

    public void setMaxTotalUsage(int maxTotalUsage) {
        this.maxTotalUsage = maxTotalUsage;
    }

    public int getAllowedMaxDiscount() {
        return allowedMaxDiscount;
    }

    public void setAllowedMaxDiscount(int allowedMaxDiscount) {
        this.allowedMaxDiscount = allowedMaxDiscount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public int getMaxUse() {
        return maxUse;
    }

    public void setMaxUse(int maxUse) {
        this.maxUse = maxUse;
    }

    public int getMaxUseUser() {
        return maxUseUser;
    }

    public void setMaxUseUser(int maxUseUser) {
        this.maxUseUser = maxUseUser;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }
}
