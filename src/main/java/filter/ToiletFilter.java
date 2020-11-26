package filter;

import domain.facility.Toilet;
import domain.facility.Utility;

public class ToiletFilter implements Filter {
    @Override
    public boolean filter(Utility utility) {
        return utility instanceof Toilet;
    }
}
