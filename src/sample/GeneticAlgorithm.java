package sample;

import java.util.Arrays;

public class GeneticAlgorithm {
    private double mutationRate;
    private double crossoverRate;


    //    private final int bestIndividuals = 1;
    private final int tournamentKnights = 2;


    public Population initializePopulation(int populationSize) {
        return new Population(populationSize);
    }

    public GeneticAlgorithm(double crossoverRate, double mutationRate) {
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }
    public Individual tournamentSelection(Population population) {
        Population tournament = new Population(population.getPopulation().size());
//        System.out.println("fit1   "+population.getPopulation().get(0).getFitness());
//        System.out.println("fit2   "+population.getPopulation().get(1).getFitness());
        for (int i = 0; i < tournamentKnights; i++) {
            int knightPosition = (int) (Math.random() * population.getPopulation().size());
            Individual tournamentIndividual = population.getPopulation().get(knightPosition);
            tournamentIndividual.setFitness(population.getPopulation().get(knightPosition).getFitness());
            tournament.setIndividual(knightPosition, tournamentIndividual);
//            System.out.println("ryc: " +tournament.getPopulation().get(knightPosition).getFitness());
        }
        tournament.sortPopulationByFitness();
        return tournament.getPopulation().get(0);
    }

    public Population crossover(Population population) {
        Population crossedPopulation = new Population(population.getPopulation().size());
        int forCross = 0, firstParent = 0;

        for (int i = 0; i < population.getPopulation().size(); i++) {

            Individual parent1 = population.getPopulation().get(firstParent);

            if (Math.random() < crossoverRate) {
                Individual offspring_1 = new Individual();
                Individual offspring_2 = new Individual();
                ++forCross;
                if (forCross % 2 == 0) {
                    Individual parent2 = population.getPopulation().get(i);
//                    System.out.println("p1: "+ Arrays.deepToString(parent1.getGenes()));
//                    System.out.println("p2: "+ Arrays.deepToString(parent2.getGenes()));
//                    System.out.println("offspring1 before: "+ Arrays.deepToString(offspring_1.getGenes()));
//                    System.out.println("offspring2 before: "+ Arrays.deepToString(offspring_2.getGenes()));
                    for (int j = 0; j < parent1.getGenes().length; j++) {
                        for (int k = 0; k < parent1.getGenes()[j].length; k++) {
                            if (Math.random() < 0.5) {
//                                System.out.println("pozycja: "+ j + " " + k);
                                offspring_1.setFirstDimensionGenes(j,k, parent2.getGenes()[j][k]);
                                offspring_2.setFirstDimensionGenes(j, k, parent1.getGenes()[j][k]);
                            } else {
                                offspring_1.setFirstDimensionGenes(j,k, parent1.getGenes()[j][k]);
                                offspring_2.setFirstDimensionGenes(j,k, parent2.getGenes()[j][k]);
                            }
                        }
//                        System.out.println("--------");
                        crossedPopulation.setIndividual(firstParent, offspring_1);
                        crossedPopulation.setIndividual(i, offspring_2);
                    }
//                    System.out.println("offspring 1 after: "+ Arrays.deepToString(offspring_1.getGenes()));
//                    System.out.println("offspring 2 after: "+ Arrays.deepToString(offspring_2.getGenes()));
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

        for (int i = 0; i < newPopulation.getPopulation().size(); i++) {
            Individual newIndividual = new Individual();
            newIndividual.setGenes(tournamentSelection(population).getGenes());
            newPopulation.setIndividual(i, newIndividual);
//            System.out.println(i+":  " +Arrays.deepToString(newIndividual.getGenes()));
        }
        return newPopulation;

    }

    public Population mutatePopulation(Population population) {
        Population mutatedPopulation = new Population(population.getPopulation().size());
//        for (int i = 0; i < bestIndividuals; i++) {
//            mutatedPopulation.getPopulation().add(population.getPopulation().get(i));
//        }

        for (int i = 0; i < population.getPopulation().size(); i++) {
            for (int j = 0; j < population.getPopulation().get(i).getGenes().length; j++) {
                for (int k = 0; k < population.getPopulation().get(i).getGenes()[j].length; k++) {
                    if (Math.random() <= mutationRate) {
                        if (mutatedPopulation.getPopulation().get(i).getGenes()[j][k] == 0)
                            mutatedPopulation.getPopulation().get(i).getGenes()[j][k] = 1;
                        else
                            mutatedPopulation.getPopulation().get(i).getGenes()[j][k] = 0;
                    }
                }

            }
        }
        return mutatedPopulation;
    }


    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public int getTournamentKnights() {
        return tournamentKnights;
    }

}
