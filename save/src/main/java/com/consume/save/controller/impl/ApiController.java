package com.consume.save.controller.impl;


import com.consume.save.commons.requestDto.OperationDto;
import com.consume.save.commons.requestDto.UserDto;
import com.consume.save.commons.responseDto.OpClientResponse;
import com.consume.save.commons.responseDto.ResponseDto;
import com.consume.save.controller.ApiControllerInterface;
import com.consume.save.service.OperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController implements ApiControllerInterface {

    private final OperationService operationService;

    @PostMapping("/operation")
    public ResponseEntity<OpClientResponse> operation(@Valid @RequestBody OperationDto operationDto){
        return ResponseEntity.ok(operationService.createOperation(operationDto));
    }

    @PatchMapping("/operation")
    public ResponseEntity<OpClientResponse> updateOperation(@Valid @RequestBody OpClientResponse operationDto){
        return ResponseEntity.ok(operationService.updateOperation(operationDto));
    }

    @GetMapping
    public ResponseEntity<Page<OpClientResponse>> obtenerOperaciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        return ResponseEntity.ok(
                operationService.obtenerOperaciones(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> user(@RequestBody UserDto userDto)  {

        return ResponseEntity.ok(
                operationService.user(userDto)
        );
    }

}
