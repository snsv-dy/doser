package edu.iis.mto.testreactor.doser;

import java.util.concurrent.TimeUnit;

public class ReceipeBuilder {
    public Medicine medicine = Medicine.of("alantan");
    private int number = 1;
    private Dose dose = Dose.of(
            Capacity.of(100, CapacityUnit.MILILITER),
            Period.of(0, TimeUnit.SECONDS)
            );

    public Receipe build(){
        return Receipe.of(medicine, dose, number);
    }

    public ReceipeBuilder withNumber(int number) {
        this.number = number;
        return this;
    }

    public ReceipeBuilder withDose(Dose dose) {
        this.dose = dose;
        return this;
    }

    public static final Receipe ANY_RECIPE = new ReceipeBuilder().build();
}
