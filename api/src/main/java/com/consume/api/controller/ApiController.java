package com.consume.api.controller;

import com.consume.api.commons.requestDto.OperationDto;
import com.consume.api.commons.responseDto.OpClientResponse;
import com.consume.api.service.OperationService;
import com.consume.api.utils.AES256Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    private final OperationService operationService;

    @PostMapping("/operation")
    public ResponseEntity<OpClientResponse> operation(@Valid @RequestBody OperationDto operationDto){
        return ResponseEntity.ok(operationService.createOperation(operationDto));
    }

    @PostMapping("/aes")
    public ResponseEntity<Map<String, String>> aes( @RequestParam String data) throws NoSuchAlgorithmException {

        SecretKey key = AES256Utils.generateKey();

        String keyBase64 = Base64.getEncoder()
                .encodeToString(key.getEncoded());

        String encryptedData = AES256Utils.encrypt(data,keyBase64);

        String decryptedData = AES256Utils.decrypt(encryptedData, keyBase64);

        Map<String, String> response = new HashMap<>();
        response.put("key", keyBase64);
        response.put("encrypted", encryptedData);
        response.put("decrypted", decryptedData);
        return ResponseEntity.ok(response);
    }


}
