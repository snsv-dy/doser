package edu.iis.mto.testreactor.doser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.iis.mto.testreactor.doser.infuser.Infuser;
import edu.iis.mto.testreactor.doser.infuser.InfuserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class MedicineDoserTest {

    @Mock
    private Infuser infuser;
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
        doser = new MedicineDoser(infuser, log, clock);
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
        Receipe receipe = new ReceipeBuilder()
                .withDose(
                        Dose.of(Capacity.of(20, CapacityUnit.MILILITER), Period.of(0, TimeUnit.SECONDS))
                )
                .build();

        doser.add(
                (new PackageBuilder()).withCapacity(Capacity.of(19, CapacityUnit.MILILITER))
                        .build()
        );

        assertThrows(InsufficientMedicineException.class, () -> {
            doser.dose(receipe);
        });
    }

    @Test
    void errorRelatedToDispensingDoseShouldBeLogged() throws InfuserException {
        doThrow(new InfuserException()).when(infuser).dispense(any(), any());
        doser.add(PackageBuilder.ANY_PACKAGE);

        doser.dose(ReceipeBuilder.ANY_RECIPE);

        verify(log).logDifuserError(any(), any());
    }

    @Test
    void dispensing1DoseOfMedicineIn100mLPackages() {
        doser.add(PackageBuilder.ANY_PACKAGE);

        DosingResult result = doser.dose(ReceipeBuilder.ANY_RECIPE);

        assertEquals(DosingResult.SUCCESS, result);
    }


    @Test
    void dispensing0DosesOfMedicineIn100mLPackages() {
        doser.add(
                (new PackageBuilder())
                        .withCapacity(Capacity.of(100, CapacityUnit.MILILITER))
                        .build()
        );

        Receipe receipe = (new ReceipeBuilder())
                .withDose(Dose.of(
                        Capacity.of(100, CapacityUnit.MILILITER),
                        Period.of(0, TimeUnit.SECONDS)))
                .withNumber(0)
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            DosingResult result = doser.dose(receipe);
        });

    }
}
