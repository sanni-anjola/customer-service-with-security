package io.anjola.customerservicejava.model.entity.user;

import lombok.Data;

@Data
public class UserDetails {
    private String billingCountry;
    private String billingAddress1;
    private String billingAddress2;
    private String billingCity;
    private String billingPostcode;
    private String billingPhone;
    private String shippingCountry;
    private String shippingAddress1;
    private String shippingAddress2;
    private String shippingCity;
    private String shippingPostcode;
    private String shippingPhone;

}
