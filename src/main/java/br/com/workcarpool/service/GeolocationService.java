package br.com.workcarpool.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import br.com.workcarpool.model.HomeAddress;

@Service
public class GeolocationService {

	public List<Double> getLatLongByAdress(HomeAddress address) throws ApiException, InterruptedException, IOException {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyA03zKo_Ey2LXS6y9SS12t9Fq4ZIA29lOk");
		GeocodingApiRequest request = GeocodingApi.newRequest(context).address(address.getAddress());
		GeocodingResult[] resultSet = request.await();
		LatLng location = resultSet[0].geometry.location;
		
		return Arrays.asList(location.lat, location.lng);
	}
}
