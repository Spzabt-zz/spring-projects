package org.bohdan.rest.sensorapi.controllers;

import jakarta.validation.Valid;
import org.bohdan.rest.sensorapi.dto.MeasurementDTO;
import org.bohdan.rest.sensorapi.dto.MeasurementDTOWithTime;
import org.bohdan.rest.sensorapi.dto.MeasurementListDTO;
import org.bohdan.rest.sensorapi.exceptions.MeasurementNotAddedException;
import org.bohdan.rest.sensorapi.models.Measurement;
import org.bohdan.rest.sensorapi.services.MeasurementService;
import org.bohdan.rest.sensorapi.util.errors.MeasurementErrorResponse;
import org.bohdan.rest.sensorapi.validator.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper,
                                 MeasurementValidator sensorValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = sensorValidator;
    }

    @PostMapping("add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMsg.append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotAddedException(errorMsg.toString());
        }

        measurementService.addMeasurement(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public MeasurementListDTO getMeasurementList() {
        List<MeasurementDTOWithTime> collect = measurementService.getMeasurements().stream().map(this::convertToMeasurementDtoWithTime).
                collect(Collectors.toList());
        return measurementService.convertToMeasurementListDto(collect);
    }

    @GetMapping("rainyDaysCount")
    public Long rainyDaysCount() {
        return measurementService.getRainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleMeasurementAddingException(MeasurementNotAddedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private MeasurementDTOWithTime convertToMeasurementDtoWithTime(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTOWithTime.class);
    }
}
