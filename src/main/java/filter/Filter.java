package filter;

import domain.facility.Utility;

import java.util.List;
import java.util.function.Predicate;

@FunctionalInterface
public interface Filter {
    boolean filter(List<Utility> utilities);
}
