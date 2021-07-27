package sample;


public class GeneticAlgorithm {
    private final double mutationRate;
    private final double crossoverRate;


    public GeneticAlgorithm(double crossoverRate, double mutationRate) {
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }

    public Individual tournamentSelection(Population population) {
        Population tournament = new Population();
        int tournamentKnights = 4; // liczba osobników w turnieju

        for (int i = 0; i < tournamentKnights; i++) {
            // losowanie z wejściowej populacji indeksów osobników, nie można wylosować dwóch najlepszych osobników
            int knightPosition = (int) ((Math.random() * (population.getPopulation().size() - 2)) + 2);

            // zapisanie wylosowanego osobnika do zmiennej
            Individual tournamentIndividual = population.getPopulation().get(knightPosition);


            //dodanie wylosowanego osobnika do populacji turniejowej
            tournament.getPopulation().add(tournamentIndividual);
        }
        tournament.sortPopulationByFitness();
        return tournament.getPopulation().get(0); // zwrócenie najlepszego osobnika z turnieju
    }

    public Population crossover(Population population) {
        Population crossedPopulation = new Population();
        crossedPopulation.getPopulation().add(new Individual(population.getPopulation().get(0).getGenes()));
        crossedPopulation.getPopulation().add(new Individual(population.getPopulation().get(1).getGenes()));

        for (int i = 0; i < population.getPopulation().size() - 2; i++) {

            // rodzic to osobnik z początkowej populacji o indeksie i
            Individual parent1 = population.getPopulation().get(i);
            if (Math.random() < crossoverRate && i != population.getPopulation().size() - 1) { // jeżeli wylosowało krzyżowanie i indeks pierwszego rodzica nie jest ostantim w populacji
                Individual parent2 = population.getPopulation().get(i + 1);

                // potomkowie przejmują całe geny rodziców
                Individual offspring1 = population.getPopulation().get(i);
                Individual offspring2 = population.getPopulation().get(i + 1);

                for (int j = 0; j < parent1.getGenes().length; j++) {
                    for (int k = 0; k < parent1.getGenes()[j].length; k++) {

                        if (Math.random() < 0.5) {
                            // geny rodziców krzyżują się z prawdopodobieństwem 50% w dzieciach
                            offspring1.setSecondDimensionGenes(j, k, parent2.getGenes()[j][k]);
                            offspring2.setSecondDimensionGenes(j, k, parent1.getGenes()[j][k]);
                        } else {
                            offspring1.setSecondDimensionGenes(j, k, parent1.getGenes()[j][k]);
                            offspring2.setSecondDimensionGenes(j, k, parent2.getGenes()[j][k]);
                        }
                    }
                }
                crossedPopulation.getPopulation().add(offspring1);
                crossedPopulation.getPopulation().add(offspring2);
                ++i;
            } else {
                // jeżeli warunek nie został spełniony, przepisz rodzica do nowej populacji
                crossedPopulation.getPopulation().add(parent1);
            }
        }
        crossedPopulation.sortPopulationByFitness();
        return crossedPopulation;
    }

    public Population makeNewPopulation(Population population) {
        Population newPopulation = new Population();

        //przepisanie najlepszych osobników do nowej populacji
        newPopulation.getPopulation().add(new Individual(population.getPopulation().get(0).getGenes()));
        newPopulation.getPopulation().add(new Individual(population.getPopulation().get(1).getGenes()));

        for (int i = 2; i < population.getPopulation().size(); i++) {
            newPopulation.getPopulation().add(new Individual(tournamentSelection(population).getGenes()));
        }
        newPopulation.sortPopulationByFitness();

        return newPopulation;
    }

    public Population mutatePopulation(Population population) {
        Population mutatedPopulation = new Population();
        mutatedPopulation.getPopulation().add(new Individual(population.getPopulation().get(0).getGenes()));
        mutatedPopulation.getPopulation().add(new Individual(population.getPopulation().get(1).getGenes()));
        for (int i = 2; i < population.getPopulation().size(); i++) {
            // wstępne przepisanie osobnika z wejściowej populacji
            mutatedPopulation.getPopulation().add(population.getPopulation().get(i));

            for (int j = 0; j < population.getPopulation().get(i).getGenes().length; j++) {
                if (Math.random() <= mutationRate) {

                    //jeżeli w chromosomie ma zajść mutacja, zamień losowy bit w tym chromosomie
                    int mutationPosition = (int) (Math.random() * (population.getPopulation().get(0).getGenes()[0].length));
                    if (population.getPopulation().get(i).getGenes()[j][mutationPosition] == 0)
                        mutatedPopulation.getPopulation().get(i).setSecondDimensionGenes(j, mutationPosition, 1);
                    else
                        mutatedPopulation.getPopulation().get(i).setSecondDimensionGenes(j, mutationPosition, 0);

                }

            }
        }
        return mutatedPopulation;
    }

}
