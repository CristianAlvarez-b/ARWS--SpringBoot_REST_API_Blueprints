package edu.eci.arsw.blueprints.test.persistence.filters.impl;


import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.filters.impl.SubsamplingFilter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubsamplingFilterTest {

    SubsamplingFilter filter;

    @Before
    public void setUp() {
        filter = new SubsamplingFilter();
    }

    @Test
    public void testSubsamplingFilter() {
        Point[] points = new Point[]{
                new Point(10, 10),
                new Point(20, 20),
                new Point(30, 30),
                new Point(40, 40),
                new Point(50, 50)
        };
        Blueprint bp = new Blueprint("Author1", "Blueprint1", points);

        Blueprint filteredBp = filter.filterBlueprint(bp);

        // Después del subsampling, deberíamos tener solo los puntos en índices pares
        assertEquals(3, filteredBp.getPoints().size());
        assertEquals(10, filteredBp.getPoints().get(0).getX());
        assertEquals(30, filteredBp.getPoints().get(1).getX());
        assertEquals(50, filteredBp.getPoints().get(2).getX());
    }
}
