package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HistoriaClinicaEntity.class)
public class HistoriaClinicaServiceTest {

    @Autowired 
    private HistoriaClinicaService historiaClinicaService;

    @Autowired 
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<HistoriaClinicaEntity> historias = new ArrayList();

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from HistoriaClinicaEntity").executeUpdate();
    }

    private void insertData() {
        for (int i= 0; i<3; i++){
            HistoriaClinicaEntity historiaEntity = factory.manufacturePojo(HistoriaClinicaEntity.class);
            entityManager.persist(historiaEntity);
            historias.add(historiaEntity);
        }
    }

    



}
