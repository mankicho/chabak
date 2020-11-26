package filter;

import domain.facility.Fishing;
import domain.facility.Utility;

public class FishingFilter implements Filter {
    @Override
    public boolean filter(Utility utility) {
        return utility instanceof Fishing;
    }
}
