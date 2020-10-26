package domain.facility;

public enum ToiletType {
    OPEN, SIMPLE, PUBLIC, MOVING, ECT;

    public static ToiletType getInstance(String toilet) {
        switch (toilet) {
            case "공중화장실":
                return ToiletType.PUBLIC;
            case "이동화장실":
                return ToiletType.MOVING;
            case "간이화장실":
                return ToiletType.SIMPLE;
            case "개방화장실":
                return ToiletType.OPEN;
            default:
                return ToiletType.ECT;
        }
    }
}
