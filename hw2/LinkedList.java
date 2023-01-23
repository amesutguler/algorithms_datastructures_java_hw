package abdullah_mesut_guler_hw2;

public class LinkedList implements HW2Interface {
    private DoubleLinkNode top;
    private DoubleLinkNode tail;
    
    public void Insert (int newElement, int pos) throws Exception{ 
        DoubleLinkNode dummy0 = top;
        
        if(pos == 0){ // adding on top of the stack
            if(top == null){ //initiliazing the stack by adding the first element
                top = new DoubleLinkNode(newElement,null,null);
                tail = top;
            }
            else{ // if there is already an initiliazed stack
                DoubleLinkNode newNode = new DoubleLinkNode(newElement,null,dummy0);
                top = newNode;
                dummy0.left = newNode;
            }
        }
        else{
            int cnt = 0;
            for(int i = 0; i< pos; i++){
                if(!(dummy0.right == null)){
                dummy0 = dummy0.right;
                cnt += 1;
                }            
            }
            if(cnt != pos){ 
                throw new Exception("The entered pos is out of index.");
            }
            else{
                if(dummy0.right == null){ //means updating the tail
                    DoubleLinkNode newNode = new DoubleLinkNode(newElement,dummy0,null);
                    dummy0.right = newNode;
                    tail = newNode;
                }
                else{ // somewhere in the middle
                    DoubleLinkNode newNode = new DoubleLinkNode(newElement,dummy0,dummy0.right);
                    dummy0.right.left = newNode;
                    dummy0.right = newNode;
                }
            }
        }
    }// DONE   
    public int Delete(int pos) throws Exception { 
        DoubleLinkNode dummy0 = top;
        int content;
        int cnt = 0;
        if(pos == 0){
            content = top.Element;
            top = dummy0.right;
            top.left = null;            
        }
        else{
            for(int i = 0; i < pos ; i++){
               if(!(dummy0.right == null)){
                    dummy0 = dummy0.right;
                    cnt++;
                }
            }
            if(cnt != pos){ 
                    content = -1;
                    throw new Exception("The entered pos is out of index. Returning -1");
                }
            else{
                    if(dummy0.right == null){ //means deleting the tail
                        content = tail.Element;
                        tail = dummy0.left;
                        tail.right = null;
                    }
                    else{ // deleting from somewhere in the middle
                        content = dummy0.Element;
                        dummy0.right.left = dummy0.left;
                        dummy0.left.right = dummy0.right;
                        dummy0.left = null;
                        dummy0.right = null;
                    }
                }
        }
        return content; 
    }// DONE
    public void LinkReverse(){
        DoubleLinkNode dummy0 = top;
        DoubleLinkNode dummy1 = tail;
        int tmp0;
        while(!(dummy1.left == dummy0.right)){ 
            if(dummy0.right == dummy1){
                break;
            }
            tmp0 = dummy1.Element;
            dummy1.Element = dummy0.Element;
            dummy0.Element = tmp0;
            dummy1 = dummy1.left;
            dummy0 = dummy0.right;
        }
        if(dummy1.left == dummy0.right){
            tmp0 = dummy1.Element;
            dummy1.Element = dummy0.Element;
            dummy0.Element = tmp0;
        }
        
        if(dummy0.right == dummy1){
            tmp0 = dummy1.Element;
            dummy1.Element = dummy0.Element;
            dummy0.Element = tmp0;
        }
    }// DONE    
    public void SacuraL(){
        DoubleLinkNode dummy0 = top;
        DoubleLinkNode dummy1 = top.right;
        
        int cnt = 0;
        
        // add a buffer at the end of the list
        DoubleLinkNode bufferNode = new DoubleLinkNode(999,tail,null);
        tail.right = bufferNode;
        tail = bufferNode;
        while(!(dummy1 == null)){
            if(!(dummy1.Element==dummy0.Element)){
                
                DoubleLinkNode newNode = new DoubleLinkNode(cnt+1,dummy0,dummy1);
                dummy0.right = newNode;
                dummy1.left = newNode;
                dummy0 = dummy1;
                dummy1 = dummy1.right;
                cnt = 0;
            }
            else{
                cnt++;
                dummy1 = dummy1.right;
            }    
        }
        // lose the buffer
        tail = bufferNode.left;
        tail.right = null;
        bufferNode.left = null;
    }// DONE
    public void OpacuraL(){
        DoubleLinkNode dummy0 = top;
        DoubleLinkNode dummy1 = top.right;
        // create buffer
        DoubleLinkNode bufferNode = new DoubleLinkNode(999,tail,null);
        tail.right = bufferNode;
        tail = bufferNode;
        while(!(dummy0 == null)){
            if(dummy1 == null) break;
            if(dummy1.Element == 1){
                dummy1 = dummy1.right.right;
                if(dummy0.right.right == null){
                    dummy0.right = null;
                    tail.left = null;
                    tail = dummy0;
                    dummy0 = null;
                }
                else{
                    dummy0.right.right.left = dummy0;
                    dummy0.right = dummy0.right.right;
                    dummy0 = dummy0.right;   
                }
            }
            else{
                if(dummy1.Element > 1){
                    DoubleLinkNode newNode = new DoubleLinkNode(dummy0.Element,dummy0,dummy1);
                    dummy1.left = newNode;
                    dummy0.right = newNode;
                    dummy1.Element--;
                    dummy0 = dummy0.right;
                }
            }
        }
        // lose the buffer
        tail = bufferNode.left;
        tail.right = null;
        bufferNode.left = null;
    }// DONE
    public void Output(){
        System.out.print("The Elements in the list are : ");
        DoubleLinkNode dummy0 = top;
        while(!(dummy0 == null)){
            System.out.print( " "+dummy0.Element);
            dummy0 = dummy0.right;
        }
        System.out.println("");
    }// DONE
    public void ReverseOutput(){
        System.out.print("The Elements in the list in reverse order are : ");
        DoubleLinkNode dummy0 = tail;
        while(!(dummy0 == null)){
            System.out.print(" "+dummy0.Element);
            dummy0 = dummy0.left;
        }
        System.out.println("");
    }// DONE
    @Override
    public String toString(){
        DoubleLinkNode dummy = top;
        String s = "" ;
        while(!(dummy == null)){
            s += " " + dummy.Element;
            dummy = dummy.right;
        }
        return s;
    }// DONE
    @Override
    public Exception LinkedListException(){
        throw new UnsupportedOperationException("");
    }// DONE 
}
