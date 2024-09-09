package edu.eci.arsw.blueprints.test.persistence.filters.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.filters.impl.RedundancyFilter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedundancyFilterTest {

    RedundancyFilter filter;

    @Before
    public void setUp() {
        filter = new RedundancyFilter();
    }

    @Test
    public void testRedundancyFilter() {
        Point[] points = new Point[]{
                new Point(10, 10),
                new Point(10, 10),  // Redundante
                new Point(20, 20),
                new Point(20, 20),  // Redundante
                new Point(30, 30)
        };
        Blueprint bp = new Blueprint("Author1", "Blueprint1", points);

        Blueprint filteredBp = filter.filterBlueprint(bp);

        assertEquals(3, filteredBp.getPoints().size());
        assertEquals(10, filteredBp.getPoints().get(0).getX());
        assertEquals(20, filteredBp.getPoints().get(1).getX());
        assertEquals(30, filteredBp.getPoints().get(2).getX());
    }
}
