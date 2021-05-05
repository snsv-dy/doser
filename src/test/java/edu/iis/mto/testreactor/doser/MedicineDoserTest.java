package edu.iis.mto.testreactor.doser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.iis.mto.testreactor.doser.infuser.Infuser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class MedicineDoserTest {

    @Mock
    private Infuser infuzer;
    @Mock
    private Clock clock;
    @Mock
    private DosageLog log;

    private MedicineDoser doser;

    @Test
    void itCompiles() {
        assertEquals(2, 1 + 1);
    }



    @BeforeEach
    void setUp() {
        doser = new MedicineDoser(infuzer, log, clock);
    }

    @Test
    void dosingWithoutAnyPackagesShouldResultInUnavailableMedicineException() {
        Receipe recipe = ReceipeBuilder.ANY_RECIPE;
        assertThrows(UnavailableMedicineException.class, () -> {
            doser.dose(recipe);
        });
    }

    @Test
    void dosingWithoutCorrecPackageShouldReturnDosingResultError() {

    }

    @Test
    void errorRelatedToDispensingDoseShouldBeLogged() {

    }

    @Test
    void dispensing3DosesOfMedicineIn20mLPackages() {

    }


}
