## Escuela Colombiana de Ingeniería

## Arquitecturas de Software
### Elaborado por: Cristian Alvarez - Juliana Briceño

# Componentes y conectores - Parte I.

El ejercicio se debe traer terminado para el siguiente laboratorio (Parte II).

#### Middleware- gestión de planos.


## Antes de hacer este ejercicio, realice [el ejercicio introductorio al manejo de Spring y la configuración basada en anotaciones](https://github.com/ARSW-ECI/Spring_LightweightCont_Annotation-DI_Example).

### Ejercico resuelto en: https://github.com/CristianAlvarez-b/ARSW_Spring_LightweightCont_Annotation-DI_Example

En este ejercicio se va a construír un modelo de clases para la capa lógica de una aplicación que permita gestionar planos arquitectónicos de una prestigiosa compañia de diseño. 

![](img/ClassDiagram1.png)

1. Configure la aplicación para que funcione bajo un esquema de inyección de dependencias, tal como se muestra en el diagrama anterior.


	Lo anterior requiere:

	* Agregar las dependencias de Spring.
	* Agregar la configuración de Spring.
	* Configurar la aplicación -mediante anotaciones- para que el esquema de persistencia sea inyectado al momento de ser creado el bean 'BlueprintServices'.


2. Complete los operaciones getBluePrint() y getBlueprintsByAuthor(). Implemente todo lo requerido de las capas inferiores (por ahora, el esquema de persistencia disponible 'InMemoryBlueprintPersistence') agregando las pruebas correspondientes en 'InMemoryPersistenceTest'.

Pruebas funcionando:

![image](https://github.com/user-attachments/assets/6a771c96-965a-4534-9e25-ff451ad3edc0)


3. Haga un programa en el que cree (mediante Spring) una instancia de BlueprintServices, y rectifique la funcionalidad del mismo: registrar planos, consultar planos, registrar planos específicos, etc.

![image](https://github.com/user-attachments/assets/95f2cf73-1b3a-4ef8-9285-e3c22d6ca754)


4. Se quiere que las operaciones de consulta de planos realicen un proceso de filtrado, antes de retornar los planos consultados. Dichos filtros lo que buscan es reducir el tamaño de los planos, removiendo datos redundantes o simplemente submuestrando, antes de retornarlos. Ajuste la aplicación (agregando las abstracciones e implementaciones que considere) para que a la clase BlueprintServices se le inyecte uno de dos posibles 'filtros' (o eventuales futuros filtros). No se contempla el uso de más de uno a la vez:
	* (A) Filtrado de redundancias: suprime del plano los puntos consecutivos que sean repetidos.

 	![image](https://github.com/user-attachments/assets/ed784e79-3be3-49d6-a10e-db40509ae9b5)

	* (B) Filtrado de submuestreo: suprime 1 de cada 2 puntos del plano, de manera intercalada.

	![image](https://github.com/user-attachments/assets/1add34a6-6533-4955-94d0-66ee9d93bc55)


5. Agrege las pruebas correspondientes a cada uno de estos filtros, y pruebe su funcionamiento en el programa de prueba, comprobando que sólo cambiando la posición de las anotaciones -sin cambiar nada más-, el programa retorne los planos filtrados de la manera (A) o de la manera (B). 

Pruebas del filtro de redundancia:

![image](https://github.com/user-attachments/assets/8874b36a-3960-4913-a112-958af78e43bc)
![image](https://github.com/user-attachments/assets/c0d94d60-6c17-44e1-a668-d7d943d80df7)



Pruebas del filtro de Submuestreo:

![image](https://github.com/user-attachments/assets/4325a854-bf70-4aeb-a899-97bda60e5232)
![image](https://github.com/user-attachments/assets/543e674e-e377-470e-ba51-00d97e4dca71)


