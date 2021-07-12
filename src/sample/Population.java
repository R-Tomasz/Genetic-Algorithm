package sample;

import java.util.ArrayList;

public class Population {
    int populationFitness = 0;
    ArrayList<Individual> population;


    public Population(int size) {
        //super((double) GameViewModel.sceneWidth/2, (double)GameViewModel.sceneHeight/2, GameViewModel.playerRadius);
        population = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            population.add(new Individual());
        }
        sortPopulationByFitness();
    }

    public void sortPopulationByFitness() {
//        population.sort(Comparator.comparing(Individual::calculateFitness).reversed());
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }



    @Override
    public String toString() {
        return "Population{" +
                "population=" + population.toString() +
                '}';
    }
}
