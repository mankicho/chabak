package domain.facility;

public enum FishingType {
    RESERVOIR, FLAT, VALLEY, SEA, ECT;

    public static FishingType getFishingType(String type) {
        switch (type) {
            case "저수지":
                return FishingType.RESERVOIR;
            case "평지":
                return FishingType.FLAT;
            case "계곡":
                return FishingType.VALLEY;
            case "바다":
                return FishingType.SEA;
            default:
                return FishingType.ECT;
        }
    }
}