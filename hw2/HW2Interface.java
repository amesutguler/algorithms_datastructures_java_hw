package abdullah_mesut_guler_hw2;

public interface HW2Interface {
    public void Insert(int newElement, int pos) throws Exception;
    public int Delete(int pos) throws Exception;
    public void LinkReverse();
    public void SacuraL();
    public void OpacuraL();
    public void Output();
    public void ReverseOutput();
    @Override
    public String toString(); 
    public Exception LinkedListException();
}
