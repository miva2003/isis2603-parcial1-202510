package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.HistoriaClinicaRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HistoriaClinicaService {
    
    @Autowired
    HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    public HistoriaClinicaEntity createHistoriaClinicaEntity(HistoriaClinicaEntity historiaClinica, Long idPaciente) throws EntityNotFoundException {
        log.info("Creando historia clinica");
        Optional<PacienteEntity> paciente = pacienteRepository.findById(idPaciente);
        if (paciente.isEmpty()){
             throw new EntityNotFoundException("El paciente no fue encontrado");
        }
        if (paciente.get().getAcudiente()!=null){
            historiaClinica.setDiagnostico("HistoriaCompartida-"+historiaClinica.getDiagnostico());
        }
        historiaClinica.setPaciente(paciente.get());
        return historiaClinicaRepository.save(historiaClinica);
    }
    
}
