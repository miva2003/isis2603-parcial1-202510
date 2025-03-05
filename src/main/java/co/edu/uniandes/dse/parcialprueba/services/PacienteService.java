package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;

@Slf4j
@Service
public class PacienteService {
    
    @Autowired
    PacienteRepository pacienteRepository;

    @Transactional
    public PacienteEntity createPaciente(PacienteEntity paciente) throws IllegalOperationException{
        log.info("Inicia el proceso de creación del autor");
        if (paciente.getTelefono().length()!=11){
            throw new IllegalOperationException("El telefono debe contener 11 dígitos.");
        }
        if (paciente.getTelefono().substring(0, 4)!="311" || paciente.getTelefono().substring(0, 4)!="601"){
            throw new IllegalOperationException("El telefono no es válido");
        }
        return paciente;
    }

    @Transactional
    public void agrgarAcudiente(Long idAcudiente, Long idPaciente) throws EntityNotFoundException, IllegalOperationException{
        Optional<PacienteEntity> acudiente = pacienteRepository.findById(idAcudiente);
        Optional<PacienteEntity> paciente = pacienteRepository.findById(idPaciente);
    
        if (acudiente.isEmpty()){
            throw new EntityNotFoundException("El acuendiente no fue encontrado");
        }
        if (paciente.isEmpty()){
            throw new EntityNotFoundException("El paciente no fue encontrado");
        }
        if (!acudiente.get().getHistoriasClinicas().isEmpty()){
            throw new IllegalOperationException("El acudiente no puede ser paciente");
        }
        if (paciente.get().getAcudiente()!=null){
            throw new IllegalOperationException("El paciente ya tiene un acudiente");
        }
        paciente.get().setAcudiente(acudiente.get());

    }
}

