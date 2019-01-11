package example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class SmartystreetsController {
	
	@Value("${smartystreets.authid}")
	protected String smartystreetsAuthid;

	@Value("${smartystreets.authtoken}")
	protected String smartystreetsAuthtoken;
	
	@Value("${smartystreets.scheme}")
	protected String smartystreetsScheme;
	
	@Value("${smartystreets.port}")
	protected String smartystreetsPort;
	
	@Value("${smartystreets.autocomplete.host}")
	protected String smartystreetsAutocompleteHost;
	
	@Value("${smartystreets.autocomplete.path}")
	protected String smartystreetsAutocompletePath;
	
	@Value("${smartystreets.streetaddress.host}")
	protected String smartystreetsStreetaddressHost;
	
	@Value("${smartystreets.streetaddress.path}")
	protected String smartystreetsStreetaddressPath;
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/smartystreets/address", produces = "application/json; charset=UTF-8")
	public ResponseEntity getAddress(@RequestParam(value = "prefix") String prefix) {

		String url = UriComponentsBuilder.newInstance()
			.scheme(smartystreetsScheme)
			.host(smartystreetsAutocompleteHost)
			.port(smartystreetsPort)
			.path(smartystreetsAutocompletePath)
			.queryParam("auth-id", smartystreetsAuthid)
			.queryParam("auth-token", smartystreetsAuthtoken)
			.queryParam("prefix", prefix)
			.build()
			.toUriString();

		try {
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(url, String.class);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/smartystreets/zipcode", produces = "application/json; charset=UTF-8")
	public ResponseEntity getZipcode(@RequestParam(value = "street") String street,
			@RequestParam(value = "city") String city, @RequestParam(value = "state") String state) {

		String url = UriComponentsBuilder.newInstance()
				.scheme(smartystreetsScheme)
				.host(smartystreetsStreetaddressHost)
				.port(smartystreetsPort)
				.path(smartystreetsStreetaddressPath)
				.queryParam("auth-id", smartystreetsAuthid)
				.queryParam("auth-token", smartystreetsAuthtoken)
				.queryParam("street", street)
				.queryParam("city", city)
				.queryParam("state", state)
				.build()
				.toUriString();

		try {
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(url, String.class);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}