package io.anjola.customerservicejava.util;

public class ApplicationConstants {
    //REST ENDPOINTS
    public static final String API_PREFIX = "/api/v1";

    public static final String AUTH_ENDPOINTS = API_PREFIX + "/auth/**";
    public static final String AUTH_ENDPOINT = API_PREFIX + "/auth";
    public static final String ADMIN_ENDPOINTS = API_PREFIX + "/admin/**";
    public static final String ADMIN_ENDPOINT = API_PREFIX + "/admin";
    public static final String USER_ENDPOINTS = API_PREFIX + "/user/**";
    public static final String USER_ENDPOINT = API_PREFIX + "/user";
    public static final String USER_AVAILABILITY_ENDPOINT = USER_ENDPOINT + "/checkUsernameAvailability";
    public static final String EMAIL_AVAILABILITY_ENDPOINT = USER_ENDPOINT + "/checkEmailAvailability";



    //PUBLIC ENDPOINTS
    public static final String[] PUBLIC_ENDPOINTS = {"/",
            "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js", "/v3/api-docs/**"};
    /*
    Mail Constants
     */

    public static final String FROM_EMAIL = "ajeed4all@gmail.com";
    public static final String FROM_NAME = "Sanni Lateef Anjola";
    public static final String MAIL_SUBJECT = "No Reply - ";
}
