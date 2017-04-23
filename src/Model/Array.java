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
        for(int i=0; i<size; i++){
            if(i == size-1)
                System.out.print(array[i]);
            else
                System.out.print(array[i]+"-");
        }
        System.out.println("");
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
        //System.out.println("P");
        Array a = new Array(this.size+1);
        System.arraycopy(this.array, 0, a.array, 0, this.size);
        a.array[a.size-1] = value;

        return a;
    }
    
    public Array add (Array array){
        int tam = this.size + array.size();
        int[] novo = new int[tam];
        novo = this.array;
        for(int i=this.size,k=0; i<tam; i++,k++){
            novo[i] = array.at(k);
        }
        Array a = new Array(tam);
        a.array = novo;
        return a;
    }
    
}
