package sample;

import javafx.scene.paint.Color;

import java.util.Arrays;

public class GeneticAlgorithm {
    private final double mutationRate;
    private final double crossoverRate;


    private final int bestIndividual = 1;
    private final int tournamentKnights = 4;


    public Population initializePopulation(int populationSize) {
        return new Population(populationSize);
    }

    public GeneticAlgorithm(double crossoverRate, double mutationRate) {
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }

    public Individual tournamentSelection(Population population) {
        for(int i = 0 ; i < population.getPopulation().size(); i++){
//            System.out.println("pop at " + i+ ": "+Arrays.deepToString(population.getPopulation().get(i).getGenes()));
        }
        Population tournament = new Population(population.getPopulation().size());
        for (int i = 0; i < tournamentKnights; i++) {
            int knightPosition = (int) (Math.random() * population.getPopulation().size());
//            System.out.println("knight pos: "+knightPosition);
            Individual tournamentIndividual = population.getPopulation().get(knightPosition);
            tournamentIndividual.setFitness(population.getPopulation().get(knightPosition).getFitness());
//            System.out.println("knight fit: "+ tournamentIndividual.getFitness());
            tournament.setIndividual(knightPosition, tournamentIndividual);
//            System.out.println("KNIGHT GENEES: "+Arrays.deepToString(tournamentIndividual.getGenes()));
//            System.out.println("tournament:  "+tournamentIndividual.getFitness());
//            tournament.sortPopulationByFitness();
        }
        tournament.sortPopulationByFitness();
//        for (int i =  0 ; i < 4; i++){
//            System.out.println(i+" fit: " +tournament.getPopulation().get(i).getFitness());
//        }
//        System.out.println("----");
//        System.out.println("WINNER: "+Arrays.deepToString(tournament.getPopulation().get(0).getGenes()));
        return tournament.getPopulation().get(0);
    }

    public Population crossover(Population population) {
        Population crossedPopulation = new Population(population.getPopulation().size());
        int forCross = 0, firstParent = 0;
//        crossedPopulation.setIndividual(0, population.getPopulation().get(0));
//        crossedPopulation.setIndividual(1, population.getPopulation().get(1));
//        crossedPopulation.getPopulation().get(0).setFitness(population.getPopulation().get(0).getFitness());
//        crossedPopulation.getPopulation().get(1).setFitness(population.getPopulation().get(1).getFitness());

        for (int i = 0; i < population.getPopulation().size(); i++) {

            Individual parent1 = population.getPopulation().get(firstParent);

            if (Math.random() < crossoverRate) {
                Individual offspring_1 = new Individual();
                Individual offspring_2 = new Individual();
                ++forCross;
                if (forCross % 2 == 0) {
                    Individual parent2 = population.getPopulation().get(i);
                    for (int j = 0; j < parent1.getGenes().length; j++) {
                        for (int k = 0; k < parent1.getGenes()[j].length; k++) {
                            if (Math.random() < 0.5) {
                                offspring_1.setSecondDimensionGenes(j, k, parent2.getGenes()[j][k]);
                                offspring_2.setSecondDimensionGenes(j, k, parent1.getGenes()[j][k]);
                            } else {
                                offspring_1.setSecondDimensionGenes(j, k, parent1.getGenes()[j][k]);
                                offspring_2.setSecondDimensionGenes(j, k, parent2.getGenes()[j][k]);
                            }
                        }
                        crossedPopulation.setIndividual(firstParent, offspring_1);
                        crossedPopulation.setIndividual(i, offspring_2);
                    }
                    firstParent++;
                } else {
                    firstParent = i;
                }
            } else {
                crossedPopulation.setIndividual(i, parent1);
            }
        }
        if (forCross % 2 != 0) { // jezeli petla sie skonczyla i zostal osobnik do krzyzowania, to go pomin
//            System.out.println("fp "+firstParent);
            crossedPopulation.setIndividual(firstParent, population.getPopulation().get(firstParent));
        }
        return crossedPopulation;
    }

    public Population makeNewPopulation(Population population) {
        Population newPopulation = new Population(population.getPopulation().size());
//        newPopulation.setIndividual(0, population.getPopulation().get(0));
//        newPopulation.setIndividual(1, population.getPopulation().get(1));
//        newPopulation.getPopulation().get(0).setFitness(population.getPopulation().get(0).getFitness());
//        newPopulation.getPopulation().get(1).setFitness(population.getPopulation().get(1).getFitness());

//        System.out.println("POPULATION BEFORE TOURNAMENT");
        for (int i = 0; i < population.getPopulation().size(); i++) {
            Individual newIndividual = new Individual();
            newIndividual.setGenes(tournamentSelection(population).getGenes());
            newPopulation.setIndividual(i, newIndividual);
        }
//        System.out.println("POPULATION AFTER TOURNAMENT");
//        for(int i = 0 ; i < newPopulation.getPopulation().size(); i++){
//            System.out.println("pop at " + i+ ": "+Arrays.deepToString(newPopulation.getPopulation().get(i).getGenes()));
//        }
        return newPopulation;

    }

    public Population mutatePopulation(Population population) {
        Population mutatedPopulation = new Population(population.getPopulation().size());
//        mutatedPopulation.setIndividual(0, population.getPopulation().get(0));
//        mutatedPopulation.setIndividual(1, population.getPopulation().get(1));
//        mutatedPopulation.getPopulation().get(0).setFitness(population.getPopulation().get(0).getFitness());
//        mutatedPopulation.getPopulation().get(1).setFitness(population.getPopulation().get(1).getFitness());

        for (int i = 0; i < population.getPopulation().size(); i++) {
//            System.out.println("pop size: "+population.getPopulation().size());
            for (int j = 0; j < population.getPopulation().get(i).getGenes().length; j++) {
//                System.out.println("ilosc genow: "+population.getPopulation().get(i).getGenes().length);
                if (Math.random() <= mutationRate) {

                    int mutationPosition = (int) (Math.random() * (population.getPopulation().get(i).getGenes()[0].length));
//                    System.out.println("pozycja mutacji: " +mutationPosition);
//                    System.out.println("pos: " + mutationPosition);
//                    System.out.println("gene before: " + Arrays.deepToString(mutatedPopulation.getPopulation().get(i).getGenes()));
                        if (mutatedPopulation.getPopulation().get(i).getGenes()[j][mutationPosition] == 0)
                            mutatedPopulation.getPopulation().get(i).setSecondDimensionGenes(j,mutationPosition, 1);
                        else
                            mutatedPopulation.getPopulation().get(i).setSecondDimensionGenes(j,mutationPosition, 0);
//                    System.out.println("gene after: " + Arrays.deepToString(mutatedPopulation.getPopulation().get(i).getGenes()));

                }

            }
        }
//        System.out.println("====");
        mutatedPopulation.sortPopulationByFitness();
        return mutatedPopulation;
    }

    public int getTournamentKnights() {
        return tournamentKnights;
    }

}
