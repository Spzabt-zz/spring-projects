package org.bohdan.sensorClient;

import org.bohdan.sensorClient.dto.Measurement;
import org.bohdan.sensorClient.response.AllMeasurementsResponse;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "localhost:8080/v1";

        final String urlSensorRegister = "http://" + baseUrl + "/sensor/register";
        final String urlAddMeasurement = "http://" + baseUrl + "/measurements/add";
        final String urlGetAllMeasurements = "http://" + baseUrl + "/measurements";
        final String urlGetRainyDaysCount = "http://" + baseUrl + "/measurements/rainyDaysCount";

        /*HttpEntity<Sensor> requestSensor = new HttpEntity<>(new Sensor("Sensor Xiaomi"));
        ResponseEntity<HttpStatus> registerSensorResponse = restTemplate.exchange(
                urlSensorRegister,
                HttpMethod.POST,
                requestSensor,
                HttpStatus.class
        );
        System.out.println(registerSensorResponse);

        for (int i = 0; i < 1000; i++) {
            HttpEntity<Measurement> requestMeasurements = new HttpEntity<>(new Measurement(
                    -100 + (float)(Math.random() * ((100 - (-100)))),
                    ThreadLocalRandom.current().nextBoolean(),
                    new Sensor("Sensor Xiaomi")));
            ResponseEntity<HttpStatus> registerMeasurementsResponse = restTemplate.exchange(
                    urlAddMeasurement,
                    HttpMethod.POST,
                    requestMeasurements,
                    HttpStatus.class
            );
            System.out.println(registerMeasurementsResponse);
        }*/

        AllMeasurementsResponse sensorApiResponse = restTemplate.getForObject(urlGetAllMeasurements, AllMeasurementsResponse.class);
        List<Measurement> measurements = sensorApiResponse.getMeasurements();

        Map<Long, List<Measurement>> measurementMap = measurements
                .stream()
                .collect(Collectors.groupingBy(
                        measurement -> measurement.getMeasurementTime().getTime()
                ));

        double[] xData = new double[measurementMap.size()];
        double[] yData = new double[measurementMap.size()];

        Map<Long, Float> resultMeasurementMap = new HashMap<>();
        for (Long time : measurementMap.keySet()) {
            List<Measurement> measurementsAtParticularTime = measurementMap.get(time);

            float res = 0f;
            for (Measurement measurement : measurementsAtParticularTime) {
                res += measurement.getValue();
            }

            res = res / measurementsAtParticularTime.size();
            resultMeasurementMap.put(time, res);
        }

        TreeMap<Long, Float> sorted
                = new TreeMap<>(resultMeasurementMap);

        for (int i = 0; i < sorted.keySet().size(); i++) {
            long key = (long) sorted.keySet().toArray()[i];
            xData[i] = key;
            yData[i] = sorted.get(key);
        }

        Long rainyDaysCount = restTemplate.getForObject(urlGetRainyDaysCount, Long.class);

        System.out.println(rainyDaysCount);

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        chart.getSeriesMap().get("y(x)").setMarker(SeriesMarkers.CIRCLE);

        // Show it
        new SwingWrapper<>(chart).displayChart();
    }
}
