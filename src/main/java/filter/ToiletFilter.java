package filter;

import domain.facility.Fishing;
import domain.facility.Toilet;
import domain.facility.Utility;

import java.util.List;

public class ToiletFilter implements Filter {
    @Override
    public boolean filter(List<Utility> utilities) {
        boolean filter = false;
        for (Utility utility : utilities) {
            if (utility instanceof Toilet) {
                return true;
            }
        }
        return false;
    }
}
