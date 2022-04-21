import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceTest {

    @Test
    void testCheckBloodPressure() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        String id1 = patientInfoRepository.add(
                new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));
        BloodPressure currentPressure = new BloodPressure(120, 80);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(id1, currentPressure);

    }



//    @Test
//    void testCheckBloodPressure() {
//        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
//
//        String id1 = String.valueOf(patientInfoRepository.getById(Mockito.any()));
//        BloodPressure currentPressure = new BloodPressure(120, 80);
//
//        MedicalService medicalService = Mockito.mock(MedicalService.class);
//        medicalService.checkBloodPressure(id1, currentPressure);
//
//
//        Mockito.verify(medicalService, Mockito.times(1)).checkBloodPressure(id1, currentPressure);
//    }

    //    @Test
//    void testCheckTemperature() {
//        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
//
//        BigDecimal currentTemperature = new BigDecimal("37.9");
//
//        String id1 = patientInfoRepository.add(
//                new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
//                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)))
//        );
//
//        MedicalService medicalService = Mockito.mock(MedicalService.class);
//        medicalService.checkTemperature(id1, currentTemperature);
//
//        Mockito.verify(medicalService, Mockito.times(0)).checkTemperature(id1, currentTemperature);
//    }

    @Test
    void testSend() {
        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        sendAlertService.send("message");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());

        Mockito.verify(sendAlertService, Mockito.times(1)).send("message");
        Assertions.assertEquals("message", argumentCaptor.getValue());
    }
}
