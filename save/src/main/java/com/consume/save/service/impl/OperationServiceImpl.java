package com.consume.save.service.impl;

import com.consume.save.commons.entity.OperationEntity;
import com.consume.save.commons.entity.Users;
import com.consume.save.commons.exception.UnauthorizedException;
import com.consume.save.commons.requestDto.OperationDto;
import com.consume.save.commons.requestDto.UserDto;
import com.consume.save.commons.responseDto.OpClientResponse;
import com.consume.save.commons.responseDto.ResponseDto;
import com.consume.save.repository.SaveRepository;
import com.consume.save.repository.UsersRepository;
import com.consume.save.service.OperationService;
import com.consume.save.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.consume.save.utils.Utils.ESTATUS_C;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final SaveRepository saveRepository;
    private final UsersRepository usersRepository;

    @Override
    public OpClientResponse createOperation(OperationDto operationDto) {
        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setOperacion(operationDto.getOperacion());
        operationEntity.setSecreto(operationDto.getSecreto());
        operationEntity.setImporte(operationDto.getImporte());
        operationEntity.setCliente(operationDto.getCliente());
        operationEntity.setEstatus(Utils.ESTATUS);
        operationEntity.setReferencia(Utils.generarReferencia());

        OperationEntity operationNew =
                saveRepository.save(operationEntity);

        return getOpClientResponse(operationNew);
    }

    @Override
    public OpClientResponse updateOperation(OpClientResponse operationDto) {

        Optional<OperationEntity> operationEntity = saveRepository.findById(operationDto.getId());
        OperationEntity operationNew = operationEntity.orElseThrow(() -> new RuntimeException("Operation not found"));
        saveRepository.updateEstatusById(operationNew.getId(), ESTATUS_C);
        operationNew.setEstatus(ESTATUS_C);
        return getOpClientResponse(operationNew);
    }

    @Override
    public Page<OpClientResponse> obtenerOperaciones(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Page<OperationEntity> operaciones =
                saveRepository.findAll(pageable);

        return operaciones.map(this::getOpClientResponse);
    }

    @Override
    public ResponseDto user(UserDto userDto) {

        String passwordHash = Utils.hash(userDto.getPassword());
        Users users = usersRepository.findByUsername(userDto.getUser());

        if (users != null && Utils.matches(userDto.getPassword(), users.getPasword())) {
            return ResponseDto.builder()
                    .message("Usuario y contraseña correctos")
                    .build();
        }else {
            throw new UnauthorizedException("Usuario o contraseña incorrectos");
        }
    }

    private OpClientResponse getOpClientResponse(OperationEntity operationNew) {
        return OpClientResponse.builder()
                .id(operationNew.getId())
                .estatus(operationNew.getEstatus())
                .referencia(operationNew.getReferencia())
                .operacion(operationNew.getOperacion())
                .build();
    }
}
