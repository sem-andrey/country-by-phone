package my.work.countrybyphone.business.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "country_phone", name = "country_calling_code_descriptor")
public class CountryCallingCodeDescriptor {
	@Id
	private String code;

	private String country;

	public static CountryCallingCodeDescriptor makeByCodeAndCountry(String code,
																	String country) {
		CountryCallingCodeDescriptor countryCallingCodeDescriptor = new CountryCallingCodeDescriptor();
		countryCallingCodeDescriptor.code = code;
		countryCallingCodeDescriptor.country = country;
		return countryCallingCodeDescriptor;
	}

	public String getCode() {
		return code;
	}

	public String getCountry() {
		return country;
	}
}
