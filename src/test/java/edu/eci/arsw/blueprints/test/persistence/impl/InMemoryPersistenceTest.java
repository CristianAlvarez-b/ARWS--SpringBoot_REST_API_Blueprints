/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.filters.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }
    @Test
    public void givenExistingBlueprint_whenGetBlueprint_thenCorrectBlueprintIsReturned() throws BlueprintNotFoundException {
        // Prueba para obtener un blueprint que ya existe
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        Blueprint bp = bpPersistence.getBlueprint("_authorname_", "_bpname_ ");

        assertNotNull("The blueprint should not be null", bp);
        assertEquals("The author name should match", "_authorname_", bp.getAuthor());
        assertEquals("The blueprint name should match", "_bpname_ ", bp.getName());
    }

    @Test(expected = BlueprintNotFoundException.class)
    public void givenNonExistingBlueprint_whenGetBlueprint_thenBlueprintNotFoundExceptionIsThrown() throws BlueprintNotFoundException {
        // Prueba para cuando se busca un blueprint que no existe
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        bpPersistence.getBlueprint("non_existing_author", "non_existing_bp");
    }

    @Test
    public void givenExistingAuthor_whenGetBlueprintsByAuthor_thenAllBlueprintsAreReturned() throws BlueprintNotFoundException {
        // Prueba para obtener todos los blueprints de un autor existente
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        Set<Blueprint> blueprints = bpPersistence.getBlueprintsByAuthor("_authorname_");

        assertNotNull("The set of blueprints should not be null", blueprints);
        assertFalse("The set of blueprints should not be empty", blueprints.isEmpty());

        for (Blueprint bp : blueprints) {
            assertEquals("The author name should match", "_authorname_", bp.getAuthor());
        }
    }

    @Test(expected = BlueprintNotFoundException.class)
    public void givenNonExistingAuthor_whenGetBlueprintsByAuthor_thenBlueprintNotFoundExceptionIsThrown() throws BlueprintNotFoundException {
        // Prueba para cuando se busca un autor que no tiene blueprints
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        bpPersistence.getBlueprintsByAuthor("non_existing_author");
    }
    // Prueba con autor vacío
    @Test(expected = BlueprintNotFoundException.class)
    public void givenEmptyAuthor_whenGetBlueprint_thenBlueprintNotFoundExceptionIsThrown() throws BlueprintNotFoundException {
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        bpPersistence.getBlueprint("", "_bpname_ ");
    }

    // Prueba con nombre de blueprint vacío
    @Test(expected = BlueprintNotFoundException.class)
    public void givenEmptyBlueprintName_whenGetBlueprint_thenBlueprintNotFoundExceptionIsThrown() throws BlueprintNotFoundException {
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        bpPersistence.getBlueprint("_authorname_", "");
    }


    // Prueba con blueprint sin puntos
    @Test
    public void givenBlueprintWithoutPoints_whenSaveBlueprint_thenItIsSavedSuccessfully() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        Blueprint bp = new Blueprint("_authorname_", "_emptyBlueprint_", new Point[]{});
        bpPersistence.saveBlueprint(bp);

        Blueprint retrievedBp = bpPersistence.getBlueprint("_authorname_", "_emptyBlueprint_");
        assertNotNull("The blueprint should not be null", retrievedBp);
        assertEquals("The blueprint should have no points", 0, retrievedBp.getPoints().size());
    }

    // Prueba con un autor con múltiples blueprints
    @Test
    public void givenAuthorWithMultipleBlueprints_whenGetBlueprintsByAuthor_thenAllBlueprintsAreReturned() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        Blueprint bp1 = new Blueprint("_authorname_", "_bp1_", new Point[]{new Point(0, 0)});
        Blueprint bp2 = new Blueprint("_authorname_", "_bp2_", new Point[]{new Point(10, 10)});
        bpPersistence.saveBlueprint(bp1);
        bpPersistence.saveBlueprint(bp2);

        Set<Blueprint> blueprints = bpPersistence.getBlueprintsByAuthor("_authorname_");

        assertEquals("There should be 3 blueprints for this author", 3, blueprints.size());
    }

    // Prueba con nombre de blueprint duplicado para el mismo autor
    @Test(expected = BlueprintPersistenceException.class)
    public void givenDuplicateBlueprintName_whenSaveBlueprint_thenBlueprintPersistenceExceptionIsThrown() throws BlueprintPersistenceException {
        InMemoryBlueprintPersistence bpPersistence = new InMemoryBlueprintPersistence();
        Blueprint bp = new Blueprint("_authorname_", "_bpname_ ", new Point[]{new Point(140, 140)});
        bpPersistence.saveBlueprint(bp);  // Debe lanzar la excepción ya que el blueprint ya existe
    }
    @Test
    public void testRedundancyFilter() {
        RedundancyFilter filter = new RedundancyFilter();
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
