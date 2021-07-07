package sample;

import java.util.Arrays;
import java.util.Comparator;

public class GeneticAlgorithm {
    private double mutationRate;
    private double crossoverRate;
    private int populationSize;


    private final int bestIndividuals = 1;
    private final int tournamentEnjoyers = 4;


    public Population initializePopulation(int numberOfIndividuals) {
        return new Population(numberOfIndividuals);
    }

    public GeneticAlgorithm(double mutationRate, double crossoverRate, int populationSize) {
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.populationSize = populationSize;
    }

    public Individual tournamentSelection(Population population) {
        Population tournament = population;
        for (int i = 0; i < tournamentEnjoyers; i++) {
            int enjoyer = (int) (Math.random() * populationSize);

        }
        return tournament.getPopulation().get(0);
    }

    public Population crossoverPopulation(Population population) {
        return population;

    }

    public Population evolvePopulation(Population population) {
        return population;

    }

    public Population mutatePopulation(Population population) {
        Population mutatedPopulation = new Population(populationSize);
        for (int i = 0; i < bestIndividuals; i++) {
            mutatedPopulation.getPopulation().add(population.getPopulation().get(i));
        }

        for (int i = 0 ; i < population.getPopulation().size(); i++) {
            for (int j = 0; j < population.getPopulation().get(i).getGenes().length; i++) {
                if (Math.random() <= mutationRate) {
//                    mutatedPopulation.getPopulation().get(i).getGenes()[j] ^= 1;
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

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getBestIndividuals() {
        return bestIndividuals;
    }

    public int getTournamentEnjoyers() {
        return tournamentEnjoyers;
    }

}
