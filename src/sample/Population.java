package sample.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;

public class Population {
    ArrayList<Individual> population;

    public Population(){
        population = new ArrayList<>();
    }

    public Population(int size) {
        population = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            population.add(new Individual());
        }
    }

    public void sortPopulationByFitness() {
        //sortowanie populacji na podstawie przystosowania osobników
        population.sort(Comparator.comparing(Individual::getFitness).reversed());
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

//    public void setPopulationFitness() {
//        int temp = 0;
//        for(int i = 0 ; i< this.getPopulation().size(); i++){
//            temp += this.getPopulation().get(i).getFitness();
//        }
//        this.populationFitness = temp / this.getPopulation().size();
//    }

}
