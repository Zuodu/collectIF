/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.OkHttpRequestHandler;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pchiu
 */
public class ServiceTechnique {

    final static String MA_CLÉ_GOOGLE_API = "AIzaSyDcVVJjfmxsNdbdUYeg9MjQoJJ6THPuap4";

    final static GeoApiContext MON_CONTEXTE_GEOAPI = new GeoApiContext().setApiKey(MA_CLÉ_GOOGLE_API);

    public static LatLng getLatLng(String adresse) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(MON_CONTEXTE_GEOAPI, adresse).await();

            return results[0].geometry.location;

        } catch (Exception ex) {
            return null;
        }
    }

    public static double toRad(double angleInDegree) {
        return angleInDegree * Math.PI / 180.0;
    }

    public static double getDistanceEnKm(LatLng origin, LatLng destination) {

        double R = 6371.0; // Average radius of Earth (km)
        double dLat = toRad(destination.lat - origin.lat);
        double dLon = toRad(destination.lng - origin.lng);
        double lat1 = toRad(origin.lat);
        double lat2 = toRad(destination.lat);

        double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0)
                + Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
        double d = R * c;

        return Math.round( d * 1000.0 ) / 1000.0;
    }
    
}
