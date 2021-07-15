package sample;

import java.util.Arrays;
import java.util.Comparator;

public class GeneticAlgorithm{
    private double mutationRate;
    private double crossoverRate;


    private final int bestIndividuals = 1;
    private final int tournamentKnights = 4;


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

    public Population crossoverPopulation(Population population) {
        int startCrossover = (int) (Math.random() * 4);
        int endCrossover = (int) (Math.random() * 3 ) + 4;
        return population;
//1 0 1 0 1 0 1 0
    }

    public Population evolvePopulation(Population population) {
        return population;

    }

    public Population mutatePopulation(Population population) {
        Population mutatedPopulation = new Population(population.getPopulation().size());
        for (int i = 0; i < bestIndividuals; i++) {
            mutatedPopulation.getPopulation().add(population.getPopulation().get(i));
        }

        for (int i = 0 ; i < population.getPopulation().size(); i++) {
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
