package edu.iis.mto.testreactor.doser;

public class PackageBuilder {
    Medicine medicine = Medicine.of("alantan");
    Capacity capacity = Capacity.of(100, CapacityUnit.MILILITER);

    public PackageBuilder withMedicine(Medicine medicine) {
        this.medicine = medicine;
        return this;
    }

    public PackageBuilder withCapacity(Capacity capacity) {
        this.capacity = capacity;
        return this;
    }

    public MedicinePackage build() {
        return MedicinePackage.of(medicine, capacity);
    }

    public static final MedicinePackage ANY_PACKAGE = (new PackageBuilder()).build();
}
