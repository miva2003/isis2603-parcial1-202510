package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PacienteService.class)
public class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Autowired 
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PacienteEntity> pacientes = new ArrayList();

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PacienteEntity").executeUpdate();
    }

    private void insertData() {
        for (int i= 0; i<3; i++){
            PacienteEntity pacienteEntity = factory.manufacturePojo(PacienteEntity.class);
            entityManager.persist(pacienteEntity);
            pacientes.add(pacienteEntity);
        }
    }

    @Test
    void testCreatePaciente() throws IllegalOperationException{
        PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
        newEntity.setTelefono("31135223452");
        PacienteEntity result = pacienteService.createPaciente(newEntity);
        assertNotNull(result);
        
        PacienteEntity entity = entityManager.find(PacienteEntity.class, result.getId());

        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getTelefono(), entity.getTelefono());
        assertEquals(newEntity.getCorreo(), entity.getCorreo());

    }

    @Test
    void testCreatePacienteInvalido() {
       assertThrows(IllegalOperationException.class, () -> {PacienteEntity newEntity = factory.manufacturePojo(PacienteEntity.class);
       newEntity.setTelefono("311352234");
       PacienteEntity result = pacienteService.createPaciente(newEntity);
    }); 

    }


}
