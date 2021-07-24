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
        Population tournament = new Population();
        for (int i = 0; i < tournamentKnights; i++) {
//            System.out.println("tournament " + i );
            int knightPosition = (int) (Math.random() * population.getPopulation().size());
//            System.out.println("knight pos: "+knightPosition);
            Individual tournamentIndividual = population.getPopulation().get(knightPosition);
//            System.out.println("knight fit: "+ tournamentIndividual.getFitness());
            tournament.getPopulation().add(tournamentIndividual);
//            System.out.println("KNIGHT GENEES: "+Arrays.deepToString(tournamentIndividual.getGenes()));
//            System.out.println("tournament:  "+tournamentIndividual.getFitness());
        }
        tournament.sortPopulationByFitness();
//        System.out.println("tournament: "+ Arrays.deepToString(tournament.getPopulation().get(0).getGenes()));

//        for (int i =  0 ; i < 4; i++){
//            System.out.println(i+" fit: " +tournament.getPopulation().get(i).getFitness());
//        }
//        System.out.println("----");
//        System.out.println("WINNER: "+Arrays.deepToString(tournament.getPopulation().get(0).getGenes()));
        return tournament.getPopulation().get(0);
    }

    public Population crossover(Population population) {
        Population crossedPopulation = new Population();

        int firstParent = 2; // index pierwszego rodzica
        crossedPopulation.getPopulation().add(population.getPopulation().get(0));
        crossedPopulation.getPopulation().add(population.getPopulation().get(1));

        for (int i = 3; i < population.getPopulation().size(); i++) {
//            System.out.println("i: "+i);
//            System.out.println(population.getPopulation().size());
            Individual parent1 = population.getPopulation().get(firstParent);
            if (Math.random() < crossoverRate) {
                Individual parent2 = population.getPopulation().get(i);
                Individual offspring1 = new Individual(population.getPopulation().get(firstParent).getGenes());
                Individual offspring2 = new Individual(population.getPopulation().get(i).getGenes());// 0 1 | 2 3 4 5 6 7 8 9
//                System.out.println(i+" parent1: " + Arrays.deepToString(parent1.getGenes()));
//                System.out.println(i+" parent2: " + Arrays.deepToString(parent2.getGenes()));
//                System.out.println(i+ " offspring1 b4: " + Arrays.deepToString(offspring1.getGenes()));
//                System.out.println(i+  " offspring2 b4: " + Arrays.deepToString(offspring2.getGenes()));
//                    System.out.println("parent1: " + Arrays.deepToString(parent1.getGenes()));
//                    System.out.println("parent2: " + Arrays.deepToString(parent2.getGenes()));
//                    System.out.println("off1: " + Arrays.deepToString(offspring1.getGenes()));
//                    System.out.println("off2: " + Arrays.deepToString(offspring2.getGenes()));
                for (int j = 0; j < parent1.getGenes().length; j++) {
                    for (int k = 0; k < parent1.getGenes()[j].length; k++) {

                        if (Math.random() < 0.5) {
//                                System.out.println("flup at: " + k);
                            offspring1.setSecondDimensionGenes(j, k, parent2.getGenes()[j][k]);
//                                System.out.println(Arrays.deepToString(offspring1.getGenes()));
                            offspring2.setSecondDimensionGenes(j, k, parent1.getGenes()[j][k]);
                        }
//                            else {
////                                System.out.println("no flip at: " + k);
//                                offspring1.setSecondDimensionGenes(j, k, parent1.getGenes()[j][k]);
////                                System.out.println(Arrays.deepToString(offspring1.getGenes()));
//                                offspring2.setSecondDimensionGenes(j, k, parent2.getGenes()[j][k]);
//                            }
                    }
                }
//                System.out.println("offspring1 after: " + Arrays.deepToString(offspring1.getGenes()));
//                System.out.println("offspring2 after: " + Arrays.deepToString(offspring2.getGenes()));
                crossedPopulation.getPopulation().add(offspring1);
                crossedPopulation.getPopulation().add(offspring2);
                firstParent +=2;
                i+=1;
            } else {
                crossedPopulation.getPopulation().add(parent1);
                firstParent=i;
//                for (Individual individ : crossedPopulation.getPopulation()) {
//                    System.out.println(Arrays.deepToString(individ.getGenes()));
//                }
            }
        }
//        if(population.getPopulation().size()!=crossedPopulation.getPopulation().size()) {
//            System.out.println("population size: " +population.getPopulation().size());
//            System.out.println("crossed size: " +crossedPopulation.getPopulation().size());
//            System.out.println("DUUUUUUUUUUUUUUUUUUUUPPPPPPPPPPPA");

//        }
//        System.out.println("POPULATION AFTER");
//        for(int i = 0 ; i < population.getPopulation().size(); i++){
//            System.out.println("pop at " + i+ ": "+Arrays.deepToString(crossedPopulation.getPopulation().get(i).getGenes()));
//        }
//        System.out.println("-----");
//        System.out.println("START");
//        for(Individual individ : crossedPopulation.getPopulation()){
//            System.out.println(Arrays.deepToString(individ.getGenes()));
//        }
//        System.out.println("STOP");
//        System.out.println(crossedPopulation.getPopulation().size());
        return crossedPopulation;
    }

    public Population makeNewPopulation(Population population) {
//        System.out.println(population.getPopulation().size());
        Population newPopulation = new Population();
        newPopulation.getPopulation().add(population.getPopulation().get(0));
        newPopulation.getPopulation().add(population.getPopulation().get(1));
//        System.out.println("newpop "+newPopulation.getPopulation().size());

//        System.out.println(newPopulation.getPopulation().get(0).getFitness());
//        System.out.println(newPopulation.getPopulation().get(1).getFitness());
//        System.out.println("fit1: "+newPopulation.getPopulation().get(0).getFitness());
//        System.out.println("fit2: "+newPopulation.getPopulation().get(1).getFitness());
//        System.out.println("--");

//        System.out.println("POPULATION BEFORE TOURNAMENT");
        for (int i = 2; i < population.getPopulation().size(); i++) {
//            System.out.println("newpop "+newPopulation.getPopulation().size());
            Individual newIndividual = new Individual(tournamentSelection(population).getGenes());
            newPopulation.getPopulation().add(newIndividual);
        }
//        System.out.println("POPULATION AFTER TOURNAMENT");
//        for(int i = 0 ; i < newPopulation.getPopulation().size(); i++){
//            System.out.println("pop at " + i+ ": "+Arrays.deepToString(newPopulation.getPopulation().get(i).getGenes()));
//        }
//        System.out.println(newPopulation.getPopulation().size());
        return newPopulation;

    }

    public Population mutatePopulation(Population population) {
        Population mutatedPopulation = new Population();
        mutatedPopulation.getPopulation().add(population.getPopulation().get(0));
        mutatedPopulation.getPopulation().add(population.getPopulation().get(1));
//        System.out.println("fit1: "+mutatedPopulation.getPopulation().get(0).getFitness());
//        System.out.println(Arrays.deepToString(mutatedPopulation.getPopulation().get(0).getGenes()));
//        System.out.println("fit2: "+mutatedPopulation.getPopulation().get(1).getFitness());
//        System.out.println("--");
        for (int i = 2; i < population.getPopulation().size(); i++) {
            mutatedPopulation.getPopulation().add(population.getPopulation().get(i));
//            System.out.println("pop size: "+population.getPopulation().size());
            for (int j = 0; j < population.getPopulation().get(i).getGenes().length; j++) {
//                System.out.println("ilosc genow: "+population.getPopulation().get(i).getGenes().length);
                if (Math.random() <= mutationRate) {

                    int mutationPosition = (int) (Math.random() * (population.getPopulation().get(i).getGenes()[0].length));
//                    System.out.println("pozycja mutacji: " +mutationPosition);
//                    System.out.println("pos: " + mutationPosition);
//                    System.out.println("gene before: " + Arrays.deepToString(mutatedPopulation.getPopulation().get(i).getGenes()));
                    if (population.getPopulation().get(i).getGenes()[j][mutationPosition] == 0)
                        mutatedPopulation.getPopulation().get(i).setSecondDimensionGenes(j, mutationPosition, 1);
                    else
                        mutatedPopulation.getPopulation().get(i).setSecondDimensionGenes(j, mutationPosition, 0);
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
