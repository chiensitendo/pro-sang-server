package com.sang.prosangserver.constants;

public class MailConstants {
	
	public static final String ACCOUNT_REGISTER_EMAIL_TEMPLATE = "<div style=\"width: fit-content;\">\r\n" + 
			"<p>Dear: <b>Mr/Ms. $name</b>.</p>\r\n" + 
			"<p>Your account has been created!</p>\r\n" + 
			"<p>Thank you for your registration. Click this <a href=\"$url\">link</a> to visit the website.</p>\r\n" + 
			"</div>";
	
	public static final String VERIFY_ACCOUNT_EMAIL_TEMPLATE = "<div style=\"width: fit-content;\">\r\n" + 
			"<p>Dear: <b>Mr/Ms. $name</b>.</p>\r\n" + 
			"<p>Please click <b>the bellow link</b> to verify your account. (only available within 7 days)</p>\r\n" + 
			"<a href=\"$url\"><p>$url</p></a>\r\n" + 
			"</div>";
	
	public static final String RESET_ACCOUNT_EMAIL_TEMPLATE = "<div style=\"width: fit-content;\">\r\n" + 
			"<p>Dear: <b>Mr/Ms. $name</b>.</p>\r\n" + 
			"<p>Please click <b>the bellow link</b> to reset your account password. (only available within 1 day)</p>\r\n" + 
			"<a href=\"$url\"><p>$url</p></a>\r\n" + 
			"</div>";
}
