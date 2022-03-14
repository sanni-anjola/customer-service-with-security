package io.anjola.customerservicejava.util;


public class MailUtil {

    public static String verificationMail(String siteURL, Long id, String name, String code){

        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your No. 1 Customer Service Agent";



        content = content.replace("[[name]]", name);
        String verifyURL = String.format("%s%s/verify?code=%s",siteURL, id, code);

        content = content.replace("[[URL]]", verifyURL);

        return content;

    }

}
