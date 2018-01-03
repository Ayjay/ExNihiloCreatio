package exnihilocreatio.items;

public enum EnumPebbleSubtype {
    STONE(0),
    GRANITE(1),
    DIORITE(2),
    ANDESITE(3);

    final int meta;

    EnumPebbleSubtype(int meta) {
        this.meta = meta;
    }

    public int getMeta() {
        return meta;
    }
}