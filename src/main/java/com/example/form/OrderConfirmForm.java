package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 注文情報を取得する.
 * 
 * @author ashibe
 *
 */
public class OrderConfirmForm {

	/**
	 * 配達先氏名
	 **/
	@NotBlank(message = "input destinationName")
	private String destinationName;

	/**
	 * 配達先Eメール
	 **/
	@Email(message = "this destinationEmail format is wrong")
	@NotBlank(message = "input destinationEmail")
	private String destinationEmail;

	/**
	 * 配達先郵便番号
	 **/
	@NotBlank(message = "input destinationZipcode")
	@Pattern(regexp = "^[0-9]{7}$", message = "destinationZipCode format is wrong")
	private String destinationZipcode;

	/**
	 * 配達先住所
	 **/
	@NotBlank(message = "input destinationAddress")
	private String destinationAddress;

	/**
	 * 配達先電話TEL
	 **/
	@Pattern(regexp = "^[0-9]{9,11}$", message = "telephone formmat is wrong")
	@NotBlank(message = "input destinationTel")
	private String destinationTel;

	/**
	 * 配達希望日
	 **/
	@NotEmpty(message = "Select delivery date")
	private String deliveryDate;

	/**
	 * 配送時間
	 **/
	@NotEmpty(message = "Select delivery time")
	private String deliveryTime;

	/**
	 * 支払方法
	 **/
	@NotBlank(message = "Select paymentMethod")
	private String paymentMethod;

	/**
	 * カード番号
	 **/
	//@Pattern(regexp="(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6011[0-9]{12}|3(?:0[0-5]|[68][0-9])[0-9]{11}|3[47]{13}|(?:2131|1800|35[0-9]{3})[0-9]{11})",message="Invalid card number")
	private String card_number;

	/**
	 * カード有効期限(年)
	 */
	private String card_exp_year;

	/**
	 * カード有効期限(月)
	 **/
	private String card_exp_month;

	/**
	 * カード名義人
	 **/
	private String card_name;

	@Override
	public String toString() {
		return "OrderConfirmForm [destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", deliveryDate=" + deliveryDate + ", deliveryTime="
				+ deliveryTime + ", paymentMethod=" + paymentMethod + ", card_number=" + card_number
				+ ", card_exp_year=" + card_exp_year + ", card_exp_month=" + card_exp_month + ", card_name=" + card_name
				+ ", card_cvv=" + card_cvv + "]";
	}

	/**
	 * セキュリティコード
	 **/
	private String card_cvv;

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getCard_exp_year() {
		return card_exp_year;
	}

	public void setCard_exp_year(String card_exp_year) {
		this.card_exp_year = card_exp_year;
	}

	public String getCard_exp_month() {
		return card_exp_month;
	}

	public void setCard_exp_month(String card_exp_month) {
		this.card_exp_month = card_exp_month;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getCard_cvv() {
		return card_cvv;
	}

	public void setCard_cvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
