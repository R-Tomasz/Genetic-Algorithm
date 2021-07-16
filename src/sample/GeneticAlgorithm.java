package sample;

import java.util.Arrays;

public class GeneticAlgorithm {
    private double mutationRate;
    private double crossoverRate;


    private final int bestIndividuals = 1;
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
        for (int i = 0; i < tournamentKnights; i++) {
            int knightPosition = (int) (Math.random() * population.getPopulation().size());
            Individual tournamentIndividual = population.getPopulation().get(knightPosition);
            tournament.setIndividual(knightPosition, tournamentIndividual);
        }
        return tournament.getPopulation().get(0);
    }

    public Population crossover(Population population) {
        Population crossedPopulation = new Population(population.getPopulation().size());
        int forCross = 0, firstParent = 0;

        for (int i = 0; i < population.getPopulation().size(); i++) {

            Individual parent1 = population.getPopulation().get(i);

            if (Math.random() < crossoverRate) {
                Individual offspring_1 = new Individual();
                Individual offspring_2 = new Individual();
                ++forCross;

                if (forCross % 2 == 0) {
                    Individual parent2 = population.getPopulation().get(firstParent);

                    for (int genePosition = 0; genePosition < parent1.getGenes().length; genePosition++) {
                        if (Math.random() < 0.5) {
                            offspring_1.setFirstDimensionGenes(genePosition, parent2.getGenes()[i]);
                            offspring_2.setFirstDimensionGenes(genePosition, parent1.getGenes()[i]);
                        } else {
                            offspring_1.setFirstDimensionGenes(genePosition, parent1.getGenes()[i]);
                            offspring_2.setFirstDimensionGenes(genePosition, parent2.getGenes()[i]);
                        }
                    }
                    crossedPopulation.setIndividual(firstParent, offspring_1);
                    crossedPopulation.setIndividual(i, offspring_2);
                }
                else {
                    firstParent = i;
                }
            }
            else {
                crossedPopulation.setIndividual(i, parent1);
            }
        }
        if (forCross % 2 != 0) { // jezeli petla sie skonczyla i zostal osobnik do krzyzowania, to go pomin
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
        }
        return newPopulation;

    }

    public Population mutatePopulation(Population population) {
        Population mutatedPopulation = new Population(population.getPopulation().size());
//        for (int i = 0; i < bestIndividuals; i++) {
//            mutatedPopulation.getPopulation().add(population.getPopulation().get(i));
//        }

        for (int i = 0; i < population.getPopulation().size(); i++) {
            for (int j = 0; j < population.getPopulation().get(i).getGenes().length; i++) {
                if (Math.random() <= mutationRate) {
                    mutatedPopulation.getPopulation().get(i).getGenes()[i][j] ^= 1;
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

    public int getBestIndividuals() {
        return bestIndividuals;
    }

    public int getTournamentKnights() {
        return tournamentKnights;
    }

}
