package sample;

import java.util.ArrayList;
import java.util.Comparator;

public class Population {
    int populationFitness;
    ArrayList<Individual> population;

    public Population(){
        population = new ArrayList<>();
    }

    public Population(int size) {
        //super((double) GameViewModel.sceneWidth/2, (double)GameViewModel.sceneHeight/2, GameViewModel.playerRadius);
        population = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            population.add(new Individual());
        }
//        sortPopulationByFitness();
    }

    public void sortPopulationByFitness() {
        population.sort(Comparator.comparing(Individual::getFitness).reversed());
//        population.sort(Comparator.comparing(Individual::calculateFitness).reversed());
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public void setIndividual(int position, Individual individual) {
        population.set(position, individual);
    }


    @Override
    public String toString() {
        return "Population{" +
                "population=" + population.toString() +
                '}';
    }

    public void setPopulationFitness() {
        int temp = 0;
        for(int i = 0 ; i< this.getPopulation().size(); i++){
            temp += this.getPopulation().get(i).getFitness();
        }
        this.populationFitness = temp / this.getPopulation().size();
    }

//    public Individual getFittest(){
//        Individual fittest = this.getPopulation().get(0);
//        fittest.setFitness(this.getPopulation().get(0).getFitness());
//        for(int i = 0 ; i< this.getPopulation().size();i++){
//            if(this.getPopulation().get(i).getFitness() > fittest.getFitness()){
//                fittest = this.getPopulation().get(i);
//            }
//        }
//        return fittest;
//    }

    public int getPopulationFitness() {
        return populationFitness;
    }
}
