package my.work.countrybyphone.business.service;

public record CountryCallingCodeDescriptorDto(String callingCode,
											  String countryName) {
	public static Builder newBuilder() {
		return new Builder();
	}

	public static final class Builder {
		private String callingCode;
		private String countryName;

		public Builder withCallingCode(String callingCode) {
			this.callingCode = callingCode;
			return this;
		}

		public Builder withCountryName(String countryName) {
			this.countryName = countryName;
			return this;
		}

		public CountryCallingCodeDescriptorDto build() {
			return new CountryCallingCodeDescriptorDto(callingCode, countryName);
		}
	}
}
