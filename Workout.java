/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amnd7dfitnesstracker;

/**
 *
 * @author Austi
 */
public class Workout implements TrackerItem, java.io.Serializable {
    private String workoutName;
    private int weight;
    private int sets;
    private int repititions;
    
    private static int totalRepititions = 0;
    private static int volume = 0;
    
    public Workout(String workoutName, int sets, int repititions, int weight){
        this.workoutName = workoutName;
        this.sets = sets;
        this.repititions = repititions;
        this.weight = weight;
    }
    
    @Override
    public String getItem()
    {
        return workoutName;
    }
    public int getSets()
    {
        return sets;
    }
    public int getRepititions()
    {
        return repititions;
    }
    public int getWeight(){
        return weight;
    }
    
    public static int getVolume()
    {
        return volume;
    }
    public static int getTotalRepititions()
    {
        return totalRepititions;
    }
    
    public static void addRepititions(int repititions)
    {
        totalRepititions += repititions;
    }
    public static void addVolume(int sets, int repititions, int weight)
    {
        volume += (sets * repititions * weight);
    }
    
    public static void setZero()
    {
        totalRepititions = 0;
        volume = 0;
    }
}
