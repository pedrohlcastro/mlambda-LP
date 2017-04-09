package Model;

public class Array {
    private int size;
    private int array[];
    
    public void Array (int size){
        this.size = size;
    }
    
    public int at (int index){
        return this.array[index];
    }
    
    public int size(){
        return size;
    }
    
    public void show (){
        System.out.println(array.toString());
    }
    
    public void set (int index, int value){
        this.array[index] = value;
    }
    
    public Array sort(){
        
        int temp;
        int[] newArray = this.array;
        for (int i = 0; i < newArray.length; i++){
            for (int j = 0; j < newArray.length; j++){
                if (newArray[i] < newArray[j]){
                    temp = newArray[i];
                    newArray[i] = newArray[j];
                    newArray[j] = temp;
                }
            }
        }
        return null;
    }
    
    public Array add (int value){
        return null;
    }
    
    public Array add (Array array){
        return null;
    }
    
    public String toString(){
        return this.array.toString();
    }
    
}
