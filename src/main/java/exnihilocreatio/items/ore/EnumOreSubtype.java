package exnihilocreatio.items.ore;

public enum EnumOreSubtype {
    PIECE(0),
    CHUNK(1),
    DUST(2),
    INGOT(3);

    final int meta;

    EnumOreSubtype(int meta) {
        this.meta = meta;
    }

    public int getMeta() {
        return meta;
    }
}