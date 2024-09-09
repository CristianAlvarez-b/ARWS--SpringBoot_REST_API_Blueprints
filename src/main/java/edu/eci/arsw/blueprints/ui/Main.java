package edu.eci.arsw.blueprints.ui;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;


public class Main {
    public static void main(String args[]){
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bs = app.getBean(BlueprintsServices.class);
        Point[] points = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint newBlueprint = new Blueprint("John Doe", "NewPlan", points);
        Blueprint newBlueprint2 = new Blueprint("John Doe", "AnotherPlan", points);

        // Registrar el blueprint
        try {
            bs.addNewBlueprint(newBlueprint);
            bs.addNewBlueprint(newBlueprint2);
        } catch (BlueprintPersistenceException e) {
            System.out.println("Error retrieving blueprint: " + e.getMessage());
        }
        // Consultar un blueprint por autor y nombre
        try {
            Blueprint retrievedBlueprint = bs.getBlueprint("John Doe", "NewPlan");
            System.out.println("Retrieved Blueprint: " + retrievedBlueprint.getName() + " by " + retrievedBlueprint.getAuthor());
        } catch (Exception e) {
            System.out.println("Error retrieving blueprint: " + e.getMessage());
        }

        // Consultar todos los blueprints de un autor
        try {
            System.out.println("Blueprints by John Doe: " + bs.getBlueprintsByAuthor("John Doe"));
        } catch (Exception e) {
            System.out.println("Error retrieving blueprints: " + e.getMessage());
        }
        // Prueba de filtros
        Point[] pointsToFilter = new Point[]{
                new Point(10, 10),
                new Point(10, 10),  // Redundante
                new Point(20, 20),
                new Point(20, 20),  // Redundante
                new Point(30, 30),
                new Point(40, 40)
        };
        Blueprint bp = new Blueprint("Author1", "Blueprint1", pointsToFilter);
        try {
            bs.addNewBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            System.out.println("Error retrieving blueprint: " + e.getMessage());
        }
        try {
            Blueprint retrievedBlueprint = bs.getBlueprint("Author1", "Blueprint1");
            System.out.println("Retrieved Blueprint: " + retrievedBlueprint.getName() + " by " + retrievedBlueprint.getAuthor());
            System.out.println("Blueprint points: "+ retrievedBlueprint.getPoints().toString());
        } catch (Exception e) {
            System.out.println("Error retrieving blueprint: " + e.getMessage());
        }
    }
}
