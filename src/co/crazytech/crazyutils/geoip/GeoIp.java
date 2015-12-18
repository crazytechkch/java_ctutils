package co.crazytech.crazyutils.geoip;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import co.crazytech.commons.json.JsonParser;

public class GeoIp {
	private String ip, countryCode,country,regionCode,region,
		city,zipcode,timezone,metroCode,text;
	private Double latitude,longitude;
	
	private static String URL = "http://freegeoip.net/json/";
	
	public GeoIp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GeoIp(String hostname){
		super();
		String json;
		this.text = hostname;
		try {
			json = getJsonString(hostname);
			JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
			this.ip = jsonObject.get("ip").getAsString();
			this.countryCode = jsonObject.get("country_code").getAsString();
			this.country = jsonObject.get("country_name").getAsString();
			this.regionCode = jsonObject.get("region_code").getAsString();
			this.region = jsonObject.get("region_name").getAsString();
			this.city = jsonObject.get("city").getAsString();
			this.zipcode = jsonObject.get("zip_code").getAsString();
			this.timezone = jsonObject.get("time_zone").getAsString();
			this.metroCode = jsonObject.get("metro_code").getAsString();
			this.latitude = jsonObject.get("latitude").getAsDouble();
			this.longitude = jsonObject.get("longitude").getAsDouble();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return text;
	}
	
	public static String getJsonString(String hostname) throws UnknownHostException, MalformedURLException, IOException{
		// url - http://phpmysql-crazytechco.rhcloud.com/aviation/get_airports.php?where=where%20city%20like%20'ka%'
		return new JsonParser().getJsonFromUrl(URL+hostname);
		
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getMetroCode() {
		return metroCode;
	}
	public void setMetroCode(String metroCode) {
		this.metroCode = metroCode;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
