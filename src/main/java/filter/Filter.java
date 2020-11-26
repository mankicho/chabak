package filter;

import domain.facility.Utility;

import java.util.function.Predicate;

@FunctionalInterface
public interface Filter {
    boolean filter(Utility utility);
}
