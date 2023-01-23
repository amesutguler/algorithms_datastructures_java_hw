package abdullah_mesut_guler_hw4;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class HW4 {
    ArrayList<LinkedList> personTable;
    ArrayList<LinkedList> songTable;
    public void startProcess(String filepath) throws IOException{
        // to initiliaze tables for both persons and songs
        ArrayList<LinkedList> tablePerson = new <LinkedList>ArrayList();
        ArrayList<LinkedList> tableSong = new <LinkedList>ArrayList();
        personTable = tablePerson;
        songTable = tableSong;
        readCommandTxt(filepath);}
    private void readCommandTxt(String filepath) throws FileNotFoundException, IOException{
        boolean exitCond = false;
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while((line = bufferedReader.readLine()) != null){
            exitCond = executeCommands(line);
            if(exitCond) break;
        }
        bufferedReader.close();}
    private boolean executeCommands(String line){
        boolean exitCond = false;
        String words[] = line.split("\\s+");
        String operation = "";
        String name = "";
        String song = "";
        int i = 0;
        while(i < words.length){
            if(i == 0)operation = words[i];
            if(i == 1)name = words[i];
            if(i == 2) song += words[i];
            if(i > 2)song += " " + words[i];
            i++;
        }
        switch(operation){
            case "I" -> addPerson(name);
            case "L" -> personLikesSong(name,song);
            case "E" -> personDisliked(name,song);
            case "D" -> deletePerson(name);
            case "P" -> personLikes(name);
            case "M" -> matchMaking(name);
            case "R" -> offerSong(name);
            case "X" -> {
                System.out.println("Process is finished");
                exitCond = true;
            }
            default -> {
            }
        }
        return exitCond;}
    private boolean addPerson(String personName){
        boolean cond = false;
        // name of the person is converted into a integer key with getKey
        int keyForPerson = getKey(personName);
        if(personTable.get(keyForPerson) != null) return cond;
        // creating a LinkedList for this persons liked songs
        LinkedList pSongsList = new LinkedList(); 
        // go to personskey'th index of the personTable and put
        // the reference of the this persons liked songs list reference
        personTable.set(pSongsList, keyForPerson);
        cond = true;
        return cond;}
    private boolean personLikesSong(String personName ,String songName){
        boolean cond = false;
        int keyForPerson = getKey(personName);
        // personTable.get(keyForPerson) if this is null then it means
        // at the keyForPerson index of the personTable there isn't an 
        // initiliazed LinkedList, which means such a person does not exists
        // in the table for given name
        if(personTable.get(keyForPerson) != null){
            LinkedList dummy = (LinkedList) personTable.get(keyForPerson);
            dummy.push(songName);
            System.out.println(personName + " likes " + songName);
            cond = true; // the song is added to persons liked songs list
            // operations for songTable
            int keyForSong = getKey(songName);
            // check if there is a song at songTable[keyForSong] already 
            if(songTable.get(keyForSong) == null){
                // create a linkedlist for this song. The persons that like
                // this song will be stored here. Reference of the linkedlist
                // is stored in song object's personList field.
                LinkedList sPersonsList = new LinkedList();
                songTable.set(sPersonsList, keyForSong);
                // push the person name that likes this song into linkedlist
                LinkedList dummy1 = sPersonsList;
                dummy1.push(personName);
            }else{
                // the song is already in the songTable
                LinkedList dummy1 = (LinkedList) songTable.get(keyForSong);
                dummy1.push(personName);
            }return cond;
        }else{
            return cond;
        }
    }
    private boolean personDisliked(String personName, String songName){
        boolean cond = false;
        int keyForPerson = getKey(personName);
        LinkedList dummy = (LinkedList) personTable.get(keyForPerson);
        // such a person does not exist in the system
        if(dummy == null) return cond; 
        // person found in the personTable
        if(dummy.popRequired(songName)){
            // the person does not like song anymore
            System.out.println(personName + " does not like the " + songName);
            // person name should also be erased from songTable
            int keyForSong = getKey(songName);
            LinkedList dummy1 = (LinkedList) songTable.get(keyForSong);
            dummy1.popRequired(personName);
            cond = true;
            return cond;
        }else{
            System.out.println(personName +" " + songName + " can not be erased");
            return cond;
        }
    }  
    private boolean deletePerson(String personName){
        boolean cond = false;
        int keyForPerson = getKey(personName);
        LinkedList dummy = (LinkedList) songTable.get(keyForPerson);
        if(personTable.get(keyForPerson) != null){
            personTable.set(null, keyForPerson);
            cond = true;
            // the person should also be erased from songTable
            // the does not have any liked song already
            if(dummy.top == null) return cond;
            while(dummy != null){
                // checking each song that person liked
                String s = dummy.pop();
                int keyForSong = getKey(s);
                if(songTable.get(keyForSong) != null){
                    LinkedList dummy0 = (LinkedList) songTable.get(keyForSong);
                    // deleting person's name from the song LinkedList
                    dummy0.popRequired(personName);
                }
            }
            return cond;
        }else{
            System.out.println(personName + " is not in the list");
            return cond;
        }
    }
    private boolean personLikes(String personName){
        boolean cond = false;
        int keyForPerson = getKey(personName);
        if(personTable.get(keyForPerson) == null){
            System.out.println(personName + " is not in the list");
            return cond;
        }
        LinkedList dummy = (LinkedList) personTable.get(keyForPerson);
        if(dummy.top == null){
            System.out.println(personName + " has no song");
            return cond;
        }else{
            System.out.println(dummy.printList());
            cond = true;
            return cond;
        }
    }
    private boolean offerSong(String personName){
        boolean cond = false;
        int cnt = 0;
        int keyForPerson = getKey(personName);
        if(personTable.get(keyForPerson) == null){
            System.out.println("There is not such a person.");
        }else{
            LinkedList dummyP = (LinkedList) personTable.get(keyForPerson);
            if(dummyP == null){
                System.out.println(personName + " does not have any liked songs.");
                return cond;
            }
            Node dummyPNode = dummyP.top; 
            while(dummyPNode != null){
                
                String aSong = dummyPNode.data;
                int keyForSong = getKey(aSong);
                
                LinkedList dummyS = (LinkedList) songTable.get(keyForSong);
                Node dummySSong = dummyS.top;
                while(dummySSong != null){
                    String aPerson = dummySSong.data;
                    int key0 = getKey(aPerson);
                    LinkedList dummyFinal = (LinkedList) personTable.get(key0);
                    Node dummyFinalNode = dummyFinal.top;
                    while(dummyFinalNode != null){
                        System.out.println("Recommending "+dummyFinalNode.data);
                        dummyFinalNode = dummyFinalNode.next;
                        cnt++;
                        if(cnt == 5) {
                            cond = true;
                            return cond;
                        }
                    }
                    dummySSong = dummySSong.next;
                }
                dummyPNode = dummyPNode.next;
            }
        }
        return cond;
    }
    private boolean matchMaking(String personName){
        boolean cond = false;
        int cnt = 0;
        int keyForPerson = getKey(personName);
        if(personTable.get(keyForPerson) == null){
            System.out.println("There is not such a person");
        }else{
            LinkedList dummyP = (LinkedList) personTable.get(keyForPerson);
            if(dummyP == null){
                System.out.println(personName + " does not have any liked songs. Cannot find possible friends.");
                return cond;
            }
            Node dummyPSongs = dummyP.top;
            while(dummyPSongs != null){
                String aSong = dummyPSongs.data;
                int keyForSong = getKey(aSong);
                LinkedList dummySongList = (LinkedList) songTable.get(keyForSong);
                Node dummySongs = dummySongList.top;
                while(dummySongs != null){
                    if(!dummySongs.data.equals(personName)){
                        System.out.println("Possible friend " + dummySongs.data);
                        cnt++;
                        if(cnt == 3){
                            cond = true;
                            return cond;
                        }
                    }
                    dummySongs = dummySongs.next;
                }
                dummyPSongs = dummyPSongs.next;
            }
        }    
        return cond;
    }

    private class ArrayList<T>{
        private Object[] data;
        private static final int DEFAULT_SIZE = 100000;
        private int size = 0;
        public ArrayList(){
            this.data = new Object[DEFAULT_SIZE];
        }  
        public void add(T num){
            if(isFull()){
                resize();
            }
            data[size++] = num; 
        }
        public T remove(){
            return (T) data[--size];
        }
        public Object get(int index){
            return data[index];
        }
        public int size(){
            return size;
        }
        public void set(T value, int index){
            data[index] = value;
        }
        private boolean isFull(){
            return size == DEFAULT_SIZE;
        }
        private void resize(){
            Object[] tmp = new Object[data.length*2];
            System.arraycopy(data, 0, tmp, 0, data.length);
            data = tmp;
        }
    }
    private static int getKey(String S){
        final int p = 31, m = 111111;
        int res = 0;
        final char[] s = S.toCharArray();
        long p_pow = 1;
        
        for(int i = 0; i < s.length; i++){
            res = (int)((res + (s[i] - 'a' + 1) * p_pow) % m);
            p_pow = (p_pow * p) % m;
        }
        return res;
    }
    private static int getKey2(String S){
        int res = 0;
        final int p = 47, m = 11111111;
        final char[] s = S.toCharArray();
        long p_pow = 1;
        
        for(int i = 0; i < s.length; i++){
            res = (int)((res + (s[i] - 'a' + 1) * p_pow) % m);
            p_pow = (p_pow * p) % m;
        }
        return res;
    }
    private class LinkedList{
        private Node top;
        private boolean isEmpty(){
            return top == null;
        }
        private void push(String item){
        // to at the begining of the stack
            if(isEmpty()){
                top = new Node(item, top); 
            }
            else {
                top = new Node(item, top);
            }
        } 
        private String pop(){
            if(top == null) {
                return null;
            }else{
                String result = top.data;
                top = top.next;
                return result;
            }
        }
        private boolean popRequired(String s){
            boolean cond = false;
            Node dummy = top;
            if(top == null){
                return cond;
            }else{
                if (dummy.data.equals(s)){ // song found in the top
                    cond = true;
                    top = dummy.next;
                    return cond;
                }
                while(dummy.next != null){
                    if(dummy.next.data.equals(s)){
                        if(dummy.next.next == null){// song found in the last node
                            dummy.next = null;
                            cond = true;
                            return cond;
                        }else{
                            cond = true;
                            dummy.next = dummy.next.next;
                            return cond;
                        }
                    }
                    dummy = dummy.next;
                }
            }
            return cond;
        }
        private String printList(){
            String s = "";
            Node dummy = top;
            while(dummy != null){
                s += dummy.data + " ";
                dummy = dummy.next;
            }
            return s;
        }   
    }
    private class Node{
        private String data;
        private Node next;     
        private Node(String data, Node next){
            this.data = data;
            this.next = next;
        }
    }    
}

