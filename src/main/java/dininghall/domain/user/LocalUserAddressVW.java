/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Tolga
 */
@Data
public class LocalUserAddressVW implements Serializable {

    private long localUserId;
    private int addressId;
    private String line1;
    private String line2;
    private String line3;
    private int continentId;
    private int countryId;
    private int subDivisionId;
    private int cityId;
    private int stateId;
    private int streetId;
    private String cityName;
    private String stateName;
    private String streetName;
    private String postCode;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String header;
    private String countryName;
    private String city;

}