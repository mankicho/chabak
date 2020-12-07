package domain;

public class BestAndCount {
    private String cityProvince;
    private String bestPlaceName;
    private String bestPlaceImage;
    private int count;
    private int numOfJJIM;

    public BestAndCount(String cityProvince, String bestPlaceName, String bestPlaceImage, int count, int numOfJJIM) {
        this.cityProvince = cityProvince;
        this.bestPlaceName = bestPlaceName;
        this.bestPlaceImage = bestPlaceImage;
        this.count = count;
        this.numOfJJIM = numOfJJIM;
    }

    public String getCityProvince() { return cityProvince; }

    public String getBestPlaceName() { return bestPlaceName; }

    public String getBestPlaceImage() { return bestPlaceImage; }

    public int getCount() { return count; }

    public int getNumOfJJIM() { return numOfJJIM; }
}
