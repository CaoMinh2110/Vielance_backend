package com.example.clothing_stores.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_USERNAME(1001, "Password must be at least {min} character!", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1002, "Password must be at least {min} character!", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1003, "Email incorrect format!", HttpStatus.BAD_REQUEST),

    USER_EXISTED(1011, "Username has already been taken", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1012, "User not existed!", HttpStatus.NOT_FOUND),

    EMAIL_EXISTED(1013, "Email has already been taken", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTED(1014, "Email not existed!", HttpStatus.NOT_FOUND),

    PHONE_EXISTED(1015, "Phone number has already been taken", HttpStatus.BAD_REQUEST),
    PHONE_NOT_EXISTED(1016, "Phone number not existed!", HttpStatus.NOT_FOUND),

    ADDRESS_NOT_EXISTED(1020, "Address not existed!", HttpStatus.NOT_FOUND),

    CATEGORY_EXISTED(1030, "Category existed!", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(1031, "Category not existed!", HttpStatus.NOT_FOUND),

    PRODUCT_EXISTED(1032, "Product existed!", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1033, "Product not existed!", HttpStatus.NOT_FOUND),

    COUPON_EXISTED(1034, "Coupon existed!", HttpStatus.BAD_REQUEST),
    COUPON_NOT_EXISTED(1035, "Coupon not existed!", HttpStatus.NOT_FOUND),

    ORDER_EXISTED(1036, "Order existed!", HttpStatus.BAD_REQUEST),
    ORDER_NOT_EXISTED(1037, "Order not existed!", HttpStatus.NOT_FOUND),

    CART_NOT_EXISTED(1101, "Cart not existed!", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(1200, "Unauthenticated", HttpStatus.FORBIDDEN),
    ROLE_EXISTED(1201, "Role already existed!", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(1202, "Permission already existed!", HttpStatus.NOT_FOUND),

    ORDER_COMPLETED(1203, "Cant change the completed Order", HttpStatus.BAD_REQUEST);
    int code;
    String message;
    HttpStatusCode status;
}
