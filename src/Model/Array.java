package Model;

public class Array {
    private int size;
    private int array[];
    
    public Array (int size){
        this.size = size;
        this.array = new int[this.size];
    }
    
    public int at (int index){
        return this.array[index];
    }
    
    public int size(){
        return size;
    }
    
    public void show (){
        System.out.print("[");
        for(int i=0; i<size; i++){
            if(i==0)
                System.out.print(array[i]+",");
            else if(i == size-1)
                System.out.print(" "+array[i]);
            else
                System.out.print(" "+array[i]+",");
        }
        System.out.println("]");
    }
    
    public void set (int index, int value){
        this.array[index] = value;
    }
    
    public Array sort(){
        int temp;
        Array a;
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
        a = new Array(this.size);
        a.array = newArray;
        return a;
    }

    public Array add (int value){
        int tempArray[] = new int[this.size+1];
        System.arraycopy(this.array, 0, tempArray, 0, this.size);
        tempArray[this.size] = value;
        Array a = new Array(this.size+1);
        a.array = tempArray;
        
        return a;
    }
    
    public Array add (Array array){
        int tempArray[] = new int[this.size + array.size()];
        System.arraycopy(this.array, 0, tempArray, 0, this.size);
        for(int i=this.size, k=0; i<this.size + array.size(); i++,k++)
            tempArray[i] = array.at(k);
        Array a = new Array(this.size + array.size());
        a.array = tempArray;
        
        return a;
    }
    
}
