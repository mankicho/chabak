package filter;

import domain.facility.Fishing;
import domain.facility.Utility;

import java.util.List;

public class FishingFilter implements Filter {
    @Override
    public boolean filter(List<Utility> utilities) {

        for (Utility utility : utilities) {
            if (utility instanceof Fishing) {
                return true;
            }
        }
        return false;
    }
}
